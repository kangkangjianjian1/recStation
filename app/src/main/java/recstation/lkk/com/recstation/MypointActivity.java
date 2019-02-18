package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.facebook.Facebook;
import cn.jiguang.share.facebook.messenger.FbMessenger;
import cn.jiguang.share.jchatpro.JChatPro;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.twitter.Twitter;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.fragment.CreditShopFragment;
import recstation.lkk.com.recstation.fragment.PointRecyclerFragment;
import recstation.lkk.com.recstation.fragment.pro_GirdFragment;
import recstation.lkk.com.recstation.uitool.ShareBoard;
import recstation.lkk.com.recstation.uitool.ShareBoardlistener;
import recstation.lkk.com.recstation.uitool.SnsPlatform;
import recstation.lkk.com.recstation.util.Logger;
import zuo.biao.library.base.BaseActivity;

public class MypointActivity extends BaseActivity implements View.OnClickListener, OnTabSelectListener {
    private int mAction = Platform.ACTION_SHARE;
    private ShareBoard mShareBoard;
    TextView my_point_point;
    TextView my_point_add;
    ViewPager my_point_vp;
    SlidingTabLayout my_point_tab;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles1 = {
            "全部记录", "收入记录","支出记录"
    };
    private MyPagerAdapter2 mAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(MypointActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };
    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MypointActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypoint);
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    @Override
    public void initView() {
        my_point_point = findView(R.id.my_point_point);
        my_point_add = findView(R.id.my_point_add);
        my_point_vp = findView(R.id.my_point_vp);
        my_point_tab = findView(R.id.my_point_tab);

        for (String title : mTitles1) {

            switch (title) {
                case "全部记录":
                    mFragments.add(PointRecyclerFragment.createInstance());
                    break;
                case "收入记录":
                    mFragments.add(PointRecyclerFragment.createInstance());
                    break;
                case "支出记录":
                    mFragments.add(PointRecyclerFragment.createInstance());
                    break;
                default:
                    break;
            }
        }

        FragmentManager manager = getSupportFragmentManager();
        mAdapter = new MyPagerAdapter2(manager);
        my_point_vp.setAdapter(mAdapter);
        my_point_tab.setViewPager(my_point_vp);
        my_point_tab.setOnTabSelectListener(this);
        my_point_vp.setCurrentItem(0);
        my_point_vp.setOffscreenPageLimit(4);



    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    public class MyPagerAdapter2 extends FragmentPagerAdapter {
        public MyPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles1[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onForwardClick(View v) {


        showBroadView();


    }




    private void showBroadView() {
        Logger.e("LKKLKK","不是空的吧00");
        if (mShareBoard == null) {
            mShareBoard = new ShareBoard(this);
            List<String> platforms = JShareInterface.getPlatformList();
            Logger.e("LKKLKK",platforms.size()+"不是空的吧");
            if (platforms != null) {
                Logger.e("LKKLKK",platforms.size()+"不是空的吧2");
                Iterator var2 = platforms.iterator();
                while (var2.hasNext()) {
                    String temp = (String) var2.next();
                    SnsPlatform snsPlatform = createSnsPlatform(temp);
                    mShareBoard.addPlatform(snsPlatform);
                }
            }
            mShareBoard.setShareboardclickCallback(mShareBoardlistener);
        }
        mShareBoard.show();
    }

    private ShareBoardlistener mShareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, String platform) {

            switch (mAction) {
                case Platform.ACTION_SHARE:
                  //  progressDialog.show();
                    showProgressDialog("正在分享...");
                    //这里以分享链接为例
                    ShareParams shareParams = new ShareParams();
                    shareParams.setUrl("http://www.baidu.com");
                    shareParams.setShareType(Platform.SHARE_IMAGE);
                    //twitter支持单张、多张（最多4张本地图片）
                    if(Twitter.Name.equals(platform)){
                        String[] array = new String[]{ DemoApplication.ImagePath, DemoApplication.ImagePath};
                        shareParams.setImageArray(array);
                        //shareParams.setImagePath(MyApplication.ImagePath);
                    }else{
                        shareParams.setImagePath(DemoApplication.ImagePath);
                    }
                    JShareInterface.share(platform, shareParams, mShareListener);
//                    ShareParams shareParams = new ShareParams();
//                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
//                    shareParams.setTitle(ShareTypeActivity.share_title);
//                    shareParams.setText(ShareTypeActivity.share_text);
//                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
//                    shareParams.setUrl(ShareTypeActivity.share_url);
//                    shareParams.setImagePath(MyApplication.ImagePath);
//                    JShareInterface.share(platform, shareParams, mShareListener);
                    break;
                default:
                    break;
            }
        }
    };

    private PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享成功";
                handler.sendMessage(message);
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            Logger.e("LKKLKK", "error:" + errorCode + ",msg:" + error);
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享失败:" + error.getMessage() + "---" + errorCode;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onCancel(Platform platform, int action) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享取消";
                handler.sendMessage(message);
            }
        }
    };



    public static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (platformName.equals(Wechat.Name)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (platformName.equals(WechatMoments.Name)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (platformName.equals(WechatFavorite.Name)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";

        } else if (platformName.equals(SinaWeibo.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_key";
        } else if (platformName.equals(SinaWeiboMessage.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_msg_key";
        } else if (platformName.equals(QQ.Name)) {
            mIcon = "jiguang_socialize_qq";
            mGrayIcon = "jiguang_socialize_qq";
            mShowWord = "jiguang_socialize_text_qq_key";

        } else if (platformName.equals(QZone.Name)) {
            mIcon = "jiguang_socialize_qzone";
            mGrayIcon = "jiguang_socialize_qzone";
            mShowWord = "jiguang_socialize_text_qq_zone_key";
        } else if (platformName.equals(Facebook.Name)) {
            mIcon = "jiguang_socialize_facebook";
            mGrayIcon = "jiguang_socialize_facebook";
            mShowWord = "jiguang_socialize_text_facebook_key";
        } else if (platformName.equals(FbMessenger.Name)) {
            mIcon = "jiguang_socialize_messenger";
            mGrayIcon = "jiguang_socialize_messenger";
            mShowWord = "jiguang_socialize_text_messenger_key";
        }else if (Twitter.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_twitter";
            mGrayIcon = "jiguang_socialize_twitter";
            mShowWord = "jiguang_socialize_text_twitter_key";
        } else if (platformName.equals(JChatPro.Name)) {
            mShowWord = "jiguang_socialize_text_jchatpro_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }



}
