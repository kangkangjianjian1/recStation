package recstation.lkk.com.recstation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.application.DemoApplication;
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

public class SetpwdActivity extends BaseActivity implements View.OnClickListener{
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private Button setpwd_btn_confirm;
    private EditText setpwd_account_pwd;
    private String username,appsms_id,appsms_code,password;

    public static Intent createIntent(Context context) {
        return new Intent(context, SetpwdActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpwd);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        setpwd_btn_confirm = findView(R.id.setpwd_btn_confirm,this);
        setpwd_account_pwd = findView(R.id.setpwd_account_pwd);

    }

    @Override
    public void initData() {
        username = getIntent().getStringExtra("username");
        appsms_id = getIntent().getStringExtra("appsms_id");
        appsms_code = getIntent().getStringExtra("appsms_code");
        password = setpwd_account_pwd.getText().toString().trim();



    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setpwd_btn_confirm:
                setpwd();
                break;
        }

    }



    public void setpwd() {

        if (setpwd_account_pwd.getText().toString().length() < 6) {
            AppToast.showShortText(SetpwdActivity.this,"密码过短，请重新输入!");
            setpwd_account_pwd.requestFocus();
            return;
        }
        showProgressDialog("正在设置新的密码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.setpwd(URLConfig.SETDPWD_URL, username,setpwd_account_pwd.getText().toString().trim(),appsms_id,appsms_code)
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
