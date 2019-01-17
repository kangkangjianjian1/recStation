package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class FindpwdActivity extends BaseActivity implements View.OnClickListener{
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private Button findpwd_btn_get_yzm;
    private Button btn_findpwd;
    private TimeCount time;
    private EditText findpwd_account_phone;
    private EditText findpwd_account_yzm;
    public static Intent createIntent(Context context) {
        return new Intent(context, FindpwdActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        time = new TimeCount(60000, 1000);
        findpwd_btn_get_yzm = findView(R.id.findpwd_btn_get_yzm, this);
        btn_findpwd = findView(R.id.findpwd_btn_confirm, this);
        findpwd_account_phone = findView(R.id.findpwd_account_photo);
        findpwd_account_yzm = findView(R.id.findpwd_account_yzm);
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
            case R.id.findpwd_btn_get_yzm:
                findpwdGetYzm();
                break;
            case R.id.findpwd_btn_confirm:
                findpwd();
                break;
        }

    }
    public void findpwd() {
        final String username = findpwd_account_phone.getText().toString();
        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
        final String appsms_code = findpwd_account_yzm.getText().toString();
        if (prepareForFindpwd(appsms_id)){
            return;
        }
        showProgressDialog("正在核对验证码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.checksms(URLConfig.CHECKSMS_URL, username,appsms_id,appsms_code)
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

                                Intent intent = SetpwdActivity.createIntent(FindpwdActivity.this);
                                intent.putExtra("username",username);
                                intent.putExtra("appsms_id",appsms_id);
                                intent.putExtra("appsms_code",appsms_code);
                                startActivity(intent);
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


    public void findpwdGetYzm() {
        String phoneNumbers = findpwd_account_phone.getText().toString();
        if (!TestUtil.isPhoneNumber(phoneNumbers)) {
            showShortToast("手机号格式不正确");
            return;
        }
        if (!NetUtil.isNetConnected(this)) {
            showShortToast("没有网络");
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
    private boolean prepareForFindpwd(String appsms_id) {
        if (!NetUtil.isNetConnected(this)) {
            AppToast.showShortText(FindpwdActivity.this,"没有网络！");
            return true;
        }
        if ("".equals(appsms_id)) {
            AppToast.showShortText(FindpwdActivity.this,"您没有申请验证码！");
            findpwd_account_yzm.requestFocus();
            return true;
        }
        if (findpwd_account_phone.getText().length() == 0) {
            AppToast.showShortText(FindpwdActivity.this,"手机号不能为空!");
            findpwd_account_phone.requestFocus();
            return true;
        }
        if (findpwd_account_yzm.getText().length() == 0) {
            AppToast.showShortText(FindpwdActivity.this,"验证码不能为空!");
            findpwd_account_yzm.requestFocus();
            return true;
        }
        return false;
    }




    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            findpwd_btn_get_yzm.setBackground(getResources().getDrawable(R.drawable.btn_yzm_selector2));
            findpwd_btn_get_yzm.setClickable(false);
            findpwd_btn_get_yzm.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            findpwd_btn_get_yzm.setText("重新获取验证码");
            findpwd_btn_get_yzm.setClickable(true);
            findpwd_btn_get_yzm.setBackground(getResources().getDrawable(R.drawable.btn_yzm_selector));

        }
    }
    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }
}
