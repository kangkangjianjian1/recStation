package recstation.lkk.com.recstation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import recstation.lkk.com.recstation.adapter.RecPersonAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.Dingdanbean;
import recstation.lkk.com.recstation.model.RecPerson;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.RecpersonView;
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

public class ZhoubianActivity extends BaseHttpRecyclerActivity<RecPerson, RecpersonView, RecPersonAdapter> implements AlertDialog.OnDialogButtonClickListener {
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    List<RecPerson> mdatalist = new ArrayList<RecPerson>();
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    String phoneNum="";
    String price="";
    String type="";
    String picturepath="";
    String RETRIEVETYPE_ID="";

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ZhoubianActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhoubian);
        price = getIntent().getStringExtra("price");
        type = getIntent().getStringExtra("type");
        picturepath = getIntent().getStringExtra("picturepath");
        RETRIEVETYPE_ID = getIntent().getStringExtra("RETRIEVETYPE_ID");

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        srlBaseHttpRecycler.autoRefresh();
    }

    @Override
    public void initView() {//必须调用
        super.initView();

    }

    @Override
    public void setList(final List<RecPerson> list) {
        mdatalist = list;
        setList(new AdapterCallBack<RecPersonAdapter>() {

            @Override
            public RecPersonAdapter createAdapter() {
                return new RecPersonAdapter(context);
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
        getDingdanListData(page);
        //仅测试用<<<<<<<<<<<
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                onHttpResponse(-page, page >= 5 ? null : JSON.toJSONString(TestUtil.getUserList(page, 10)), null);
//            }
//        }, 1000);
        //仅测试用>>>>>>>>>>>>
    }

    @Override
    public List<RecPerson> parseArray(String json) {
        return JSON.parseArray(json, RecPerson.class);
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initEvent() {//必须调用
        super.initEvent();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        phoneNum = mdatalist.get(position).getMOBILE();

        new AlertDialog(ZhoubianActivity.this, "联系收购人员", "是否拨打电话？", true, 0, this).show();

    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (!isPositive) {
            return;
        }
        switch (requestCode) {
            case 0:
                TestUtil.call(this, phoneNum );
                break;
            default:
                break;
        }

    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }

    @Override
    public void onForwardClick(View v) {
        super.onForwardClick(v);
        toActivity(MapActivity.createIntent(ZhoubianActivity.this));
    }



    public void getDingdanListData(final int page) {


//        onHttpResponse(-page, page >= 1 ? null : JSON.toJSONString(mdatalist), null);

//        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");

//        if (StringUtil.isEmpty(RETRIEVETYPE_IDS)){
//            showShortToast("请至少选择一个类型");
//            return;
//        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("RETRIEVETYPE_ID", RETRIEVETYPE_ID);
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
                                List<RecPerson> orderlist = gson1.fromJson(jsonArray.toString(), new TypeToken<List<RecPerson>>() {
                                }.getType());

                                Logger.e("nnnnn", orderlist.size() + "fzwk0p0p");

                                for (int i = 0; i<orderlist.size();i++){
                                    orderlist.get(i).setPICTUREPATH(picturepath);
                                    orderlist.get(i).setPRICE(price);
                                    orderlist.get(i).setTYPE(type);
                                }

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



}
