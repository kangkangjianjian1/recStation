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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recstation.lkk.com.recstation.GonggaoDetailActivity;
import recstation.lkk.com.recstation.LoginActivity;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.RecdetailActivity;
import recstation.lkk.com.recstation.animations.MyDescriptionAnimation;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Notice;
import recstation.lkk.com.recstation.model.Piclb;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.NoticeView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.util.Log;

import static recstation.lkk.com.recstation.util.TestUtil.IsLogin;
import static zuo.biao.library.util.CommonUtil.toActivity;


/**
 * 使用方法：复制>粘贴>改名>改代码
 */

/**
 * 列表Fragment示例
 *
 * @author Lemon
 * @use new DemoListFragment(),具体参考.DemoTabActivity(getFragment方法内)
 */
public class FirstListGirdFragment extends BaseFragment implements View.OnClickListener {
    //	private static final String TAG = "DemoListFragment";
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
   NoticeView first_noticeview;
    SliderLayout mSlider;
    Button btn_yuyue, btn_zhoubian;
    //    TextView tv;
    public static int a = 1;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static FirstListGirdFragment createInstance() {
        return new FirstListGirdFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.first_list_gird_fragment);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;//返回值必须为view
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须在onCreateView方法内调用
        first_noticeview = findView(R.id.first_noticeview);
        btn_yuyue = findView(R.id.btn_yuyue);
        btn_zhoubian = findView(R.id.btn_zhoubian);
//        tv = findView(R.id.gird_title);
        mSlider = findView(R.id.slider);
//        rec_GirdFragment1 fragment1 = rec_GirdFragment1.createInstance(null);
//        rec_GirdFragment2 fragment2 = rec_GirdFragment2.createInstance(null);
//        rec_GirdFragment3 fragment3 = rec_GirdFragment3.createInstance(null);
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.rec_FragmentContainer1, fragment1);
//        transaction.replace(R.id.rec_FragmentContainer2, fragment2);
//        transaction.replace(R.id.rec_FragmentContainer3, fragment3);
//        transaction.commit();

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须在onCreateView方法内调用
        getBannerData();

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();

        super.onStop();
    }


//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        btn_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  btn_zhoubian.setBackgroundResource(R.drawable.btn_big_selector2);
                if(IsLogin()){
//                    btn_yuyue.setBackgroundResource(R.drawable.btn_big_selector);
                    startActivity(RecdetailActivity.createIntent(context));
                }else {
                    toActivity(LoginActivity.createIntent(context));
                }

                //更新页面
            }
        });
        btn_zhoubian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                btn_yuyue.setBackgroundResource(R.drawable.btn_big_selector2);
//                btn_zhoubian.setBackgroundResource(R.drawable.btn_big_selector);
                //更新页面
            }
        });
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 获取图片地址，仅供测试用
     *
     * @return
     */

    private void getBannerData() {

        Subscription subscription = HKEapiManager.getInstances().demoApi.index(URLConfig.INDEX_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Logger.e("getBannerDatagetBannerDatagetBannerData", s);


                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            String code = jsonObject1.getString("code");
                            if (!"OK".equals(code)) {
                                showShortToast("网络访问出错，请稍后再试", true);
                            } else {
                                JSONArray lbtlist = jsonObject1.getJSONArray("lbtlist");
                                Gson gson1 = new Gson();
                                List<Piclb> list = gson1.fromJson(lbtlist.toString(), new TypeToken<List<Piclb>>() {
                                }.getType());
                                Logger.e("下载的常见问题list长度", list.size() + "");
                                initLunBo(list);

                                JSONArray noticelist = jsonObject1.getJSONArray("noticelist");

                                List<Notice> noticelist2 = gson1.fromJson(noticelist.toString(), new TypeToken<List<Notice>>() {
                                }.getType());
                                Logger.e("下载的noticelist2长度", noticelist2.size() + "");
                                initNotice(noticelist2);
                                JSONArray retrievelist = jsonObject1.getJSONArray("retrievelist");

                                JSONArray khsllist = retrievelist.getJSONArray(0);
                                JSONArray wrwllist = retrievelist.getJSONArray(1);
                                JSONArray gcllist = retrievelist.getJSONArray(2);

//                                JSONArray khsllist = jsonObject2.getJSONArray("khsllist");
                                List<HuishouBean> khsllist2 = gson1.fromJson(khsllist.toString(), new TypeToken<List<HuishouBean>>() {
                                }.getType());
                                Logger.e("khsllist2", khsllist2.size()+"fzwk");
//                                JSONArray wrwllist = jsonObject3.getJSONArray("wrwllist");
                                List<HuishouBean> wrwllist2 = gson1.fromJson(wrwllist.toString(), new TypeToken<List<HuishouBean>>() {
                                }.getType());
                                Logger.e("wrwllist2", wrwllist2.size()+"fzwk");
//                                JSONArray gcllist = jsonObject4.getJSONArray("gcllist");
                                List<HuishouBean> gcllist2 = gson1.fromJson(gcllist.toString(), new TypeToken<List<HuishouBean>>() {
                                }.getType());
                                Logger.e("gcllist2", gcllist2.size()+"fzwk");
                                initFragmentChild(khsllist2,wrwllist2,gcllist2);

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

    @Override
    public void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    public void initFragmentChild(List<HuishouBean> list1,List<HuishouBean> list2,List<HuishouBean> list3) {
        rec_GirdFragment1 fragment1 = rec_GirdFragment1.createInstance(list1);
        rec_GirdFragment2 fragment2 = rec_GirdFragment2.createInstance(list2);
        rec_GirdFragment3 fragment3 = rec_GirdFragment3.createInstance(list3);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rec_FragmentContainer1, fragment1);
        transaction.replace(R.id.rec_FragmentContainer2, fragment2);
        transaction.replace(R.id.rec_FragmentContainer3, fragment3);
        transaction.commit();
    }

    public void initNotice(List<Notice> noticeList2) {
//        List<String> notices = new ArrayList<>();
//        for (int i = 0; i < noticeList2.size(); i++) {
//            notices.add(noticeList2.get(i).getTITILE());
//        }
        first_noticeview.addNotice(noticeList2);
        first_noticeview.startFlipping();
        first_noticeview.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
            @Override
            public void onNotieClick(int position, Notice notice) {
                //打开公告详情页面。
                Intent i =GonggaoDetailActivity.createIntent(context);
                i.putExtra("url",notice.getLINKURL());
                startActivity(i);
            }
        });
    }

    public void initLunBo(List<Piclb> list) {


        if (list.size() > 0) {
            HashMap<String, String> file_maps = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                file_maps.put(list.get(i).getORDER_ID(), list.get(i).getPATH());
            }
            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        // .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mSlider.addSlider(textSliderView);
            }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new MyDescriptionAnimation());
            mSlider.setDuration(4000);


        } else {
            HashMap<String, Integer> file_maps = new HashMap<String, Integer>();

            //轮播默认
            file_maps.put("Hannibal", R.drawable.lunbo1);
            file_maps.put("Big Bang Theory", R.drawable.lunbo2);
            file_maps.put("House of Cards", R.drawable.lunbo3);
            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        // .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mSlider.addSlider(textSliderView);
            }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new MyDescriptionAnimation());
            mSlider.setDuration(4000);


        }


    }

}