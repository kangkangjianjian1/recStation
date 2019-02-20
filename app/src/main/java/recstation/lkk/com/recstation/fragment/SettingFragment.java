/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package recstation.lkk.com.recstation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import recstation.lkk.com.recstation.AboutUsActivity;
import recstation.lkk.com.recstation.DingdanDetailActivity;
import recstation.lkk.com.recstation.LoginActivity;
import recstation.lkk.com.recstation.MainTabActivity;
import recstation.lkk.com.recstation.MyDingdanActivity;
import recstation.lkk.com.recstation.MypointActivity;
import recstation.lkk.com.recstation.QRCodeActivity;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.ShanghuReisterPhotoActivity;
import recstation.lkk.com.recstation.ShanghuguanliActivity;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.edituseractivity.EditUserActivity;
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
import zuo.biao.library.base.BaseBottomTabActivity;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.manager.SystemBarTintManager;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;
import zuo.biao.library.ui.CutPictureActivity;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.ui.EditTextInfoWindow;
import zuo.biao.library.ui.SelectPictureActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.StringUtil;

/**
 * 设置fragment
 *
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class SettingFragment extends BaseFragment implements OnClickListener, OnDialogButtonClickListener {
    //	private static final String TAG = "SettingFragment";
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    TextView setting_tv_user_name;
    CircleImageView setting_ivHead;
    ImageView setting_img_btn_sign;
    LinearLayout setting_ll_lvxin;
    LinearLayout setting_ll_jifen;
    LinearLayout layout_dingdianguanli;
    LinearLayout layout_jifenguanli;
    LinearLayout layout_huishoubaojia;
    LinearLayout layout_shanghuguanli;
    LinearLayout layout_myerweima;
    LinearLayout layout_xitongshezhi;
    LinearLayout layout_yijianpankui;
    LinearLayout layout_guanyuwomen;
    ScrollView setting_scrollview;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private static final int REQUEST_TO_SELECT_PICTURE = 20;
    private static final int REQUEST_TO_CUT_PICTURE = 21;
    private String picturePath;

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static SettingFragment createInstance() {
        return new SettingFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.setting_fragment);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Override
    public void initView() {//必须调用


        setting_scrollview = findView(R.id.setting_scrollview, this);
        setting_ivHead = findView(R.id.setting_ivSettingHead, this);
        setting_tv_user_name = findView(R.id.setting_tv_user_name, this);
        setting_img_btn_sign = findView(R.id.setting_img_btn_sign, this);
        setting_ll_lvxin = findView(R.id.setting_ll_lvxin, this);
        setting_ll_jifen = findView(R.id.setting_ll_jifen, this);
        layout_dingdianguanli = findView(R.id.layout_dingdianguanli, this);
        layout_shanghuguanli = findView(R.id.layout_shanghuguanli, this);
        layout_jifenguanli = findView(R.id.layout_jifenguanli, this);
        layout_huishoubaojia = findView(R.id.layout_huishoubaojia, this);
        layout_myerweima = findView(R.id.layout_myerweima, this);
        layout_xitongshezhi = findView(R.id.layout_xitongshezhi, this);
        layout_yijianpankui = findView(R.id.layout_yijianpankui, this);
        layout_guanyuwomen = findView(R.id.layout_guanyuwomen, this);

//访问网络查看签到状态


//		SystemBarTintManager tintManager = new SystemBarTintManager(context);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setStatusBarTintResource(zuo.biao.library.R.color.topbar_bg_white);//状态背景色，可传drawable资源
        // 状态栏沉浸，4.4+生效 >>>>>>>>>>>>>>>>>
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须调用
        String path = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"userheaderpic","");

        if (!StringUtil.isEmpty(path)){
            Glide.with(context).load(path).into(setting_ivHead);
        }
        String nicheng = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"loginusernicheng","");

        if (!StringUtil.isEmpty(nicheng)){
            setting_tv_user_name.setText(nicheng);
        }
    }


    private void logout() {
        context.finish();
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

//		findView(R.id.llSettingSetting).setOnClickListener(this);
//		findView(R.id.llSettingAbout).setOnClickListener(this);
//		findView(R.id.llSettingLogout).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        String path = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"userheaderpic","");

        if (!StringUtil.isEmpty(path)){
            Glide.with(context).load(path).into(setting_ivHead);
        }
        String nicheng = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"loginusernicheng","");

        if (!StringUtil.isEmpty(nicheng)){
            setting_tv_user_name.setText(nicheng);
        }

    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (!isPositive) {
            return;
        }

        switch (requestCode) {
            case 0:
                logout();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.setting_ivSettingHead:
                if (TestUtil.IsLogin()) {
                    //selectPicture();
                    startActivityForResult(EditUserActivity.createIntent(context),123);
                    //发送签到消息到后台
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                break;
            case R.id.setting_tv_user_name:
                if (TestUtil.IsLogin()) {
                    //selectPicture();
                    startActivityForResult(EditUserActivity.createIntent(context),123);
                    //发送签到消息到后台
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                break;

            case R.id.setting_img_btn_sign:
                if (TestUtil.IsLogin()) {
                      HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(), "loginuser", "no");
                    setting_img_btn_sign.setImageResource(R.drawable.qiandao_n);
                    setting_img_btn_sign.setClickable(false);
                    //发送签到消息到后台
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.setting_ll_jifen:
                if (TestUtil.IsLogin()) {
                    startActivity(MypointActivity.createIntent(context));
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.setting_ll_lvxin:
                if (TestUtil.IsLogin()) {
                    showShortToast("功能正在开发");
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_dingdianguanli:
                if (TestUtil.IsLogin()) {
                    startActivity(MyDingdanActivity.createIntent(context));
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_jifenguanli:
                if (TestUtil.IsLogin()) {
                    startActivity(MypointActivity.createIntent(context));
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_huishoubaojia:
                if (TestUtil.IsLogin()) {
                   showShortToast("功能正在开发");
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
                case R.id.layout_shanghuguanli:
                if (TestUtil.IsLogin()) {
                    startActivity(ShanghuguanliActivity.createIntent(context));
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_myerweima:
                if (TestUtil.IsLogin()) {
                    CommonUtil.toActivity(context, QRCodeActivity.createIntent(context, 1));
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_xitongshezhi:
                if (TestUtil.IsLogin()) {
                   // CommonUtil.toActivity(context, AboutUsActivity.createIntent(context));
                    showShortToast("系统设置正在开发");
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_yijianpankui:
                if (TestUtil.IsLogin()) {
                    showShortToast("功能正在开发");
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_guanyuwomen:
                if (TestUtil.IsLogin()) {
                    CommonUtil.toActivity(context, AboutUsActivity.createIntent(context));
                } else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
//			case R.id.llSettingAbout:
//				//toActivity(AboutActivity.createIntent(context));
//				break;
//			case R.id.llSettingLogout:
//				new AlertDialog(context, "退出登录", "确定退出登录？", true, 0, this).show();
//				break;
            default:
                break;
        }
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

//    private void selectPicture() {
//        toActivity(SelectPictureActivity.createIntent(context), REQUEST_TO_SELECT_PICTURE, false);
//    }
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 123:
                if (data != null) {
                String path = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"userheaderpic","");

                if (!StringUtil.isEmpty(path)){
                    Glide.with(context).load(path).into(setting_ivHead);
                }
                    String nicheng = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"loginusernicheng","");

                    if (!StringUtil.isEmpty(nicheng)){
                        setting_tv_user_name.setText(nicheng);
                    }
//                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH));
                }
                break;


        }
    }

//    /**
//     * 裁剪图片
//     *
//     * @param path
//     */
//    private void cutPicture(String path) {
//        if (StringUtil.isFilePath(path) == false) {
//            showShortToast("找不到图片");
//            return;
//        }
//        this.picturePath = path;
//
//        toActivity(CutPictureActivity.createIntent(context, path
//                , DataKeeper.imagePath, "photo" + System.currentTimeMillis(), 200)
//                , REQUEST_TO_CUT_PICTURE);
//    }
//
//    /**
//     * 显示图片
//     *
//     * @param path
//     */
//    private void setPicture(String path) {
//        if (StringUtil.isFilePath(path) == false) {
//            showShortToast("找不到图片");
//            return;
//        }
//        this.picturePath = path;
//
//        setting_scrollview.smoothScrollTo(0, 0);
//        Logger.e("cccccnnnnnn", path);
//        Glide.with(context).load(path).into(setting_ivHead);
//
//        editPic();
//
//    }
//
//
//    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//    public void editPic(){
//
//        File file3 = new File(picturePath);//filePath 图片地址
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//        RequestBody imageBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
//        builder.addFormDataPart("file", file3.getName(), imageBody3);//imgfile 后台接收图片流的参数名
//
//        List<MultipartBody.Part> parts = builder.build().parts();
//       String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");
//       RequestBody userName = NetUtil.toRequestBody(userName2);
//        Subscription subscription = HKEapiManager.getInstances().demoApi.editPic(URLConfig.EDITUSERPIC_URL,parts,userName)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(s);
//
//                            String code = jsonObject.getString("code");
//                            if (code.equals("OK")){
//                                showShortToast("头像上传成功", true);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
//                        showShortToast("网络异常，请稍后再试", true);
//
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
////                        Logger.e("lkk", "onCompleted");
//                    }
//                });
//        mCompositeSubscription.add(subscription);
//    }

