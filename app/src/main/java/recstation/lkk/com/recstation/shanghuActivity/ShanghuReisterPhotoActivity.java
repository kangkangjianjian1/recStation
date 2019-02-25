package recstation.lkk.com.recstation.shanghuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import recstation.lkk.com.recstation.MainTabActivity;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.NetUtil;
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

public class ShanghuReisterPhotoActivity extends BaseActivity implements View.OnClickListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    String shanghu_area, shanghu_city, shanghu_province, shanghu_account, shanghu_address, shanghu_farenname, shanghu_name, shanghu_mobile ,shanghu_lon,shanghu_lat;
    RequestBody shanghu_area2, shanghu_city2, shanghu_province2, shanghu_account2, shanghu_address2, shanghu_farenname2, shanghu_name2, shanghu_mobile2,shanghu_lon2,shanghu_lat2;
    LinearLayout front_layout2, back_layout2, yingyezhizhao_layout2;
    ImageView front_layout, back_layout, yingyezhizhao_layout;
    String picturePath_front = "";
    String picturePath_back = "";
    String picturePath = "";

    Button btn_save;
    public static Intent createIntent(Context context) {
        return new Intent(context, ShanghuReisterPhotoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanghu_reister_photo);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        front_layout = findView(R.id.front_layout, this);
        front_layout2 = findView(R.id.front_layout2, this);
        back_layout = findView(R.id.back_layout, this);
        back_layout2 = findView(R.id.back_layout2, this);
        yingyezhizhao_layout = findView(R.id.yingyezhizhao_layout, this);
        yingyezhizhao_layout2 = findView(R.id.yingyezhizhao_layout2, this);
        btn_save = findView(R.id.shanghu_photo_savebtn,this);
    }

    @Override
    public void initData() {
        shanghu_area = getIntent().getStringExtra("shanghu_area");

        shanghu_city = getIntent().getStringExtra("shanghu_city");
        shanghu_province = getIntent().getStringExtra("shanghu_province");
        shanghu_account = getIntent().getStringExtra("shanghu_account");
        shanghu_address = getIntent().getStringExtra("shanghu_address");
        shanghu_farenname = getIntent().getStringExtra("shanghu_farenname");
        shanghu_name = getIntent().getStringExtra("shanghu_name");
        shanghu_mobile = getIntent().getStringExtra("shanghu_mobile");
        Logger.e("shanghu_area",shanghu_area);
        Logger.e("shanghu_mobile",shanghu_mobile);
        Logger.e("shanghu_city",shanghu_city);
        Logger.e("shanghu_province",shanghu_province);
        Logger.e("shanghu_account",shanghu_account);
        Logger.e("shanghu_address",shanghu_address);
        Logger.e("shanghu_farenname",shanghu_farenname);
        Logger.e("shanghu_name",shanghu_name);



        shanghu_lon ="36.445566";
        shanghu_lon2 =NetUtil.toRequestBody(shanghu_lon);
        shanghu_lat ="114.685566";
        shanghu_lat2 =NetUtil.toRequestBody(shanghu_lat);
        shanghu_area2 = NetUtil.toRequestBody(shanghu_area);
        shanghu_city2 = NetUtil.toRequestBody(shanghu_city);
        shanghu_province2 = NetUtil.toRequestBody(shanghu_province);
        shanghu_account2 = NetUtil.toRequestBody(shanghu_account);
        shanghu_address2 = NetUtil.toRequestBody(shanghu_address);
        shanghu_farenname2 = NetUtil.toRequestBody(shanghu_area);
        shanghu_name2 = NetUtil.toRequestBody(shanghu_name);
        shanghu_mobile2 = NetUtil.toRequestBody(shanghu_mobile);



    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.front_layout2:
                selectPicture(1);
                break;
            case R.id.front_layout:
                selectPicture(1);
                break;
            case R.id.back_layout2:
                selectPicture(2);
                break;
            case R.id.back_layout:
                selectPicture(2);
                break;
            case R.id.yingyezhizhao_layout2:
                selectPicture(3);
                break;
            case R.id.yingyezhizhao_layout:
                selectPicture(3);
                break;
                case R.id.shanghu_photo_savebtn:
                    uoload();
                break;
        }

    }


    private void selectPicture( int code) {
        toActivity(SelectPictureActivity.createIntent(context), code, false);
    }
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                if (data != null) {
                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH),11);
                }
                break;
            case 11:
                if (data != null) {
                    setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH), R.id.front_layout);
                }
                break;
            case 2:
                if (data != null) {
                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH), 22);
                }
                break;
            case 22:
                if (data != null) {
                    setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH), R.id.back_layout);
                }
                break;
            case 3:
                if (data != null) {
                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH), 33);
                }
                break;
            case 33:
                if (data != null) {
                    setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH), R.id.yingyezhizhao_layout);
                }
                break;

        }
    }

    /**
     * 裁剪图片
     *
     * @param path
     */
    private void cutPicture(String path ,int code) {
        if (StringUtil.isFilePath(path) == false) {
            showShortToast("找不到图片");
            return;
        }



        toActivity(CutPictureActivity.createIntent(context, path
                , DataKeeper.imagePath, "photo" + System.currentTimeMillis(), 200,100)
                , code);
    }

    /**
     * 显示图片
     *
     * @param path
     */
    private void setPicture(String path, int id) {
        if (StringUtil.isFilePath(path) == false) {
            showShortToast("找不到图片");
            return;
        }
        if (id==R.id.front_layout){
            this.picturePath_front = path;
        }else if (id==R.id.back_layout){
            this.picturePath_back = path;
        }else{
            this.picturePath = path;}

        Logger.e("cccccnnnnnn", path);
        Glide.with(context).load(path).into((ImageView) findView(id));
        ((ImageView) findView(id)).setVisibility(View.VISIBLE);
    }




    public void uoload(){
        File file1 = new File(picturePath_front);//filePath 图片地址
        File file2 = new File(picturePath_back);//filePath 图片地址
        File file3 = new File(picturePath);//filePath 图片地址
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        RequestBody imageBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        RequestBody imageBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        RequestBody imageBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
        builder.addFormDataPart("ZCARD", file1.getName(), imageBody1);//imgfile 后台接收图片流的参数名
        builder.addFormDataPart("FCARD", file2.getName(), imageBody2);//imgfile 后台接收图片流的参数名
        builder.addFormDataPart("LICENSE", file3.getName(), imageBody3);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();

        Subscription subscription = HKEapiManager.getInstances().demoApi.BusinessCertificate(URLConfig.BUSSINESS_INDECATE_URL,parts,shanghu_name2,shanghu_mobile2,shanghu_farenname2,shanghu_address2,shanghu_account2,shanghu_province2,shanghu_city2,shanghu_area2,shanghu_lon2,shanghu_lat2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            String code = jsonObject.getString("code");
                            if (code.equals("OK")){
                                showShortToast("认证成功", true);
                                toActivity(MainTabActivity.createIntent(ShanghuReisterPhotoActivity.this));
                                finish();
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
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();
    }
}
