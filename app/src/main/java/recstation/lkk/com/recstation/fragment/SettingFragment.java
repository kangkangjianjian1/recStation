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

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import recstation.lkk.com.recstation.LoginActivity;
import recstation.lkk.com.recstation.MainTabActivity;
import recstation.lkk.com.recstation.MyDingdanActivity;
import recstation.lkk.com.recstation.MypointActivity;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.util.TestUtil;
import zuo.biao.library.base.BaseBottomTabActivity;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.manager.SystemBarTintManager;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;

/**
 * 设置fragment
 *
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class SettingFragment extends BaseFragment implements OnClickListener, OnDialogButtonClickListener {
    //	private static final String TAG = "SettingFragment";
    TextView setting_tv_user_name;
    ImageView setting_ivHead;
    ImageView setting_img_btn_sign;
    LinearLayout setting_ll_lvxin;
    LinearLayout setting_ll_jifen;
    LinearLayout layout_dingdianguanli;
    LinearLayout layout_jifenguanli;
    LinearLayout layout_shangpinduihuan;
    LinearLayout layout_shimingrenzheng;
    LinearLayout layout_xitongshezhi;
    LinearLayout layout_yijianpankui;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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


        setting_ivHead = findView(R.id.setting_ivSettingHead, this);
        setting_tv_user_name = findView(R.id.setting_tv_user_name, this);
        setting_img_btn_sign = findView(R.id.setting_img_btn_sign, this);
        setting_ll_lvxin = findView(R.id.setting_ll_lvxin, this);
        setting_ll_jifen = findView(R.id.setting_ll_jifen, this);
        layout_dingdianguanli = findView(R.id.layout_dingdianguanli, this);
        layout_jifenguanli = findView(R.id.layout_jifenguanli, this);
        layout_shangpinduihuan = findView(R.id.layout_shangpinduihuan, this);
        layout_shimingrenzheng = findView(R.id.layout_shimingrenzheng, this);
        layout_xitongshezhi = findView(R.id.layout_xitongshezhi, this);
        layout_yijianpankui = findView(R.id.layout_yijianpankui, this);

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
                showShortToast("onClick  ivSettingHead");

                startActivity(LoginActivity.createIntent(context));
                break;
            case R.id.setting_tv_user_name:
                showShortToast("onClick  setting_tv_user_name");
                startActivity(LoginActivity.createIntent(context));
                //	toActivity(SettingActivity.createIntent(context));
                break;

            case R.id.setting_img_btn_sign:
                if (TestUtil.IsLogin()) {
                    setting_img_btn_sign.setImageResource(R.drawable.qiandao_n);
                    setting_img_btn_sign.setClickable(false);
                    //发送签到消息到后台
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.setting_ll_jifen:
                if (TestUtil.IsLogin()) {
                    startActivity(MypointActivity.createIntent(context));
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.setting_ll_lvxin:
                if (TestUtil.IsLogin()) {
                    ((MainTabActivity)context).selectFragment(2);
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_dingdianguanli:
                if (TestUtil.IsLogin()) {
                    startActivity(MyDingdanActivity.createIntent(context));
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_jifenguanli:
                if (TestUtil.IsLogin()) {
                    startActivity(MypointActivity.createIntent(context));
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_shangpinduihuan:
                if (TestUtil.IsLogin()) {
                    ((MainTabActivity)context).selectFragment(2);
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_shimingrenzheng:
                if (TestUtil.IsLogin()) {
                    ((MainTabActivity)context).selectFragment(2);
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_xitongshezhi:
                if (TestUtil.IsLogin()) {
                    ((MainTabActivity)context).selectFragment(2);
                }else {
                    startActivity(LoginActivity.createIntent(context));
                }
                //	toActivity(SettingActivity.createIntent(context));
                break;
            case R.id.layout_yijianpankui:
                if (TestUtil.IsLogin()) {
                    ((MainTabActivity)context).selectFragment(2);
                }else {
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


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}