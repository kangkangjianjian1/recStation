package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.util.AppToast;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.NetUtil;
import recstation.lkk.com.recstation.util.RxHelper;
import recstation.lkk.com.recstation.util.SharedPreferencesUtils;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.util.StringUtil;

public class LoginActivity extends BaseActivity implements OnBottomDragListener, View.OnClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    TextView tv_forget_pwd, tv_newUser_register;
    Button btn_login;

    EditText accountText;
    EditText passwordText;

    private String mUserName = "";
    private String mPassword = "";

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, this);
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    @Override
    public void initView() {
        tv_forget_pwd = findView(R.id.tv_forget_pwd, this);
        tv_newUser_register = findView(R.id.tv_new_user_register, this);
        btn_login = findView(R.id.btn_login, this);
        accountText = findView(R.id.account_editor);
        passwordText = findView(R.id.account_pwd);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:
                startActivity(FindpwdActivity.createIntent(LoginActivity.this));
                break;
            case R.id.tv_new_user_register:
                startActivity(RegisterActivity.createIntent(LoginActivity.this));
                break;
            case R.id.btn_login:
                //网络请求
                handleLogin();
                break;
        }

    }

    private void handleLogin() {

        if (prepareForLogin()) {
            return;
        }
        showProgressDialog("正在登陆");
        // if the data has ready
        mUserName = accountText.getText().toString();
        mPassword = passwordText.getText().toString();

        Subscription subscription = HKEapiManager.getInstances().demoApi.login(URLConfig.LOGIN_URL, mUserName, mPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Logger.e("handleLoginhandleLoginhandleLogin", s);

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");


                            if ("OK".equals(code)) {
                                //登陆成功跳转到首页。
                                showShortToast(message, true);
                                String time = TestUtil.getNowTime();
                                if (jsonObject.has("pd")) {
                                JSONObject pd =jsonObject.getJSONObject("pd");
                                String name = pd.getString("NAME");
                                String path = pd.getString("PATH");
                                String status = pd.getString("STATUS");
                                    HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(),"userheaderpic",path);
                                    HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(),"loginusernicheng",name);
                                    HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(),"loginuserstatus",status);
                                }
                                HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(), "islogin", time);
                                HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(), "loginuser", mUserName);

                                if (jsonObject.has("merchant")) {
                                    JSONObject merchant =jsonObject.getJSONObject("merchant");
                                    String USERNAME2 = merchant.getString("USERNAME");
                                    HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(), "isbusiness", USERNAME2);
                                }
                                onBackPressed();

                            } else {
                                showShortToast(message, true);
                            }







                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private boolean prepareForLogin() {
        if (!NetUtil.isNetConnected(this)) {
            AppToast.showShortText(LoginActivity.this, "没有网络");
            return true;
        }
        if (accountText.getText().length() == 0) {
            AppToast.showShortText(LoginActivity.this, "手机号不能为空!");
            accountText.requestFocus();
            return true;
        }

        if (passwordText.getText().length() == 0) {
            AppToast.showShortText(LoginActivity.this, "密码不能为空");
            passwordText.requestFocus();
            return true;
        }

        if (passwordText.getText().toString().length() < 6) {
            AppToast.showShortText(LoginActivity.this, "密码过短，请重新输入!");
            passwordText.requestFocus();
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
