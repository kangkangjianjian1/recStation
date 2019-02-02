package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.adapter.HuishouBeanAdapter;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
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


/**
 * 使用方法：复制>粘贴>改名>改代码
 */

/**用户列表界面Activity示例
 * @author Lemon
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use toActivity(DemoHtpRecyclerActivity.createIntent ( ...));
 */
public class RecdetailActivity extends BaseHttpRecyclerActivity<HuishouBean, HuishouBeanView, HuishouBeanAdapter> implements OnBottomDragListener {
    //	private static final String TAG = "DemoHtpRecyclerActivity";
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    List<HuishouBean> mdatalist = new ArrayList<HuishouBean>();
    CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {

        return new Intent(context, RecdetailActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO demo_http_recycler_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
        setContentView(R.layout.activity_recdetail, this);
        mdatalist =  (ArrayList<HuishouBean>) getIntent().getSerializableExtra("alllist");

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
        setList(new AdapterCallBack<HuishouBeanAdapter>() {

            @Override
            public HuishouBeanAdapter createAdapter() {
                return new HuishouBeanAdapter(context);
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
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (id > 0) {
//            //toActivity(UserActivity.createIntent(context, id));
//        }
        Intent i = SelectRecTypeActivity.createIntent(RecdetailActivity.this);
        i.putExtra("selectRec_rectype1", mdatalist.get(position).getRETRIEVETYPE_NAME());
        i.putExtra("selectRec_miaoshu1", mdatalist.get(position).getRETRIEVETYPE_NAME());
        i.putExtra("selectRec_price1", "7");
        i.putExtra("selectRec_RETRIEVETYPE_ID", mdatalist.get(position).getRETRIEVETYPE_ID());
        startActivity(i);
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void getRectypeData(final int page) {
                            onHttpResponse(-page, page >= 1 ? null : JSON.toJSONString(mdatalist), null);


    }

}