//    public void editName(){
//
//        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");
//
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("USERNAME",userName2);
//        hashMap.put("NAME","我是评论");
//
//        Subscription subscription = HKEapiManager.getInstances().demoApi.sendPost(URLConfig.EDITUSERNAME_URL,hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(s);
//
//                            String code = jsonObject.getString("code");
//                            if (code.equals("OK")){
//                                showShortToast("头像上传成功", true);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
//                        showShortToast("网络异常，请稍后再试", true);
//
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
////                        Logger.e("lkk", "onCompleted");
//                    }
//                });
//        mCompositeSubscription.add(subscription);
//    }
//
//    @Override
//    public void onDestroy() {
//        mCompositeSubscription.clear();
//        super.onDestroy();
//    }

//    /**编辑图片名称
//     */
//    private void editName(boolean toWindow) {
//        if (toWindow) {
//            intent = EditTextInfoWindow.createIntent(context, EditTextInfoWindow.TYPE_NICK
//                    , "照片名称", StringUtil.getTrimedString(tvDemoMainHeadName), getPackageName());
//        } else {
//            intent = EditTextInfoActivity.createIntent(context, EditTextInfoActivity.TYPE_NICK
//                    , "照片名称", StringUtil.getTrimedString(tvDemoMainHeadName));
//        }
//
//        toActivity(intent, REQUEST_TO_EDIT_TEXT_INFO, ! toWindow);
//    }

}