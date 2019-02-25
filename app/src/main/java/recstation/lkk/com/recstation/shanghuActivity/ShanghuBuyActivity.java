package recstation.lkk.com.recstation.shanghuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.RecdetailActivity;
import recstation.lkk.com.recstation.SelectRecTypeActivity;
import recstation.lkk.com.recstation.SucessActivity;
import recstation.lkk.com.recstation.adapter.HuishouBeanAdapter;
import recstation.lkk.com.recstation.adapter.HuishouBeanSelectAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Msg;
import recstation.lkk.com.recstation.model.Point;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.SharedPreferencesUtils;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.HuishouBeanView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseHttpRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.StringUtil;

import static recstation.lkk.com.recstation.util.TestUtil.getNowTime;

public class ShanghuBuyActivity extends BaseHttpRecyclerActivity<HuishouBean, HuishouBeanView, HuishouBeanSelectAdapter> {
    //	private static final String TAG = "DemoHtpRecyclerActivity";
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    List<HuishouBean> mdatalist = new ArrayList<HuishouBean>();
    String RETRIEVETYPE_IDS = "";
    String RETRIEVETYPE_NAMES = "";

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {

        return new Intent(context, ShanghuBuyActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanghu_buy);
        Type type =new TypeToken<List<HuishouBean>>() {}.getType();

        //mdatalist =  (ArrayList<HuishouBean>) getIntent().getSerializableExtra("alllist");
        mdatalist = HKEapiManager.getInstances().preferences.getDataList(DemoApplication.getInstance(), "shougoulist",type);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        srlBaseHttpRecycler.autoRefresh();
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须调用
        super.initView();

    }

    @Override
    public void setList(final List<HuishouBean> list) {
        mdatalist = list;
        setList(new AdapterCallBack<HuishouBeanSelectAdapter>() {

            @Override
            public HuishouBeanSelectAdapter createAdapter() {
                return new HuishouBeanSelectAdapter(context);
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
        getRectypeData(page);
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
    public List<HuishouBean> parseArray(String json) {
        return JSON.parseArray(json, HuishouBean.class);
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initEvent() {//必须调用
        super.initEvent();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        showShortToast("nindianjiale " + position);
//        if (id > 0) {
//            //toActivity(UserActivity.createIntent(context, id));
//        }
//        Intent i = SelectRecTypeActivity.createIntent(ShanghuBuyActivity.this);
//        i.putExtra("selectRec_rectype1", mdatalist.get(position).getRETRIEVETYPE_NAME());
//        i.putExtra("selectRec_miaoshu1", mdatalist.get(position).getRETRIEVETYPE_NAME());
//        i.putExtra("selectRec_price1", "7");
//        i.putExtra("selectRec_RETRIEVETYPE_ID", mdatalist.get(position).getRETRIEVETYPE_ID());
//        startActivity(i);
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void getRectypeData(final int page) {
        onHttpResponse(-page, page >= 1 ? null : JSON.toJSONString(mdatalist), null);
    }


    @Override
    public void onForwardClick(View v) {
//        super.onForwardClick(v);

        for (int i = 0; i < mdatalist.size(); i++) {
            if ("000".equals(mdatalist.get(i).getSTATUS())) {
                if ("".equals(RETRIEVETYPE_IDS)) {
                    RETRIEVETYPE_IDS = mdatalist.get(i).getRETRIEVETYPE_ID();
                } else {
                    RETRIEVETYPE_IDS = RETRIEVETYPE_IDS + "," + mdatalist.get(i).getRETRIEVETYPE_ID();
                }
                if ("".equals(RETRIEVETYPE_NAMES)) {
                    RETRIEVETYPE_NAMES = mdatalist.get(i).getRETRIEVETYPE_NAME();
                } else {
                    RETRIEVETYPE_NAMES = RETRIEVETYPE_NAMES + "," + mdatalist.get(i).getRETRIEVETYPE_NAME();
                }

            }
        }
        editName();

    }


    public void editName() {

        String userName2 = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "");

        if (StringUtil.isEmpty(RETRIEVETYPE_IDS)) {
            showShortToast("请至少选择一个类型");
            return;
        }
        Logger.e(RETRIEVETYPE_IDS);
        Logger.e(RETRIEVETYPE_NAMES);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("USERNAME", userName2);
        hashMap.put("RETRIEVETYPE_IDS", RETRIEVETYPE_IDS);
        hashMap.put("RETRIEVETYPE_NAMES", RETRIEVETYPE_NAMES);

        Subscription subscription = HKEapiManager.getInstances().demoApi.sendPost(URLConfig.SHOUGOU_FABU_URL, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            String code = jsonObject.getString("code");
                            if (code.equals("OK")) {
                                String date = getNowTime();
                                Msg shougou = new Msg();
                                shougou.setPreMsg("商户收购订单完成");
                                shougou.setTitle("收购订单发布");
                                shougou.setProMsg("恭喜您收购订单发布完成");
                                shougou.setTime(date);
                                shougou.setMsgType("订单消息");
                                Type type =new TypeToken<List<Msg>>() {}.getType();

                                List<Msg> list =  HKEapiManager.getInstances().preferences.getDataList(DemoApplication.getInstance(), "dingdanmsglist",type);
                                list.add(shougou);
                                HKEapiManager.getInstances().preferences.setDataList(DemoApplication.getInstance(), "dingdanmsglist",list);


                                showShortToast("收购订单发布成功", true);
                                finish();
                                Intent i = SucessActivity.createIntent(ShanghuBuyActivity.this);
                                i.putExtra("title","发布收购成功");
                                startActivity(i);
                            }

                        } catch (JSONException e) {
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

}
