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
import java.util.IllegalFormatCodePointException;
import java.util.List;

import recstation.lkk.com.recstation.adapter.RecPersonAdapter;
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


    public void getRectypeData(final int page) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }
        //  showProgressDialog("正在核对验证码");
        Logger.e("nnnnn", "lllfzwk222");
        Subscription subscription = HKEapiManager.getInstances().demoApi.recperson(URLConfig.RECPERSON_URL,page+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
//                        s = "[{\"NAME\":\"小张\",\"ORDER_ID\":\"1\",\"PRICE\":\"3\",\"PRICE\":\"5\",\"PHONE\":\"13831292593\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/82b68c5920a24f8790a3922c0b496502.png\",\"CONTENT\":\"为人老实，主要收购玻璃\"}]";

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("merchantlist");
                            JSONObject pageObject = jsonObject.getJSONObject("page");
                            int totalPage = Integer.parseInt(pageObject.getString("totalPage"));
                            if ("OK".equals(code)){
                                Gson gson1 = new Gson();
                                List<RecPerson> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<RecPerson>>() {
                                }.getType());
                                for (int i=0;i<khsllist2.size();i++){
                                    khsllist2.get(i).setPRICE(price);
                                    khsllist2.get(i).setPICTUREPATH(picturepath);
                                    khsllist2.get(i).setTYPE(type);
                                }
                                Logger.e("nnnnn", khsllist2.size() + "fzwk222");

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


    @Override
    protected void onDestroy() {
        mCompositeSubscription.clear();
        super.onDestroy();

    }


}
