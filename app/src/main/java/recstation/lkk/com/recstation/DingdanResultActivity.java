package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.schedulers.EventLoopsScheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.AlertDialog;

public class DingdanResultActivity extends BaseActivity implements AlertDialog.OnDialogButtonClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    HuishouBean hu;
    String sate;
    LinearLayout dingdanresult_userLayout;
    TextView dingdanresult_status;
    TextView dingdanresult_name;
    TextView dingdanresult_mobile;
    TextView dingdanresult_adress;
    TextView dingdanresult_time;
    TextView dingdanresult_typeName;
    TextView dingdanresult_No;

    Button dingdanresult_btn;

    public static Intent createIntent(Context context) {
        return new Intent(context, DingdanResultActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan_result);
        hu = (HuishouBean) getIntent().getSerializableExtra("data");
        sate = getIntent().getStringExtra("state");

        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        dingdanresult_btn = findView(R.id.dingdanresult_btn);
        dingdanresult_userLayout = findView(R.id.dingdanresult_userLayout);
        dingdanresult_status = findView(R.id.dingdanresult_status);
        dingdanresult_name = findView(R.id.dingdanresult_name);
        dingdanresult_mobile = findView(R.id.dingdanresult_mobile);
        dingdanresult_adress = findView(R.id.dingdanresult_adress);
        dingdanresult_time = findView(R.id.dingdanresult_time);
        dingdanresult_typeName = findView(R.id.dingdanresult_typeName);
        dingdanresult_No = findView(R.id.dingdanresult_No);
        if (sate.equals("1")) {
            tvBaseTitle.setText("接收订单");
            dingdanresult_btn.setText("接收订单");
            dingdanresult_userLayout.setVisibility(View.GONE);
            dingdanresult_adress.setText(hu.getPROVINCE() + hu.getCITY() + hu.getAREA());

        } else if (sate.equals("2")) {

            if ("1".equals(hu.getSTATUS())) {
                tvBaseTitle.setText("订单详情");
                dingdanresult_btn.setText("评价");
                dingdanresult_userLayout.setVisibility(View.VISIBLE);
                dingdanresult_adress.setText(hu.getPROVINCE() + hu.getCITY() + hu.getAREA() + hu.getADDRESS());
            }else if ("9".equals(hu.getSTATUS())) {
                tvBaseTitle.setText("订单详情");
                dingdanresult_btn.setText("已完成");
                dingdanresult_userLayout.setVisibility(View.VISIBLE);
                dingdanresult_adress.setText(hu.getPROVINCE() + hu.getCITY() + hu.getAREA() + hu.getADDRESS());
            }else {
                tvBaseTitle.setText("订单详情");
                dingdanresult_btn.setText("取消订单");
                dingdanresult_userLayout.setVisibility(View.VISIBLE);
                dingdanresult_adress.setText(hu.getPROVINCE() + hu.getCITY() + hu.getAREA() + hu.getADDRESS());
            }

        }



    }

    @Override
    public void initData() {

        if (hu.getSTATUS().equals("0")) {
            dingdanresult_status.setText("订单未分配");
            dingdanresult_btn.setClickable(true);
        } else {
            dingdanresult_status.setText("订单已经被预订");
            dingdanresult_btn.setClickable(false);
            dingdanresult_btn.setVisibility(View.INVISIBLE);

        }
        dingdanresult_name.setText(hu.getRETRIEVETYPE_NAME());
        dingdanresult_mobile.setText(hu.getMOBILE());
        dingdanresult_time.setText(hu.getCREATETIME());
        dingdanresult_typeName.setText(hu.getRETRIEVETYPE_NAME());
        dingdanresult_No.setText(hu.getRETRIEVEORDER_ID());

    }

    @Override
    public void initEvent() {
        dingdanresult_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {







                if (sate.equals("1")) {
                    new AlertDialog(DingdanResultActivity.this, "接收订单", "确定接收订单？", true, 0, DingdanResultActivity.this).show();

                } else {
//                    撤销订单
                    if ("1".equals(hu.getSTATUS())) {
                        new AlertDialog(DingdanResultActivity.this, "评价", "确定评价订单？", true, 1, DingdanResultActivity.this).show();

                    }else if ("9".equals(hu.getSTATUS())) {

                        showShortToast("订单已完成");
                    }else {
                        new AlertDialog(DingdanResultActivity.this, "撤销", "确定撤销订单？", true, 2, DingdanResultActivity.this).show();

                    }

                }
            }
        });

    }


    public void jieshoudingdan(String RETRIEVEORDER_ID,String STATUS ,String username ,String APPRAISE) {
        //应该去商户名
       // String username = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");

        Subscription subscription = HKEapiManager.getInstances().demoApi.jieshoudindan(URLConfig.EDITDINGDAN_URL, RETRIEVEORDER_ID, STATUS, username, APPRAISE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Logger.e("getRectypeData:", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if ("OK".equals(code)) {
                                showShortToast(message);
                                finish();

                            } else {
                                showShortToast(message);
                            }


                        } catch (JSONException e) {
                            showShortToast("数据解析异常");
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

    public void chexiaodingdan() {
        //应该去商户名
        String username = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");

        Subscription subscription = HKEapiManager.getInstances().demoApi.chexiaodindan(URLConfig.EDITDINGDAN_URL, hu.getRETRIEVEORDER_ID(), "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Logger.e("getRectypeData:", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if ("OK".equals(code)) {
                                showShortToast(message);
                                finish();

                            } else {
                                showShortToast(message);
                            }

                        } catch (JSONException e) {
                            showShortToast("数据解析异常");
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

    @Override
    public void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }


    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (! isPositive) {
            return;
        }

        switch (requestCode) {
            case 0:

                jieshoudingdan(hu.getRETRIEVEORDER_ID(), "1", "商户大亨", null);
                break;
            case 1:
                jieshoudingdan(hu.getRETRIEVEORDER_ID(), null, null, "pingjia");
                // chexiaodingdan();
               // jieshoudingdan(hu.getRETRIEVEORDER_ID(), null, null, "pingjia");
                break;
            case 2:
                // chexiaodingdan();
                showShortToast("撤销接口未开发");
                break;
            default:
                break;
        }
    }
}