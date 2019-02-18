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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.DingdanResultActivity;
import recstation.lkk.com.recstation.LoginActivity;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.adapter.GridHuishouAdapter;
import recstation.lkk.com.recstation.adapter.GridProductAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.ProductBean;
import recstation.lkk.com.recstation.model.RecPerson;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseListFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;


/**
 * 使用方法：复制>粘贴>改名>改代码
 */

/**
 * 列表Fragment示例
 *
 * @author Lemon
 * @use new DemoListFragment(),具体参考.DemoTabActivity(getFragment方法内)
 */
public class rec_GirdFragment extends BaseListFragment<HuishouBean, GridView, GridHuishouAdapter> {
    public static int a = 1;
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    List<HuishouBean> mDatalist =new ArrayList<HuishouBean>();
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static rec_GirdFragment createInstance() {
        return new rec_GirdFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.pro_gird_fragment);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        onRefresh();

        return view;//返回值必须为view
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须在onCreateView方法内调用
        super.initView();
//        tv = findView(R.id.gird_title);
    }

    @Override
    public void setList(final List<HuishouBean> list) {
        mDatalist =list;
//        int a;
//        if (list.size() % 4 == 0) {
//           a = list.size() / 4 ;
//        } else {
//            a = list.size() / 4 + 1;
//        }
//
//        DisplayMetrics dm = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        ViewGroup.LayoutParams lp;
//        lp = lvBaseList.getLayoutParams();
//        lp.width = dm.widthPixels;
//        lp.height = dm.widthPixels / 4 * a;
//        lvBaseList.setLayoutParams(lp);
        //示例代码<<<<<<<<<<<<<<<
        setList(new AdapterCallBack<GridHuishouAdapter>() {

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }

            @Override
            public GridHuishouAdapter createAdapter() {
                return new GridHuishouAdapter(context);
            }
        });
        //示例代码>>>>>>>>>>>>>>>
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须在onCreateView方法内调用
        super.initData();
    }


    @Override
    public void getListAsync(int page) {
        //示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        showProgressDialog(R.string.loading);
        getRectypeData(page);
    }


//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        super.initEvent();
        lvBaseList.setOnItemClickListener(this);

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (adapter!=null){
//            lvBaseList.setAdapter(adapter);
//        }
//
//    }

    //示例代码<<<<<<<<<<<<<<<<<<<
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		//实现单选
//
//		showShortToast("aaa"+position);
//		//toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
//	}
    //示例代码>>>>>>>>>>>>>>>>>>>
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //实现单选
        if (TestUtil.IsLogin()){
            HuishouBean hu =  mDatalist.get(position);
            Intent i = DingdanResultActivity.createIntent(context);
            i.putExtra("data",hu);
            i.putExtra("state","1");
            startActivity(i);
        }else {
            toActivity(LoginActivity.createIntent(context));
        }


//		adapter.selectedPosition = adapter.selectedPosition == position ? -1 : position;
//		adapter.notifyListDataSetChanged();
//        Log.e("kkkkkkwww", "ddddddddwwwkkk");
//        showShortToast("aaa" + position);

        //toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
    }

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void getRectypeData(final int page) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }
        //  showProgressDialog("正在核对验证码");
     //   String username = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");

        Subscription subscription = HKEapiManager.getInstances().demoApi.getdingdanlist(URLConfig.DINGDAN_URL,page+"",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Logger.e("getRectypeData:",s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                            JSONObject pageObject = jsonObject.getJSONObject("page");
                            int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                            if ("OK".equals(code)){
                                Gson gson1 = new Gson();
                                List<HuishouBean> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<HuishouBean>>() {
                                }.getType());
//                                for (int i=0;i<khsllist2.size();i++){
//                                    khsllist2.get(i).setPRICE(price);
//                                    khsllist2.get(i).setPICTUREPATH(picturepath);
//                                    khsllist2.get(i).setTYPE(type);
//                                }
                                Logger.e("nnnnn", khsllist2.size() + "fzwk222");

                                onLoadSucceed(page,page >= totalPage ? null : khsllist2);
                            }else {
                                showShortToast(message);
                            }



                        } catch (JSONException e) {
                            showShortToast("数据解析异常");
                            e.printStackTrace();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                        showShortToast("验证码核对异常，请稍后再试", true);

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        showShortToast("访问正常", true);
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
}