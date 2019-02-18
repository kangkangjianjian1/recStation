package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.fragment.rec_GirdFragment1;
import recstation.lkk.com.recstation.model.HuishouBean;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseListActivity;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.model.Entry;
import zuo.biao.library.ui.GridAdapter;

import static recstation.lkk.com.recstation.util.TestUtil.IsLogin;

public class MyZhoubianActivity2 extends BaseActivity implements View.OnClickListener {

    List<HuishouBean> list1 = new ArrayList<HuishouBean>();
    List<HuishouBean> list2 = new ArrayList<HuishouBean>();
    List<HuishouBean> list3 = new ArrayList<HuishouBean>();

    public static Intent createIntent(Context context,  List<HuishouBean> list1,  List<HuishouBean> list2,  List<HuishouBean> list3) {
        return new Intent(context, MyZhoubianActivity2.class).putExtra("list1", (Serializable) list1).putExtra("list2", (Serializable) list2).putExtra("list3", (Serializable) list3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zhoubian2);
        list1 =  (ArrayList<HuishouBean>) getIntent().getSerializableExtra("list1");
        list2 =  (ArrayList<HuishouBean>) getIntent().getSerializableExtra("list2");
        list3 =  (ArrayList<HuishouBean>) getIntent().getSerializableExtra("list3");

        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        rec_GirdFragment1 fragment1 = rec_GirdFragment1.createInstance(list1);
        rec_GirdFragment1 fragment2 = rec_GirdFragment1.createInstance(list2);
        rec_GirdFragment1 fragment3 = rec_GirdFragment1.createInstance(list3);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rec_FragmentContainer1, fragment1);
        transaction.replace(R.id.rec_FragmentContainer2, fragment2);
        transaction.replace(R.id.rec_FragmentContainer3, fragment3);
        transaction.commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.btn_sucess) {
//
//            ((MainTabActivity) context).selectFragment(1);
//        }
    }

}
