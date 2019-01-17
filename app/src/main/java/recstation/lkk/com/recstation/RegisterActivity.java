package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.util.AppToast;
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
import zuo.biao.library.interfaces.OnBottomDragListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private Button register_btn_get_yzm;
    private Button btn_register;
    private TimeCount time;
    private EditText register_account_phone;
    private EditText register_account_yzm;
    private EditText register_account_pwd;

    private String registerName = "";
    private String registerPassword = "";


    public static Intent createIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        time = new TimeCount(60000, 1000);
        register_btn_get_yzm = findView(R.id.register_btn_get_yzm, this);
        btn_register = findView(R.id.btn_register, this);
        register_account_phone = findView(R.id.register_account_phone);
        register_account_yzm = findView(R.id.register_account_yzm);
        register_account_pwd = findView(R.id.register_account_pwd);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_get_yzm:
                getYzm();
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }

    }


    public void getYzm() {
        String phoneNumbers = register_account_phone.getText().toString();
        Logger.e("wcnmd",phoneNumbers);
        if (!TestUtil.isPhoneNumber(phoneNumbers)) {
            showShortToast("手机号格式不正确");
            Logger.e("wcnmd","手机号格式不正确");
            return;
        }
        if (!NetUtil.isNetConnected(this)) {
            showShortToast("没有网络");
            Logger.e("wcnmd","没有网络");
            return;
        }

        showProgressDialog("正在申请验证码");
        time.start();
        Subscription subscription = HKEapiManager.getInstances().demoApi.getyzm(URLConfig.GETSMS_URL, phoneNumbers, "1")
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
                            String APPSMS_ID = jsonObject.getString("APPSMS_ID");
                            if ("OK".equals(code)){
                                //登陆成功跳转到首页。
                                showShortToast(message,true);
                                HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(),"APPSMS_ID",APPSMS_ID);

                            }else{
                                showShortToast(message,true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showShortToast("申请验证码异常，请稍后再试",true);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });

        mCompositeSubscription.add(subscription);


    }

    public void registerUser() {
        String username = register_account_phone.getText().toString();
        String password = register_account_pwd.getText().toString();
        String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
        String appsms_code = register_account_yzm.getText().toString();
        if (prepareForRegister(appsms_id)){
            return;
        }
        showProgressDialog("正在注册");
        Subscription subscription = HKEapiManager.getInstances().demoApi.register(URLConfig.REGISTER_URL, username, password,appsms_id,appsms_code)
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
                                onBackPressed();
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
                        showShortToast("注册新用户异常，请稍后再试",true);

                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });

        mCompositeSubscription.add(subscription);


    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            register_btn_get_yzm.setBackground(getResources().getDrawable(R.drawable.btn_yzm_selector2));
            register_btn_get_yzm.setClickable(false);
            register_btn_get_yzm.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            register_btn_get_yzm.setText("重新获取验证码");
            register_btn_get_yzm.setClickable(true);
            register_btn_get_yzm.setBackground(getResources().getDrawable(R.drawable.btn_yzm_selector));

        }
    }
    private boolean prepareForRegister(String appsms_id) {
        if (!NetUtil.isNetConnected(this)) {
            AppToast.showShortText(RegisterActivity.this,"没有网络！");
            return true;
        }
        if ("".equals(appsms_id)) {
            AppToast.showShortText(RegisterActivity.this,"您没有申请验证码！");
            register_account_phone.requestFocus();
            return true;
        }
        if (register_account_phone.getText().length() == 0) {
            AppToast.showShortText(RegisterActivity.this,"手机号不能为空!");
            register_account_phone.requestFocus();
            return true;
        }
        if (register_account_yzm.getText().length() == 0) {
            AppToast.showShortText(RegisterActivity.this,"验证码不能为空!");
            register_account_yzm.requestFocus();
            return true;
        }

        if (register_account_pwd.getText().length() == 0) {
            AppToast.showShortText(RegisterActivity.this,"密码不能为空");
            register_account_pwd.requestFocus();
            return true;
        }

        if (register_account_pwd.getText().toString().length() < 6) {
            AppToast.showShortText(RegisterActivity.this,"密码过短，请重新输入!");
            register_account_pwd.requestFocus();
            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }
}
