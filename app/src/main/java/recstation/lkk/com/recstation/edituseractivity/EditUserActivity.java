package recstation.lkk.com.recstation.edituseractivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.NetUtil;
import recstation.lkk.com.recstation.util.SharedPreferencesUtils;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.ui.CutPictureActivity;
import zuo.biao.library.ui.SelectPictureActivity;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.StringUtil;

public class EditUserActivity extends BaseActivity implements View.OnClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private static final int REQUEST_TO_SELECT_PICTURE = 20;
    private static final int REQUEST_TO_CUT_PICTURE = 21;
    private String picturePath;
    LinearLayout edit_userpic, edit_username;
    ImageView edituser_pic;
    TextView edituser_name;


    public static Intent createIntent(Context context) {
        return new Intent(context, EditUserActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        edit_userpic = findView(R.id.edit_userpic, this);
        edit_username = findView(R.id.edit_username, this);
        edituser_pic = findView(R.id.edituser_pic);
        edituser_name = findView(R.id.edituser_name);




    }

    @Override
    public void initData() {
        String path = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"userheaderpic","");

        if (!StringUtil.isEmpty(path)){
            Glide.with(context).load(path).into(edituser_pic);
        }
        String nicheng = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"loginusernicheng","");

        if (!StringUtil.isEmpty(nicheng)){
            edituser_name.setText(nicheng);
        }
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.edit_username:
                toActivity(EditUserNameActivity.createIntent(context), 456, false);

                break;
            case R.id.edit_userpic:
                selectPicture();
                break;
        }
    }
    private void selectPicture() {
        toActivity(SelectPictureActivity.createIntent(context), REQUEST_TO_SELECT_PICTURE, false);
    }
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 456:
                if (data != null) {
                    if (!StringUtil.isEmpty( data.getStringExtra("loginusernicheng"))){
                        edituser_name.setText(data.getStringExtra("loginusernicheng"));
                    }

                }
                break;
            case REQUEST_TO_SELECT_PICTURE:
                if (data != null) {
                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH));
                }
                break;
            case REQUEST_TO_CUT_PICTURE:
                if (data != null) {
                    setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH));
                }
                break;

        }
    }

    /**
     * 裁剪图片
     *
     * @param path
     */
    private void cutPicture(String path) {
        if (StringUtil.isFilePath(path) == false) {
            showShortToast("找不到图片");
            return;
        }
        this.picturePath = path;

        toActivity(CutPictureActivity.createIntent(context, path
                , DataKeeper.imagePath, "photo" + System.currentTimeMillis(), 200)
                , REQUEST_TO_CUT_PICTURE);
    }

    /**
     * 显示图片
     *
     * @param path
     */
    private void setPicture(String path) {
        if (StringUtil.isFilePath(path) == false) {
            showShortToast("找不到图片");
            return;
        }
        this.picturePath = path;
        Glide.with(context).load(path).into(edituser_pic);

        editPic();
    }


    public void editPic() {

        File file3 = new File(picturePath);//filePath 图片地址
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        RequestBody imageBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
        builder.addFormDataPart("file", file3.getName(), imageBody3);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();
        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");
        RequestBody userName = NetUtil.toRequestBody(userName2);
        Subscription subscription = HKEapiManager.getInstances().demoApi.editPic(URLConfig.EDITUSERPIC_URL, parts, userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            String code = jsonObject.getString("code");
                            if (code.equals("OK")) {
                                HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(),"userheaderpic",picturePath);

                                showShortToast("头像上传成功", true);

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
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        //把返回数据存入Intent
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }
}
