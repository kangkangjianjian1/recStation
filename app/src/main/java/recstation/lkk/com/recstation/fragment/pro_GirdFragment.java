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

import recstation.lkk.com.recstation.ProDetailActivity;
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
        onLoadSucceed(page, page >= 1 ? null : datalist);
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
      String name = datalist.get(position).getITEM_NAME();
        switch (name){
            case "不锈钢包饺子器":
                Intent intent = ProDetailActivity.createIntent(context);
                intent.putExtra("id1",R.drawable.jiaozi1);
                intent.putExtra("id2",R.drawable.jiaozi2);
                intent.putExtra("id3",R.drawable.jiaozi3);
                startActivity(intent);
                break;
            case "碎菜绞肉机":
                Intent intent2 = ProDetailActivity.createIntent(context);
                intent2.putExtra("id1",R.drawable.jiaorou1);
                intent2.putExtra("id2",R.drawable.jiaorou2);
                intent2.putExtra("id3",R.drawable.jiaorou3);
                startActivity(intent2);
                break;
            case "97件双层木盒套装(豪华款)":
                Intent intent3 = ProDetailActivity.createIntent(context);
                intent3.putExtra("id1",R.drawable.muhe1);
                intent3.putExtra("id2",R.drawable.muhe2);
                startActivity(intent3);
                break;
            case "投影闹钟":
                Intent intent4 = ProDetailActivity.createIntent(context);
                intent4.putExtra("id1",R.drawable.naozhong1);
                intent4.putExtra("id2",R.drawable.naozhong2);
                intent4.putExtra("id3",R.drawable.naozhong3);
                startActivity(intent4);
                break;
            case "小鹿存钱罐":
                Intent intent5 = ProDetailActivity.createIntent(context);
                intent5.putExtra("id1",R.drawable.chuqianguan1);
                intent5.putExtra("id2",R.drawable.chuqianguan2);
                intent5.putExtra("id3",R.drawable.chuqianguan3);
                startActivity(intent5);
                break;
            case "新年卡通呆萌小挂件":
                Intent intent6 = ProDetailActivity.createIntent(context);
                intent6.putExtra("id1",R.drawable.guajian1);
                intent6.putExtra("id2",R.drawable.guajia2);
                intent6.putExtra("id3",R.drawable.guajian1);
                startActivity(intent6);
                break;

            case "磁悬浮炫光地球仪":
                Intent intent7 = ProDetailActivity.createIntent(context);
                intent7.putExtra("id1",R.drawable.diqiuyi1);
                intent7.putExtra("id2",R.drawable.diqiuyi2);
                intent7.putExtra("id3",R.drawable.diqiuyi3);
                startActivity(intent7);
                break;
            case "台电32G手电脑两用u盘":
                Intent intent8 = ProDetailActivity.createIntent(context);
                intent8.putExtra("id1",R.drawable.u1);
                intent8.putExtra("id2",R.drawable.u2);
                intent8.putExtra("id3",R.drawable.u3);
                startActivity(intent8);
                break;
            case "小米九号平衡车":
                Intent intent9 = ProDetailActivity.createIntent(context);
                intent9.putExtra("id1",R.drawable.pinghengche1);
                intent9.putExtra("id2",R.drawable.pinghengche2);
                startActivity(intent9);
                break;
                default:
                    Intent intent32 = ProDetailActivity.createIntent(context);
                    intent32.putExtra("id1",R.drawable.muhe1);
                    intent32.putExtra("id2",R.drawable.muhe2);
                    startActivity(intent32);
                    break;
        }
      //  showShortToast("aaa" + position);
    }

}