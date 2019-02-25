package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.shanghuActivity.ShanghuBuyActivity;
import recstation.lkk.com.recstation.util.AppToast;
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

public class AdviceActivity extends BaseActivity {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    Button adviced_btn;
    EditText adviced_ed;


    public static Intent createIntent(Context context) {

        return new Intent(context, AdviceActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        adviced_btn = findView(R.id.adviced_btn);
        adviced_ed = findView(R.id.adviced_ed);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        adviced_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitAdvice();
            }
        });

    }

    public void commitAdvice() {

        if (adviced_ed.getText().toString().length() < 1) {
            AppToast.showShortText(AdviceActivity.this,"请重新输入!");
            adviced_ed.requestFocus();
            return;
        }
        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("USERNAME", userName2);
        hashMap.put("OPINION", adviced_ed.getText().toString());
        showProgressDialog("正在提交反馈意见");
        Subscription subscription = HKEapiManager.getInstances().demoApi.sendPost(URLConfig.ADVICE_URL, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if ("OK".equals(code)){
                                //登陆成功跳转到首页。
                                showShortToast(message,true);
//                                Intent intent = LoginActivity.createIntent(SetpwdActivity.this);
////                                intent.putExtra("username",username);
////                                intent.putExtra("appsms_id",appsms_id);
////                                intent.putExtra("appsms_code",appsms_code);
//                                startActivity(intent);
                                finish();

                            }else{
                                showShortToast(message,true);
                            }
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
