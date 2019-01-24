package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.adapter.AdressAdapter;
import recstation.lkk.com.recstation.adapter.HuishouBeanAdapter;
import recstation.lkk.com.recstation.model.Adress;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.AdressView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zuo.biao.library.base.BaseHttpRecyclerActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.JSON;

public class AdressListActivity extends BaseHttpRecyclerActivity<Adress,AdressView,AdressAdapter> {

    List<Adress> mdatalist = new ArrayList<Adress>();
    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, AdressListActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        srlBaseHttpRecycler.autoRefresh();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }


    @Override
    public void setList(final List<Adress> list) {
        mdatalist = list;
        setList(new AdapterCallBack<AdressAdapter>() {

            @Override
            public AdressAdapter createAdapter() {
                return new AdressAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }
    @Override
    public void getListAsync(int page) {
        getRectypeData(page);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getRectypeData(0);

    }

    @Override
    public List<Adress> parseArray(String json) {
        return JSON.parseArray(json, Adress.class);
    }

    @Override
    public void onForwardClick(View v) {
        super.onForwardClick(v);
        //打开地址编辑页面
        startActivity(AddAdressActivity.createIntent(AdressListActivity.this));
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //打开编辑地址页面
        Intent intent = new Intent();
        //把返回数据存入Intent
        String adress =mdatalist.get(position).getAdressName()+mdatalist.get(position).getRealAdress();
        Logger.e("nnnnn",adress);
        Logger.e("nnnnn2",mdatalist.get(position).getPhone());
        intent.putExtra("adress", adress);
        intent.putExtra("phone", mdatalist.get(position).getPhone());
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
//        Intent i = AddAdressActivity.createIntent(AdressListActivity.this);
//        i.putExtra("selectRec_rectype1",mdatalist.get(position).getRETRIEVETYPE_NAME());
//        i.putExtra("selectRec_miaoshu1",mdatalist.get(position).getBZ());
//        i.putExtra("selectRec_price1",mdatalist.get(position).getPRICE());
//        startActivity(i);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        //把返回数据存入Intent
        String adress =mdatalist.get(0).getAdressName()+mdatalist.get(0).getRealAdress();
        intent.putExtra("adress", adress);
        intent.putExtra("phone", mdatalist.get(0).getPhone());
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    public void getRectypeData(final int page) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }

        //showProgressDialog("正在核对验证码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.index(URLConfig.CHECKSMS_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        s="[{\"name\":\"李小康\",\"phone\":\"13900034567\",\"adressName\":\"河北省 邯郸市 丛台区1\",\"realAdress\":\"八大胡同132号\",\"isDefault\":\"1\"},"+
                    "{\"name\":\"李小康2\",\"phone\":\"13900034567\",\"adressName\":\"河北省 邯郸市 丛台区2\",\"realAdress\":\"八大胡同132号\",\"isDefault\":\"0\"},"+
                   "{\"name\":\"李小康3\",\"phone\":\"13900034567\",\"adressName\":\"河北省 邯郸市 丛台区3\",\"realAdress\":\"八大胡同132号\",\"isDefault\":\"0\"}]";
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            Gson gson1 = new Gson();
                            List<Adress> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Adress>>() {
                            }.getType());
                            Logger.e("nnnnn",khsllist2.size()+"fzwk");

                            onHttpResponse(-page, page >= 2 ? null : s, null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                        showShortToast("验证码核对异常，请稍后再试",true);

                    }
                }, new Action0() {
                    @Override
                    public void call() {
//                        Logger.e("lkk", "onCompleted");
                    }
                });



    }


}
