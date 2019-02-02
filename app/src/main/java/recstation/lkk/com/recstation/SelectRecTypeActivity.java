package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Notice;
import recstation.lkk.com.recstation.model.Piclb;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;

public class SelectRecTypeActivity extends BaseActivity implements View.OnClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private OptionsPickerView pvNoLinkOptions;
    LinearLayout mLinearLayout;
    LinearLayout adress_linearlayout;
    Button select_rectype_savebtn;
    TextView selectRec_datetime, selectRec_adress, selectRec_phone, selectRec_price, selectRec_miaoshu, selectRec_rectype;
    String selectRec_datetime1, selectRec_adress1, selectRec_phone1, selectRec_RETRIEVETYPE_ID, selectRec_price1, selectRec_miaoshu1, selectRec_rectype1;
    String address, phone, province, city, area, adressreal, ydate, ytime;
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> month = new ArrayList<>();
    private ArrayList<ArrayList<String>> time = new ArrayList<ArrayList<String>>();
    private ArrayList<String> time_1 = new ArrayList<String>();

    public static Intent createIntent(Context context) {
        return new Intent(context, SelectRecTypeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_rec_type);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        mLinearLayout = findView(R.id.selectRec_datetime_click, this);
        adress_linearlayout = findView(R.id.adress_linearlayout, this);
        select_rectype_savebtn = findView(R.id.select_rectype_savebtn, this);
        selectRec_datetime = findView(R.id.selectRec_datetime);
        selectRec_adress = findView(R.id.selectRec_adress);
        selectRec_phone = findView(R.id.selectRec_phone);
        selectRec_price = findView(R.id.selectRec_price);
        selectRec_miaoshu = findView(R.id.selectRec_miaoshu);
        selectRec_rectype = findView(R.id.selectRec_rectype);
    }

    @Override
    public void initData() {

        selectRec_price1 = getIntent().getStringExtra("selectRec_price1");
        selectRec_RETRIEVETYPE_ID = getIntent().getStringExtra("selectRec_RETRIEVETYPE_ID");
        selectRec_miaoshu1 = getIntent().getStringExtra("selectRec_miaoshu1");
        selectRec_rectype1 = getIntent().getStringExtra("selectRec_rectype1");
        selectRec_price.setText("行情价格:" + selectRec_price1 + "/公斤");
        selectRec_miaoshu.setText(selectRec_miaoshu1);
        selectRec_rectype.setText(selectRec_rectype1);
        selectRec_adress.setText("请添加服务地址");
        selectRec_phone.setText("");
        initNoLinkOptionsPicker();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.selectRec_datetime_click && pvNoLinkOptions != null) {
            pvNoLinkOptions.show(v);
        } else if (v.getId() == R.id.adress_linearlayout) {

            //打开地址栏
            startActivityForResult(AdressListActivity.createIntent(SelectRecTypeActivity.this), URLConfig.TYPETOLIST);

        } else if (v.getId() == R.id.select_rectype_savebtn) {

            //上传数据
            yuyueHuishou();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == URLConfig.TYPETOLIST) {
            address = data.getStringExtra("adress");
            phone = data.getStringExtra("phone");
            province = data.getStringExtra("PROVINCE");
            city = data.getStringExtra("CITY");
            area = data.getStringExtra("AREA");
            adressreal = data.getStringExtra("ADDRESSreal");

            selectRec_adress.setText(address);
            selectRec_phone.setText(phone);
        }
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }

    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        getNoLinkData();
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                ydate = month.get(options1);
                ytime = time.get(options1).get(options2);
                String str = month.get(options1) + " " + time.get(options1).get(options2);
                selectRec_datetime.setText(str);
                //showShortToast(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = month.get(options1) + " " + time.get(options1).get(options2);
                        selectRec_datetime1 = str;
                        selectRec_datetime.setText(str);
                        // showShortToast(str);
                    }
                })
                .build();
//        pvNoLinkOptions.setNPicker(year, month, time);
        pvNoLinkOptions.setPicker(month, time);
        pvNoLinkOptions.setSelectOptions(0, 0);


    }

    private void getNoLinkData() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String y0 = calendar.get(Calendar.YEAR) + "";
        String m0 = (calendar.get(Calendar.MONTH) + 1) + "";
        String d0 = calendar.get(Calendar.DATE) + "";

        year.add(y0);
        month.add(y0 + "-" + m0 + "-" + d0);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        String y = calendar.get(Calendar.YEAR) + "";
        String m = (calendar.get(Calendar.MONTH) + 1) + "";
        String d = calendar.get(Calendar.DATE) + "";

        month.add(y + "-" + m + "-" + d);

        calendar.add(Calendar.DATE, 1);

        String y2 = calendar.get(Calendar.YEAR) + "";
        String m2 = (calendar.get(Calendar.MONTH) + 1) + "";
        String d2 = calendar.get(Calendar.DATE) + "";

        month.add(y2 + "-" + m2 + "-" + d2);

        calendar.add(Calendar.DATE, 1);

        String y3 = calendar.get(Calendar.YEAR) + "";
        String m3 = (calendar.get(Calendar.MONTH) + 1) + "";
        String d3 = calendar.get(Calendar.DATE) + "";
        month.add(y3 + "-" + m3 + "-" + d3);

        calendar.add(Calendar.DATE, 1);

        String y4 = calendar.get(Calendar.YEAR) + "";
        String m4 = (calendar.get(Calendar.MONTH) + 1) + "";
        String d4 = calendar.get(Calendar.DATE) + "";
        month.add(y4 + "-" + m4 + "-" + d4);

        String y5 = calendar.get(Calendar.YEAR) + "";
        String m5 = (calendar.get(Calendar.MONTH) + 1) + "";
        String d5 = calendar.get(Calendar.DATE) + "";
        month.add(y5 + "-" + m5 + "-" + d5);


        time_1.add("8:00-12:00");
        time_1.add("14:00-18:00");
        time.add(time_1);
        time.add(time_1);
        time.add(time_1);
        time.add(time_1);
        time.add(time_1);
        time.add(time_1);
    }

    //网络请求默认地址，没有的话就点击添加

    private void yuyueHuishou() {
        String username = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");

        Subscription subscription = HKEapiManager.getInstances().demoApi.yuyuehuishou(URLConfig.YUYUE_URL, username, selectRec_RETRIEVETYPE_ID,province,city,area,adressreal,phone,ydate,ytime )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Logger.e("yuyueHuishou", s);


                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            String code = jsonObject1.getString("code");
                            if ("OK".equals(code)) {
                                //关闭自己跳转到成功。
                                Intent i =SucessActivity.createIntent(SelectRecTypeActivity.this);
                                i.putExtra("title","预约成功");
                                startActivity(i);
                                showShortToast("预约成功", true);
                            } else {
                                showShortToast("网络异常，稍后再试", true);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showShortToast("网络异常，稍后再试", true);

                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showShortToast("网络异常，稍后再试", true);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });

        mCompositeSubscription.add(subscription);


    }


}
