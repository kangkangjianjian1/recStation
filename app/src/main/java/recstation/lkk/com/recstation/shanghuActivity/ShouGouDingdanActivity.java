package recstation.lkk.com.recstation.shanghuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.adapter.DingdanAdapter;
import recstation.lkk.com.recstation.adapter.HuishouBeanSelectAdapter;
import recstation.lkk.com.recstation.adapter.ShouGouDingdanAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.Dingdanbean;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Msg;
import recstation.lkk.com.recstation.model.RecPerson;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.DingdanView;
import recstation.lkk.com.recstation.view.ShougouDingdanView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseHttpRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.StringUtil;

public class ShouGouDingdanActivity extends BaseHttpRecyclerActivity<Dingdanbean, ShougouDingdanView, ShouGouDingdanAdapter> implements AlertDialog.OnDialogButtonClickListener{
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    List<Dingdanbean> mdatalist = new ArrayList<Dingdanbean>();
    int clickposition=0;

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {

        return new Intent(context, ShouGouDingdanActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_gou_dingdan);

        initView();
        initData();
        initEvent();
          srlBaseHttpRecycler.autoRefresh();
    }
    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须调用
        super.initView();

    }

    @Override
    public void initData() {//必须调用
        super.initData();

    }

    @Override
    public void initEvent() {//必须调用
        super.initEvent();

    }
    @Override
    public void setList(final List<Dingdanbean> list) {
        mdatalist = list;
        setList(new AdapterCallBack<ShouGouDingdanAdapter>() {

            @Override
            public ShouGouDingdanAdapter createAdapter() {
                return new ShouGouDingdanAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }

    @Override
    public void getListAsync(int page) {
        getDingdanListData(page);
    }

    @Override
    public List<Dingdanbean> parseArray(String json) {
        return JSON.parseArray(json, Dingdanbean.class);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        clickposition = position;
        new AlertDialog(this, "撤销收购订单", "确定撤销订单？", true, 0,this).show();

    }

    public void getDingdanListData(final int page) {


//        onHttpResponse(-page, page >= 1 ? null : JSON.toJSONString(mdatalist), null);

        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");

//        if (StringUtil.isEmpty(RETRIEVETYPE_IDS)){
//            showShortToast("请至少选择一个类型");
//            return;
//        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("USERNAME", userName2);
        hashMap.put("currentPage", page+"");

        Subscription subscription = HKEapiManager.getInstances().demoApi.sendPost(URLConfig.SHOUGOU_QUERY_URL, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                            JSONObject pageObject = jsonObject.getJSONObject("page");
                            int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                            if ("OK".equals(code)){
                                Gson gson1 = new Gson();
                                List<Dingdanbean> orderlist = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Dingdanbean>>() {
                                }.getType());

                                Logger.e("nnnnn", orderlist.size() + "fzwk0p0p");

                                onHttpResponse(-page, page >= totalPage ? null : JSON.toJSONString(orderlist), null);
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
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (!isPositive) {
            return;
        }

        switch (requestCode) {
            case 0:
                CheXiaoDingdan(mdatalist.get(clickposition).getMERCHANTORDER_ID());
//                if (!TestUtil.IsBussiness()){
//                    showShortToast("您不是商家,不能接收订单,请先在商户管理中进行商户认证.");
//
//                    return;
//                }else {
////                    String username = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "-1");
//                    CheXiaoDingdan(mdatalist.get(clickposition).getMERCHANTORDER_ID());
//                }

                break;

            default:
                break;
        }
    }

     public void CheXiaoDingdan(String id) {


//        onHttpResponse(-page, page >= 1 ? null : JSON.toJSONString(mdatalist), null);

//        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");

//        if (StringUtil.isEmpty(RETRIEVETYPE_IDS)){
//            showShortToast("请至少选择一个类型");
//            return;
//        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("MERCHANTORDER_ID", id);


        Subscription subscription = HKEapiManager.getInstances().demoApi.sendPost(URLConfig.SHOUGOU_DEL_URL, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
//                            JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
//                            JSONObject pageObject = jsonObject.getJSONObject("page");
//                            int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                            if ("OK".equals(code)){

                                Msg shougou = new Msg();
                                shougou.setPreMsg("商户收购订单撤销");
                                shougou.setTitle("收购订单撤销");
                                shougou.setProMsg("恭喜您收购订单撤销成功");
                                shougou.setTime(TestUtil.getNowTime());
                                shougou.setMsgType("订单消息");
                                Type type =new TypeToken<List<Msg>>() {}.getType();

                                List<Msg> list =  HKEapiManager.getInstances().preferences.getDataList(DemoApplication.getInstance(), "dingdanmsglist",type);
                                list.add(shougou);
                                HKEapiManager.getInstances().preferences.setDataList(DemoApplication.getInstance(), "dingdanmsglist",list);

                                showShortToast("撤销成功");
                                srlBaseHttpRecycler.autoRefresh();
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
                    }
                });
        mCompositeSubscription.add(subscription);

    }


}
