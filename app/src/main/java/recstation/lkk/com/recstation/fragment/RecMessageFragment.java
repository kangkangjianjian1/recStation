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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import recstation.lkk.com.recstation.R;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;

/**
 * 设置fragment
 *
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class RecMessageFragment extends BaseFragment implements OnClickListener, OnDialogButtonClickListener, OnTabSelectListener {
    //	private static final String TAG = "SettingFragment";
    private Context mContext = getActivity();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles1 = {
            "系统消息", "订单消息"
    };
    private MyPagerAdapter mAdapter;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static RecMessageFragment createInstance() {
        return new RecMessageFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.recmessage_fragment);
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
        for (String title : mTitles1) {

            switch (title) {
                case "系统消息":
                    mFragments.add( SystemMsgRecyclerFragment.createInstance());
                    break;
                case "订单消息":
                    mFragments.add(DingdanMsgRecyclerFragment.createInstance());
                    break;
                default:
                    break;
            }
        }
        ViewPager vp =findView(R.id.rec_msg_vp);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());

        vp.setAdapter(mAdapter);
        SlidingTabLayout tabLayout = findView(R.id.rec_msg_tab);
        tabLayout.setViewPager(vp);
        tabLayout.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
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
//            case R.id.ivSettingHead:
//                showShortToast("onClick  ivSettingHead");
//                break;
//            case R.id.llSettingSetting:
//                //	toActivity(SettingActivity.createIntent(context));
//                break;
//            case R.id.llSettingAbout:
//                //toActivity(AboutActivity.createIntent(context));
//                break;
//            case R.id.llSettingLogout:
//                new AlertDialog(context, "退出登录", "确定退出登录？", true, 0, this).show();
//                break;
            default:
                break;
        }
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
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