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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.DingdanResultActivity;
import recstation.lkk.com.recstation.LoginActivity;
import recstation.lkk.com.recstation.adapter.DingdanAdapter;
import recstation.lkk.com.recstation.adapter.DingdanMsgAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.Dingdanbean;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Msg;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
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
    List<Dingdanbean> mDatalist =new ArrayList<Dingdanbean>();

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
        mDatalist = list;
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
        String USERNAME = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");
        String STATUS = null;
       if (status.equals("10")){

           Subscription subscription = HKEapiManager.getInstances().demoApi.querydingdan(URLConfig.DINGDAN_URL,page+"",null,null,USERNAME,null,null,null)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Action1<String>() {
                       @Override
                       public void call(String s) {

                           Logger.e("getDingdanDatagetDingdanData:"+status,s);
                           try {
                               JSONObject jsonObject = new JSONObject(s);
                               String code = jsonObject.getString("code");
                               String message = jsonObject.getString("message");
                               JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                               JSONObject pageObject = jsonObject.getJSONObject("page");
                               int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                               if ("OK".equals(code)){
                                   Gson gson1 = new Gson();
                                   List<Dingdanbean> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Dingdanbean>>() {
                                   }.getType());
                                   List<Dingdanbean> khsllist3 = new ArrayList<Dingdanbean>();
                                   for (int i = 0; i<khsllist2.size();i++){
                                       if (!"9".equals(khsllist2.get(i).getSTATUS())){
                                           khsllist3.add(khsllist2.get(i));
                                       }
                                   }
                                   onHttpResponse(-page, page >= totalPage ? null : JSON.toJSONString(khsllist3), null);

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
                           showShortToast("网络异常，请稍后再试", true);

                       }
                   }, new Action0() {
                       @Override
                       public void call() {
//                        Logger.e("lkk", "onCompleted");
                       }
                   });
           mCompositeSubscription.add(subscription);

       }else if (status.equals("11")){
            STATUS = null;

           Subscription subscription = HKEapiManager.getInstances().demoApi.querydingdan(URLConfig.DINGDAN_URL,page+"",null,null,USERNAME,null,null,null)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Action1<String>() {
                       @Override
                       public void call(String s) {

                           Logger.e("getDingdanDatagetDingdanData:"+status,s);
                           try {
                               JSONObject jsonObject = new JSONObject(s);
                               String code = jsonObject.getString("code");
                               String message = jsonObject.getString("message");
                               JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                               JSONObject pageObject = jsonObject.getJSONObject("page");
                               int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                               if ("OK".equals(code)){
                                   Gson gson1 = new Gson();
                                   List<Dingdanbean> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Dingdanbean>>() {
                                   }.getType());
                                   List<Dingdanbean> khsllist3 = new ArrayList<Dingdanbean>();
                                   for (int i = 0; i<khsllist2.size();i++){
                                       if ("9".equals(khsllist2.get(i).getSTATUS())){
                                           khsllist3.add(khsllist2.get(i));
                                       }
                                   }
                                   onHttpResponse(-page, page >= totalPage ? null : JSON.toJSONString(khsllist3), null);

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
                           showShortToast("网络异常，请稍后再试", true);

                       }
                   }, new Action0() {
                       @Override
                       public void call() {
//                        Logger.e("lkk", "onCompleted");
                       }
                   });
           mCompositeSubscription.add(subscription);

        } else {
           STATUS = status;

                Subscription subscription = HKEapiManager.getInstances().demoApi.querydingdan(URLConfig.DINGDAN_URL,page+"",USERNAME,STATUS,null,null,null,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        Logger.e("getDingdanDatagetDingdanData:"+status,s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                            JSONObject pageObject = jsonObject.getJSONObject("page");
                            int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                            if ("OK".equals(code)){
                                Gson gson1 = new Gson();
                                List<Dingdanbean> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Dingdanbean>>() {
                                }.getType());
                                onHttpResponse(-page, page >= totalPage ? null : JSON.toJSONString(khsllist2), null);

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
    }


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initEvent() {//必须调用
        super.initEvent();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (TestUtil.IsLogin()){
            Dingdanbean hu1 =  mDatalist.get(position);
            HuishouBean hu = new HuishouBean();
            hu.setADDRESS(hu1.getADDRESS());
            hu.setAREA(hu1.getAREA());
            hu.setRETRIEVETYPE_ID(hu1.getRETRIEVETYPE_ID());
            hu.setSUSERNAME(hu1.getSUSERNAME());
            hu.setRETRIEVEORDER_ID(hu1.getRETRIEVEORDER_ID());
            hu.setYDATE(hu1.getYDATE());
            hu.setCREATETIME(hu1.getCREATETIME());
            hu.setYTIME(hu1.getYTIME());
            hu.setMOBILE(hu1.getMOBILE());
            hu.setAPPRAISE(hu1.getAPPRAISE());
            hu.setPROVINCE(hu1.getPROVINCE());
            hu.setCITY(hu1.getCITY());
            hu.setSTATUS(hu1.getSTATUS());
            hu.setPICTUREPATH(hu1.getPICTUREPATH());
            hu.setRETRIEVETYPE_NAME(hu1.getRETRIEVETYPE_NAME());
            Intent i = DingdanResultActivity.createIntent(context);
            i.putExtra("data",hu);
            if (status.equals("11")||status.equals("10")){
                i.putExtra("state","3");
            }else{
                i.putExtra("state","2");
            }

            startActivity(i);
        }else {
            toActivity(LoginActivity.createIntent(context));
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