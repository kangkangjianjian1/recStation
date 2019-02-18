package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.DEMO.DemoListActivity;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.util.TestUtil;
import zuo.biao.library.base.BaseListActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.model.Entry;
import zuo.biao.library.ui.GridAdapter;

import static recstation.lkk.com.recstation.util.TestUtil.IsLogin;

public class MyZhoubianActivity extends BaseListActivity<Entry<String, String>, GridView, GridAdapter> {
    public List<HuishouBean> datalist;
    public static int a = 1;
    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context,List<HuishouBean> list) {
        Intent i = new Intent(context,MyZhoubianActivity.class);
        i.putExtra("alllist", (Serializable) list);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datalist =  (ArrayList<HuishouBean>) getIntent().getSerializableExtra("alllist");

        setContentView(R.layout.activity_my_zhoubian);
        initView();
        initData();
        initEvent();

        onRefresh();

    }


    @Override
    public void initView() {//必须在onCreateView方法内调用
        super.initView();

    }

    @Override
    public void setList(final List<Entry<String, String>> list) {
        //示例代码<<<<<<<<<<<<<<<
        setList(new AdapterCallBack<GridAdapter>() {

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }

            @Override
            public GridAdapter createAdapter() {
                return new GridAdapter(context);
            }
        });
    }


    @Override
    public void initData() {//必须在onCreateView方法内调用
        super.initData();
    }


    @Override
    public void getListAsync(int page) {
        //示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        showProgressDialog(R.string.loading);
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
        for (int i = 0; i < datalist.size(); i++) {
            list.add(new Entry<String, String>(datalist.get(i).getPICTUREPATH(), datalist.get(i).getRETRIEVETYPE_NAME()));
        }
        onLoadSucceed(page, list);
    }

    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        super.initEvent();
        lvBaseList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(IsLogin()){
            Intent i = ZhoubianActivity.createIntent(context);
            i.putExtra("price","45");
            i.putExtra("type",datalist.get(position).getRETRIEVETYPE_NAME());
            i.putExtra("picturepath",datalist.get(position).getPICTUREPATH());
            toActivity(i);

        }else {
            toActivity(LoginActivity.createIntent(context));
        }
    }



}