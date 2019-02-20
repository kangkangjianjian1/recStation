package recstation.lkk.com.recstation.edituseractivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.SharedPreferencesUtils;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.StringUtil;

public class EditUserNameActivity extends BaseActivity implements View.OnClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    Button editname_savebtn;
    EditText editname_inputname;
    ImageView editname_del;

    public static Intent createIntent(Context context) {
        return new Intent(context, EditUserNameActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_name);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.editname_savebtn:

                editName();
                break;
            case R.id.editname_del:
                editname_inputname.setText("");
                break;
        }
    }

    @Override
    public void initView() {
        editname_savebtn = findView(R.id.editname_savebtn, this);
        editname_del = findView(R.id.editname_del, this);
        editname_inputname = findView(R.id.editname_inputname, this);
    }

    @Override
    public void initData() {

        String nicheng = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"loginusernicheng","");

        if (!StringUtil.isEmpty(nicheng)){
            editname_inputname.setText(nicheng);
        }
    }

    @Override
    public void initEvent() {

    }


    public void editName() {

        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");
        final String nicheng = editname_inputname.getText().toString();
        if (StringUtil.isEmpty(nicheng)){
            showShortToast("昵称不能为空");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("USERNAME", userName2);
        hashMap.put("NAME", nicheng);

        Subscription subscription = HKEapiManager.getInstances().demoApi.sendPost(URLConfig.EDITUSERNAME_URL, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            String code = jsonObject.getString("code");
                            if (code.equals("OK")) {
                                HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(), "loginusernicheng", nicheng);

                                showShortToast("昵称修改成功", true);
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
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        String  loginusernicheng = SharedPreferencesUtils.getInstence().getStringData(DemoApplication.getInstance(),"loginusernicheng","");
                        intent.putExtra("loginusernicheng", loginusernicheng);
                        //设置返回数据
                        setResult(RESULT_OK, intent);
                        finish();
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
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        //把返回数据存入Intent
        String  loginusernicheng = SharedPreferencesUtils.getInstence().getStringData(DemoApplication.getInstance(),"loginusernicheng","");
        intent.putExtra("loginusernicheng", loginusernicheng);
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }
}
