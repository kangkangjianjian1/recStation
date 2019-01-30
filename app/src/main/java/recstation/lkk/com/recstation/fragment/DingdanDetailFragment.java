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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.adapter.DingdanAdapter;
import recstation.lkk.com.recstation.adapter.DingdanMsgAdapter;
import recstation.lkk.com.recstation.model.Dingdanbean;
import recstation.lkk.com.recstation.model.Msg;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.DingdanMsgView;
import recstation.lkk.com.recstation.view.DingdanView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseHttpRecyclerFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;

/**
 * 用户列表界面fragment
 *
 * @author Lemon
 * @use new UserListFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 * @must 查看 .HttpManager 中的@must和@warn
 * 查看 .SettingUtil 中的@must和@warn
 */
public class DingdanDetailFragment extends BaseHttpRecyclerFragment<Dingdanbean, DingdanView, DingdanAdapter> {
    //	private static final String TAG = "UserListFragment";
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    String status;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static DingdanDetailFragment createInstance(String status) {
        DingdanDetailFragment fragment = new DingdanDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        status = getArguments().getString("status");
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        srlBaseHttpRecycler.autoRefresh();

        return view;
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须调用
        super.initView();

    }

    @Override
    public void setList(final List<Dingdanbean> list) {
        setList(new AdapterCallBack<DingdanAdapter>() {

            @Override
            public DingdanAdapter createAdapter() {
                return new DingdanAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须调用
        super.initData();

    }

    @Override
    public void getListAsync(final int page) {
        //实际使用时用这个，需要配置服务器地址		HttpRequest.getUserList(range, page, -page, this);
        getDingdanData(page,status);
    }

    @Override
    public List<Dingdanbean> parseArray(String json) {
        return JSON.parseArray(json, Dingdanbean.class);
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void getDingdanData(final int page, final String status) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }
        //  showProgressDialog("正在核对验证码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.index(URLConfig.CHECKSMS_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

//                        s = "[{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"},{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}，{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}，{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}]";
                        Dingdanbean dingdanbean = new Dingdanbean();
                        dingdanbean.setRETRIEVETYPE_NAME("订单类型");
                        dingdanbean.setPROVINCE("河北省");
                        dingdanbean.setCITY("邯郸市");
                        dingdanbean.setAREA("邯山区");
                        dingdanbean.setADDRESS("和平小区8栋403");
                        dingdanbean.setYDATE("2019-01-30");
                        dingdanbean.setYTIME("23:00:09");
//						try {
//							JSONArray jsonArray = new JSONArray(s);
//							Gson gson1 = new Gson();
//							List<Msg> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Msg>>() {
//							}.getType());
                        List<Dingdanbean> khsllist2 = new ArrayList<Dingdanbean>();
                        if (status.equals("1")){
                            khsllist2.add(dingdanbean);
                        }else if (status.equals("2")){
                            khsllist2.add(dingdanbean);
                            khsllist2.add(dingdanbean);
                        }else if (status.equals("3")){
                            khsllist2.add(dingdanbean);
                            khsllist2.add(dingdanbean);
                            khsllist2.add(dingdanbean);
                        }else {
                            khsllist2.add(dingdanbean);
                        khsllist2.add(dingdanbean);
                        khsllist2.add(dingdanbean);
                        khsllist2.add(dingdanbean);}

                        onHttpResponse(-page, page >= 2 ? null : JSON.toJSONString(khsllist2), null);
                        Logger.e("nnnnn", khsllist2.size() + "fzwk");

//							onHttpResponse(-page, page >= 2 ? null : s, null);

//						} catch (JSONException e) {
//							e.printStackTrace();
//						}


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                        showShortToast("网络异常，请稍后再试", true);

                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });
        mCompositeSubscription.add(subscription);

    }


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initEvent() {//必须调用
        super.initEvent();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id > 0) {
            //	toActivity(UserActivity.createIntent(context, id));
        }
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();
    }
}