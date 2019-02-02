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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.DEMO.DemoListFragment;
import recstation.lkk.com.recstation.DEMO.DemoListGirdFragment;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Notice;
import recstation.lkk.com.recstation.model.Piclb;
import recstation.lkk.com.recstation.model.ProductBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;

/**
 * 设置fragment
 *
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class CreditShopFragment extends BaseFragment implements  OnTabSelectListener {
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private Context mContext = getActivity();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles1 = {
            "生活用品", "学习用品","厨房用品","电子产品"
    };
    private MyPagerAdapter mAdapter;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static CreditShopFragment createInstance() {
        return new CreditShopFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //类相关初始化，必须使用<<<<<<<<<<<<<<<<
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.criditshop_fragment);
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
        getProductData();
    }


    @Override
    public void initData() {//必须调用
    }


    @Override
    public void initEvent() {//必须调用


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
    public class MyPagerAdapter extends FragmentPagerAdapter {
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




    private void getProductData() {

        Subscription subscription = HKEapiManager.getInstances().demoApi.index(URLConfig.PRODUCT_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Logger.e("getProductData:", s);


                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            String code = jsonObject1.getString("code");
                            if (!"OK".equals(code)) {
                                showShortToast("网络访问出错，请稍后再试", true);
                            } else {
                                JSONArray malllist = jsonObject1.getJSONArray("malllist");

                                JSONArray shyplist = malllist.getJSONArray(0);
                                JSONArray xxyplist = malllist.getJSONArray(1);
                                JSONArray cfyplist = malllist.getJSONArray(2);
                                JSONArray dzcplist = malllist.getJSONArray(3);

                                Gson gson1 = new Gson();
                                List<ProductBean> shyplist2 = gson1.fromJson(shyplist.toString(), new TypeToken<List<ProductBean>>() {
                                }.getType());

                                List<ProductBean> xxyplist2 = gson1.fromJson(xxyplist.toString(), new TypeToken<List<ProductBean>>() {
                                }.getType());


                                List<ProductBean> cfyplist2 = gson1.fromJson(cfyplist.toString(), new TypeToken<List<ProductBean>>() {
                                }.getType());

                                List<ProductBean> dzcplist2 = gson1.fromJson(dzcplist.toString(), new TypeToken<List<ProductBean>>() {
                                }.getType());

                                initFragmentChild(shyplist2,xxyplist2,cfyplist2,dzcplist2);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Logger.e("lkk", "e.printStackTrace");
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });

        mCompositeSubscription.add(subscription);


    }


    public void initFragmentChild(List<ProductBean> list1,List<ProductBean> list2,List<ProductBean> list3,List<ProductBean> list4) {

        Logger.e("ssss","sdecewef");
        for (String title : mTitles1) {

            switch (title) {
                case  "生活用品":
//                    mFragments.add( UserListFragment.createInstance(UserListFragment.RANGE_ALL));
                    mFragments.add(pro_GirdFragment.createInstance(list1));
                    break;
                case  "学习用品":
                    mFragments.add( pro_GirdFragment.createInstance(list2));
//                    mFragments.add(SettingFragment.createInstance());
                    break;
                case "厨房用品":
                    mFragments.add(pro_GirdFragment.createInstance(list3));
//                    mFragments.add(SettingFragment.createInstance());
                    break;
                case "电子产品":
                    mFragments.add( pro_GirdFragment.createInstance(list4));
//                    mFragments.add(SettingFragment.createInstance());
                    break;
                default:
                    break;
            }
        }
        ViewPager vp =findView(R.id.cre_shop_vp);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
        SlidingTabLayout tabLayout = findView(R.id.cre_shop_tab);
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