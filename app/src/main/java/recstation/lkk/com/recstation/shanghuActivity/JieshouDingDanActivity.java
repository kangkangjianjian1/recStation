package recstation.lkk.com.recstation.shanghuActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.fragment.DingdanDetailFragment;
import recstation.lkk.com.recstation.util.Logger;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseActivity;

public class JieshouDingDanActivity extends BaseActivity implements OnTabSelectListener {
        CompositeSubscription mCompositeSubscription = new CompositeSubscription();
private ArrayList<Fragment> mFragments = new ArrayList<>();
private final String[] mTitles1 = {
         "进行中","已完成"
        };
private MyDingdanPagerAdapter mAdapter;

public static Intent createIntent(Context context) {
        return new Intent(context, JieshouDingDanActivity.class);
        }

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jieshou_ding_dan);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        }

@Override
public void initView() {
        initFragmentChild();
        }

@Override
public void initData() {

        }

@Override
public void initEvent() {

        }

@Override
public void onTabSelect(int position) {

        }

@Override
public void onTabReselect(int position) {

        }
public class MyDingdanPagerAdapter extends FragmentPagerAdapter {
    public MyDingdanPagerAdapter(FragmentManager fm) {
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


    public void initFragmentChild() {

        Logger.e("ssss","sdecewef");
        for (String title : mTitles1) {

            switch (title) {
//                case  "未接单":
//                    mFragments.add(DingdanDetailFragment.createInstance("0"));
//                    break;
                case  "进行中":
                    mFragments.add(DingdanDetailFragment.createInstance("10"));
                    break;
//                case "待评价":
//                    mFragments.add(DingdanDetailFragment.createInstance("2"));
//                    break;
                case "已完成":
                    mFragments.add(DingdanDetailFragment.createInstance("11"));
                    break;
                default:
                    break;
            }
        }
        ViewPager vp =findView(R.id.dingdan_detail_vp);
        mAdapter =new MyDingdanPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        SlidingTabLayout tabLayout = findView(R.id.dingdan_detail_tab);
        tabLayout.setViewPager(vp);
        tabLayout.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
        vp.setOffscreenPageLimit(4);


    }




    @Override
    public void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();
    }
}
