package recstation.lkk.com.recstation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.Adress;
import recstation.lkk.com.recstation.model.JsonBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.NetUtil;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.StringUtil;

public class AddAdressActivity extends BaseActivity implements View.OnClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    String isDefault = "1";
    private EditText add_address_name;
    private EditText add_address_phone;
    private Button add_address_addressname;
    private EditText add_address_addressreal;
    private LinearLayout add_address_addressname_click;
    private Button add_address_savebtn;
    private Button add_address_delbtn;
    private ImageView iv_add_address_isdefault;
    String SETADDRESS_URL = URLConfig.ADDADDRESS_URL;
    String CHANGEADDRESS_URL = URLConfig.EDITADDRESS_URL;
    String URL = SETADDRESS_URL;
    String id = "";
    String PROVINCE = "";
    String CITY = "";
    String AREA = "";
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private static boolean isLoaded = false;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case MSG_LOAD_SUCCESS:
                    // Toast.makeText(AddAdressActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    // Toast.makeText(AddAdressActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public static Intent createIntent(Context context) {
        return new Intent(context, AddAdressActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        initView();
        initData();
        initEvent();

    }


    @Override
    public void initView() {
        add_address_name = findView(R.id.add_address_name, this);
        add_address_phone = findView(R.id.add_address_phone, this);
        add_address_addressname = findView(R.id.add_address_addressname, this);
        add_address_addressreal = findView(R.id.add_address_addressreal, this);
        add_address_addressname_click = findView(R.id.add_address_addressname_click, this);
        add_address_savebtn = findView(R.id.add_address_savebtn, this);
        add_address_delbtn = findView(R.id.add_address_delbtn, this);
        iv_add_address_isdefault = findView(R.id.iv_add_address_isdefault, this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.add_address_addressname:
                //地区选择
                hideInput();


                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(AddAdressActivity.this, "正在初始化全国地区数据，请稍后再试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_address_addressname_click:
                //地区选择
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(AddAdressActivity.this, "正在初始化全国地区数据，请稍后再试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_address_savebtn:
                //提交数据
//                Toast.makeText(AddAdressActivity.this, "保存地址到服务器", Toast.LENGTH_SHORT).show();
                saveAdress();
                break;
            case R.id.add_address_delbtn:
                //提交数据
//                Toast.makeText(AddAdressActivity.this, "保存地址到服务器", Toast.LENGTH_SHORT).show();
                delAdress();
                break;
            case R.id.iv_add_address_isdefault:
                //更改默认和图片。
                if (isDefault.equals("1")) {
                    isDefault = "0";
                    iv_add_address_isdefault.setImageResource(R.drawable.off);
                } else {
                    isDefault = "1";
                    iv_add_address_isdefault.setImageResource(R.drawable.on);
                }

                break;
        }

    }

    @Override
    public void initData() {
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String adressName = getIntent().getStringExtra("adressName");
        String realAdress = getIntent().getStringExtra("realAdress");
        String isDefault = getIntent().getStringExtra("isDefault");
        PROVINCE = getIntent().getStringExtra("PROVINCE");
        CITY = getIntent().getStringExtra("CITY");
        AREA = getIntent().getStringExtra("AREA");
        id = getIntent().getStringExtra("id");
        Logger.e("idshisha", id + "ffff");
        if (!"".equals(name)) {
            add_address_name.setText(name);
        }
        if (!"".equals(phone)) {
            add_address_phone.setText(phone);
        }
        if (!"".equals(adressName)) {
            add_address_addressname.setText(adressName);
        }
        if (!"".equals(realAdress)) {
            add_address_addressreal.setText(realAdress);
        }
        if (!"".equals(isDefault)) {
            if ("1".equals(isDefault)) {
                iv_add_address_isdefault.setImageResource(R.drawable.on);
            } else {
                iv_add_address_isdefault.setImageResource(R.drawable.off);

            }

        }
        if (!("".equals(id) || id == null)) {
            URL = CHANGEADDRESS_URL;
            add_address_savebtn.setText("确认修改");
            add_address_delbtn.setVisibility(View.VISIBLE);
        } else {
            add_address_delbtn.setVisibility(View.GONE);
            add_address_savebtn.setText("保存地址");
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 子线程中解析省市区数据
                initJsonData();
            }
        });
        thread.start();
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        startActivity(AdressListActivity.createIntent(AddAdressActivity.this));
        finish();
        super.onBackPressed();

    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = TestUtil.getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                PROVINCE = opt1tx;
                CITY = opt2tx;
                AREA = opt3tx;
                add_address_addressname.setText(tx);
                Toast.makeText(AddAdressActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    public void saveAdress() {

        final String name = add_address_name.getText().toString();
        final String MOBILE = add_address_phone.getText().toString();
        final String ADDRESS = add_address_addressreal.getText().toString();
        if (StringUtil.isEmpty(PROVINCE)) {
            showShortToast("请选址地区信息");
            return;
        }
        if (StringUtil.isEmpty(ADDRESS)) {
            showShortToast("请填写详细地址");
            return;
        }
        if (StringUtil.isEmpty(MOBILE)) {
            showShortToast("请输入联系方式");
            return;
        }
        if (StringUtil.isEmpty(name) ){
            showShortToast("请填写用户姓名");
            return;
        }
        if (StringUtil.isEmpty(PROVINCE)) {
            showShortToast("请选址地区信息");
            return;
        }
        if (!NetUtil.isNetConnected(this)) {
            showShortToast("没有网络");
            Logger.e("wcnmd","没有网络");
            return;
        }

        showProgressDialog("正在申请验证码");


        String username = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");


        if (!("".equals(id) || id == null)) {
            Subscription subscription = HKEapiManager.getInstances().demoApi.editAdress(URLConfig.EDITADDRESS_URL, isDefault, id, username, name, MOBILE, PROVINCE, CITY, AREA, ADDRESS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                String code = jsonObject.getString("code");
                                if (code.equals("OK")) {
                                    showShortToast("修改成功", true);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                            showShortToast("网络异常，请稍后再试", true);

                        }
                    }, new Action0() {
                        @Override
                        public void call() {
//                        Logger.e("lkk", "onCompleted");
                        }
                    });
            mCompositeSubscription.add(subscription);
        } else {

            //showProgressDialog("正在核对验证码");
            Subscription subscription = HKEapiManager.getInstances().demoApi.addAdress(URL, isDefault, username, name, MOBILE, PROVINCE, CITY, AREA, ADDRESS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                String code = jsonObject.getString("code");
                                if (code.equals("OK")) {
                                    showShortToast("添加成功", true);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                            showShortToast("网络异常，请稍后再试", true);

                        }
                    }, new Action0() {
                        @Override
                        public void call() {
//                        Logger.e("lkk", "onCompleted");
                        }
                    });
            mCompositeSubscription.add(subscription);
        }

        finish();
        startActivity(AdressListActivity.createIntent(AddAdressActivity.this));
    }

    public void delAdress() {


        if (!("".equals(id) || id == null)) {
            Subscription subscription2 = HKEapiManager.getInstances().demoApi.delAdress(URLConfig.DELADDRESS_URL, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                String code = jsonObject.getString("code");
                                if (code.equals("OK")) {
                                    showShortToast("删除成功", true);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                            showShortToast("网络异常，请稍后再试", true);

                        }
                    }, new Action0() {
                        @Override
                        public void call() {
//                        Logger.e("lkk", "onCompleted");
                        }
                    });
            mCompositeSubscription.add(subscription2);

           finish();
        startActivity(AdressListActivity.createIntent(AddAdressActivity.this));
        }
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
