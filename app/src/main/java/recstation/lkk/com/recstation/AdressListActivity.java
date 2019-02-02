package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.adapter.AdressAdapter;
import recstation.lkk.com.recstation.adapter.HuishouBeanAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.Adress;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.AdressView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseHttpRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;

public class AdressListActivity extends BaseHttpRecyclerActivity<Adress,AdressView,AdressAdapter> {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    List<Adress> mdatalist = new ArrayList<Adress>();
    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, AdressListActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        srlBaseHttpRecycler.autoRefresh();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }


    @Override
    public void setList(final List<Adress> list) {
        mdatalist = list;
        setList(new AdapterCallBack<AdressAdapter>() {

            @Override
            public AdressAdapter createAdapter() {
                return new AdressAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }
    @Override
    public void getListAsync(int page) {
        getRectypeData(page);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getRectypeData(0);

    }

    @Override
    public List<Adress> parseArray(String json) {
        return JSON.parseArray(json, Adress.class);
    }

    @Override
    public void onForwardClick(View v) {
        super.onForwardClick(v);
        //打开地址编辑页面
        startActivity(AddAdressActivity.createIntent(AdressListActivity.this));
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //打开编辑地址页面
        Intent intent = new Intent();
        //把返回数据存入Intent
        String adress =mdatalist.get(position).getPROVINCE()+mdatalist.get(position).getCITY()+mdatalist.get(position).getAREA()+mdatalist.get(position).getADDRESS();

        Logger.e("nnnnn",adress);
        Logger.e("nnnnn2",mdatalist.get(position).getMOBILE());
        intent.putExtra("adress", adress);
        intent.putExtra("ADDRESSreal", mdatalist.get(position).getADDRESS());
        intent.putExtra("PROVINCE", mdatalist.get(position).getPROVINCE());
        intent.putExtra("CITY", mdatalist.get(position).getCITY());
        intent.putExtra("AREA", mdatalist.get(position).getAREA());
        intent.putExtra("phone", mdatalist.get(position).getMOBILE());
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
//        Intent i = AddAdressActivity.createIntent(AdressListActivity.this);
//        i.putExtra("selectRec_rectype1",mdatalist.get(position).getRETRIEVETYPE_NAME());
//        i.putExtra("selectRec_miaoshu1",mdatalist.get(position).getBZ());
//        i.putExtra("selectRec_price1",mdatalist.get(position).getPRICE());
//        startActivity(i);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        //把返回数据存入Intent
        String adress =mdatalist.get(0).getPROVINCE()+mdatalist.get(0).getCITY()+mdatalist.get(0).getAREA()+mdatalist.get(0).getADDRESS();
        intent.putExtra("adress", adress);
        intent.putExtra("phone", mdatalist.get(0).getMOBILE());
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    public void getRectypeData(final int page) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }
        String username =HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");

        //showProgressDialog("正在核对验证码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.findAdress(URLConfig.FINDADDRESS_URL,username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Logger.e("nimabi",s);

//                        s2="[{\"name\":\"李小康\",\"phone\":\"13900034567\",\"adressName\":\"河北省 邯郸市 丛台区1\",\"realAdress\":\"八大胡同132号\",\"isDefault\":\"1\"},"+
//                    "{\"name\":\"李小康2\",\"phone\":\"13900034567\",\"adressName\":\"河北省 邯郸市 丛台区2\",\"realAdress\":\"八大胡同132号\",\"isDefault\":\"0\"},"+
//                   "{\"name\":\"李小康3\",\"phone\":\"13900034567\",\"adressName\":\"河北省 邯郸市 丛台区3\",\"realAdress\":\"八大胡同132号\",\"isDefault\":\"0\"}]";
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            JSONArray jsonArray = jsonObject.getJSONArray("addresslist");
                            Gson gson1 = new Gson();
                            List<Adress> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Adress>>() {
                            }.getType());

                            onHttpResponse(-page, page >= 1 ? null :  JSON.toJSONString(khsllist2), null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                        showShortToast("网络异常，请稍后再试",true);

                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });


        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }


}
