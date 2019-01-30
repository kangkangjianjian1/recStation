package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.adapter.PiclibAdapter;
import recstation.lkk.com.recstation.model.Adress;
import recstation.lkk.com.recstation.model.Piclb;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.PiclbView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseHttpRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;

public class MyDingdanActivity extends BaseHttpRecyclerActivity<Piclb,PiclbView,PiclibAdapter> {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    List<Piclb> mdatalist = new ArrayList<Piclb>();

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MyDingdanActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dingdan);
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
    public void setList(final List<Piclb> list) {
        mdatalist = list;
        setList(new AdapterCallBack<PiclibAdapter>() {

            @Override
            public PiclibAdapter createAdapter() {
                return new PiclibAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }
    @Override
    public void getListAsync(int page) {
        getDingdanData(page);

    }

    @Override
    public List<Piclb> parseArray(String json) {
        return JSON.parseArray(json, Piclb.class);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Logger.e("pppp","pppp");
        Intent intent = DingdanDetailActivity.createIntent(MyDingdanActivity.this);
        startActivity(intent);
        //把返回数据存入Intent
//        String orderId =mdatalist.get(position).getORDER_ID();
//        intent.putExtra("orderId", orderId);
        //设置返回数据
//        Intent i = AddAdressActivity.createIntent(AdressListActivity.this);
//        i.putExtra("selectRec_rectype1",mdatalist.get(position).getRETRIEVETYPE_NAME());
//        i.putExtra("selectRec_miaoshu1",mdatalist.get(position).getBZ());
//        i.putExtra("selectRec_price1",mdatalist.get(position).getPRICE());
//        startActivity(i);
    }


    public void getDingdanData(final int page) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }

        //showProgressDialog("正在核对验证码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.index(URLConfig.CHECKSMS_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        s="[{\"TITLE\":\"预约订单\",\"ORDER_ID\":\"13900034567\",\"PATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/82b68c5920a24f8790a3922c0b496502.png\"},{\"TITLE\":\"回收订单\",\"ORDER_ID\":\"13900034567\",\"PATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/82b68c5920a24f8790a3922c0b496502.png\"},{\"TITLE\":\"系统订单\",\"ORDER_ID\":\"13900034567\",\"PATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/82b68c5920a24f8790a3922c0b496502.png\"}]";
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            Gson gson1 = new Gson();
                            List<Adress> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Adress>>() {
                            }.getType());
                            Logger.e("nnnnn",khsllist2.size()+"fzwk");

                            onHttpResponse(-page, page >= 2 ? null : s, null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                        showShortToast("验证码核对异常，请稍后再试",true);

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
