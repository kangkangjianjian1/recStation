package recstation.lkk.com.recstation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.fragment.FirstListGirdFragment;
import recstation.lkk.com.recstation.fragment.CreditShopFragment;
import recstation.lkk.com.recstation.fragment.RecMessageFragment;
import recstation.lkk.com.recstation.fragment.SettingFragment;
import recstation.lkk.com.recstation.fragment.UserRecyclerFragment;
import recstation.lkk.com.recstation.fragment.rec_GirdFragment;
import recstation.lkk.com.recstation.util.HKEapiManager;
import zuo.biao.library.base.BaseBottomTabActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;

/**
 * 应用主页
 *
 * @author Lemon
 * @use MainTabActivity.createIntent(...)
 */
public class MainTabActivity extends BaseBottomTabActivity implements OnBottomDragListener {
    private static final String TAG = "MainTabActivity";

    ImageButton ivbtn_dingwei;
    Button btn_dingwei;
    ImageButton ivbtn_saoyisao;
    RelativeLayout main_TabTopbar;
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainTabActivity.class);
    }


    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity, this);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {// 必须调用
        super.initView();
        exitAnim = R.anim.bottom_push_out;
        ivbtn_dingwei = findView(R.id.ivbtn_dingwei);
        ivbtn_saoyisao = findView(R.id.ivbtn_saoyisao);
        btn_dingwei = findView(R.id.btn_dingwei);
        main_TabTopbar = findView(R.id.rlBottomTabTopbar);
    }


    @Override
    protected int[] getTabClickIds() {
        return new int[]{R.id.llBottomTabTab0, R.id.llBottomTabTab1, R.id.llBottomTabTab2, R.id.llBottomTabTab3, R.id.llBottomTabTab4};
    }

    @Override
    protected int[][] getTabSelectIds() {
        return new int[][]{
                new int[]{R.id.ivBottomTabTab0, R.id.ivBottomTabTab1, R.id.ivBottomTabTab2, R.id.ivBottomTabTab3, R.id.ivBottomTabTab4},//顶部图标
                new int[]{R.id.tvBottomTabTab0, R.id.tvBottomTabTab1, R.id.tvBottomTabTab2, R.id.tvBottomTabTab3, R.id.tvBottomTabTab4}//底部文字
        };
    }

    @Override
    public int getFragmentContainerResId() {
        return R.id.flMainTabFragmentContainer;
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 4:
                return SettingFragment.createInstance();
            case 1:
                return rec_GirdFragment.createInstance();
            case 2:
                return RecMessageFragment.createInstance();
            case 3:
                return CreditShopFragment.createInstance();
            default:
                return FirstListGirdFragment.createInstance();
        }
    }


    private static final String[] TAB_NAMES = {"首页", "回收", "消息", "积分商城", "我的"};

    @Override
    protected void selectTab(int position) {
        //导致切换时闪屏，建议去掉BottomTabActivity中的topbar，在fragment中显示topbar
        //		rlBottomTabTopbar.setVisibility(position == 2 ? View.GONE : View.VISIBLE);

        tvBaseTitle.setText(TAB_NAMES[position]);

        if (position != 0) {
            ivbtn_saoyisao.setVisibility(View.GONE);
            ivbtn_dingwei.setVisibility(View.GONE);
            btn_dingwei.setVisibility(View.GONE);
        } else {
            ivbtn_saoyisao.setVisibility(View.VISIBLE);
            ivbtn_dingwei.setVisibility(View.VISIBLE);
            btn_dingwei.setVisibility(View.VISIBLE);
        }

        if (position == 4) {
            main_TabTopbar.setVisibility(View.GONE);
        } else {
            main_TabTopbar.setVisibility(View.VISIBLE);

        }
    }


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {// 必须调用
        super.initData();

    }


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {// 必须调用
        super.initEvent();
        ivbtn_dingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toActivity(MapActivity.createIntent(MainTabActivity.this));
            }
        });
    }

    //将Activity的onDragBottom事件传递到Fragment，非必要>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    HKEapiManager.getInstances().preferences.putStringData(DemoApplication.getInstance(), "loginuser", "no");

                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {

    }
    //双击手机返回键退出>>>>>>>>>>>>>>>>>>>>>

    @Override
    public void onForwardClick(View v) {
        super.onForwardClick(v);
        startActivity(ScanActivity.createIntent(MainTabActivity.this));
    }

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // 内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    // 内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}