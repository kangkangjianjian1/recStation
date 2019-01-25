package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import recstation.lkk.com.recstation.fragment.CreditShopFragment;
import recstation.lkk.com.recstation.fragment.PointRecyclerFragment;
import recstation.lkk.com.recstation.fragment.pro_GirdFragment;
import zuo.biao.library.base.BaseActivity;

public class MypointActivity extends BaseActivity implements View.OnClickListener, OnTabSelectListener {

    TextView my_point_point;
    TextView my_point_add;
    ViewPager my_point_vp;
    SlidingTabLayout my_point_tab;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles1 = {
            "全部记录", "收入记录","支出记录"
    };
    private MyPagerAdapter2 mAdapter;

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


}
