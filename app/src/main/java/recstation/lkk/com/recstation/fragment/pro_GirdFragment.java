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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.adapter.GridProductAdapter;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.ProductBean;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.URLConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import zuo.biao.library.base.BaseListFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.util.Log;


/**
 * 使用方法：复制>粘贴>改名>改代码
 */

/**
 * 列表Fragment示例
 *
 * @author Lemon
 * @use new DemoListFragment(),具体参考.DemoTabActivity(getFragment方法内)
 */
public class pro_GirdFragment extends BaseListFragment<ProductBean, GridView, GridProductAdapter> {
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public List<ProductBean> datalist;
    /**

     * 创建一个Fragment实例
     *
     * @return
     */
    public static pro_GirdFragment createInstance(List<ProductBean> list) {
        pro_GirdFragment fragment = new pro_GirdFragment();
        Bundle bundle = new Bundle();
        ArrayList list2 = new ArrayList(); //这个list用于在budnle中传递 需要传递的ArrayList<Object>
        list2.add(list);
        bundle.putParcelableArrayList("list2",list2);
        fragment.setArguments(bundle);
        return fragment;
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.pro_gird_fragment);
        ArrayList list2 = getArguments().getParcelableArrayList("list2");
        datalist= (List<ProductBean>) list2.get(0);
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        onRefresh();

        return view;//返回值必须为view
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须在onCreateView方法内调用
        super.initView();
//        tv = findView(R.id.gird_title);
    }

    @Override
    public void setList(final List<ProductBean> list) {
//        int a;
//        if (list.size() % 4 == 0) {
//           a = list.size() / 4 ;
//        } else {
//            a = list.size() / 4 + 1;
//        }
//
//        DisplayMetrics dm = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        ViewGroup.LayoutParams lp;
//        lp = lvBaseList.getLayoutParams();
//        lp.width = dm.widthPixels;
//        lp.height = dm.widthPixels / 4 * a;
//        lvBaseList.setLayoutParams(lp);
        //示例代码<<<<<<<<<<<<<<<
        setList(new AdapterCallBack<GridProductAdapter>() {

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }

            @Override
            public GridProductAdapter createAdapter() {
                return new GridProductAdapter(context);
            }
        });
        //示例代码>>>>>>>>>>>>>>>
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须在onCreateView方法内调用
        super.initData();
    }


    @Override
    public void getListAsync(int page) {
        //示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        showProgressDialog(R.string.loading);
        onLoadSucceed(page, page >= 2 ? null : datalist);
       //onLoadSucceed(page, datalist);
    }


//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        super.initEvent();
        lvBaseList.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showShortToast("aaa" + position);
    }

}