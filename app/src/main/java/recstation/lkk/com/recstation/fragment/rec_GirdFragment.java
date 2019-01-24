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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.adapter.GridHuishouAdapter;
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
public class rec_GirdFragment extends BaseListFragment<HuishouBean, GridView, GridHuishouAdapter> {
    public static int a = 1;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 创建一个Fragment实例
     *
     * @return
     */
    public static rec_GirdFragment createInstance() {
        return new rec_GirdFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.pro_gird_fragment);

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
    public void setList(final List<HuishouBean> list) {
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
        setList(new AdapterCallBack<GridHuishouAdapter>() {

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }

            @Override
            public GridHuishouAdapter createAdapter() {
                return new GridHuishouAdapter(context);
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
        getRectypeData(page);
//        List<ProductBean> list = new ArrayList<ProductBean>();
////        for (int i = 0; i <13; i++) {
////            list.add(new Entry<String, String>(getPictureUrl(i + 6 * page), "联系人" + i + 6 * page));
////        }
////        for (int i = 0; i < datalist.size(); i++) {
////            list.add(new Entry<String, String>(datalist.get(i).getPICTUREPATH(), datalist.get(i).getRETRIEVETYPE_NAME()));
////        }
//        onLoadSucceed(page, list);
        //示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }


//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须在onCreateView方法内调用
        super.initEvent();
        lvBaseList.setOnItemClickListener(this);

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (adapter!=null){
//            lvBaseList.setAdapter(adapter);
//        }
//
//    }

    //示例代码<<<<<<<<<<<<<<<<<<<
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		//实现单选
//
//		showShortToast("aaa"+position);
//		//toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
//	}
    //示例代码>>>>>>>>>>>>>>>>>>>
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //实现单选
//		adapter.selectedPosition = adapter.selectedPosition == position ? -1 : position;
//		adapter.notifyListDataSetChanged();
        Log.e("kkkkkkwww", "ddddddddwwwkkk");
        showShortToast("aaa" + position);
        //toActivity(UserActivity.createIntent(context, position));//一般用id，这里position仅用于测试 id));//
    }

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void getRectypeData(final int page) {
//        final String username = findpwd_account_phone.getText().toString();
//        final String appsms_id = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(),"APPSMS_ID","");
//        final String appsms_code = findpwd_account_yzm.getText().toString();
//        if (prepareForFindpwd(appsms_id)){
//            return;
//        }
        //  showProgressDialog("正在核对验证码");
        Subscription subscription = HKEapiManager.getInstances().demoApi.index(URLConfig.CHECKSMS_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        s = "[{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"1\",\"PICTURENAME\":\"82b68c5920a24f8790a3922c0b496502.png\",\"PRICE\":\"5\",\"RETRIEVETYPE_ID\":\"a523b478bd704c3e892e9cba8224cd5d\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/82b68c5920a24f8790a3922c0b496502.png\",\"CREATETIME\":\"2019-01-16 14:32:43\",\"RETRIEVETYPE_NAME\":\"玻璃\",\"BZ\":\"玻璃\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"1\",\"PICTURENAME\":\"fd7bee50eaef4ae2bf44fdb02b7cb14a.png\",\"PRICE\":\"1\",\"RETRIEVETYPE_ID\":\"f2339cdb739f4276a2e4c36f8900b2d9\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/fd7bee50eaef4ae2bf44fdb02b7cb14a.png\",\"CREATETIME\":\"2019-01-16 14:50:16\",\"RETRIEVETYPE_NAME\":\"纺织品\",\"BZ\":\"纺织品\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"2\",\"PICTURENAME\":\"122600ee9362428fa127bb6e33ab0e15.png\",\"PRICE\":\"1\",\"RETRIEVETYPE_ID\":\"0734ea2b6956433aab3f4b471ceff047\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/122600ee9362428fa127bb6e33ab0e15.png\",\"CREATETIME\":\"2019-01-16 14:50:38\",\"RETRIEVETYPE_NAME\":\"衣柜\",\"BZ\":\"衣柜\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"2\",\"PICTURENAME\":\"f3709b31d60949a8aff4f6194f0dae9c.png\",\"PRICE\":\"5\",\"RETRIEVETYPE_ID\":\"45874847f9c84b5e8274285a1132c4f9\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/f3709b31d60949a8aff4f6194f0dae9c.png\",\"CREATETIME\":\"2019-01-16 14:33:13\",\"RETRIEVETYPE_NAME\":\"金属\",\"BZ\":\"金属\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"2\",\"PICTURENAME\":\"181af4e38764432bbaea4f86b36ccd21.png\",\"PRICE\":\"6\",\"RETRIEVETYPE_ID\":\"524a1ba313084008b53c430c91ec97e0\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/181af4e38764432bbaea4f86b36ccd21.png\",\"CREATETIME\":\"2019-01-16 14:48:56\",\"RETRIEVETYPE_NAME\":\"废电池\",\"BZ\":\"废电池\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"3\",\"PICTURENAME\":\"d9aac6bc539f49098714881a6a305583.png\",\"PRICE\":\"2\",\"RETRIEVETYPE_ID\":\"1569774c4d65463386df6aa33e100d60\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/d9aac6bc539f49098714881a6a305583.png\",\"CREATETIME\":\"2019-01-16 14:33:38\",\"RETRIEVETYPE_NAME\":\"牛奶盒\",\"BZ\":\"牛奶盒\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"3\",\"PICTURENAME\":\"f669907072cc46c6ad70e6c564e41865.png\",\"PRICE\":\"7\",\"RETRIEVETYPE_ID\":\"91d1697050434150ac0a3874b6244313\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/f669907072cc46c6ad70e6c564e41865.png\",\"CREATETIME\":\"2019-01-16 14:49:23\",\"RETRIEVETYPE_NAME\":\"废旧灯管\",\"BZ\":\"废旧灯管\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"3\",\"PICTURENAME\":\"f3d384927b4f417cb2412d30c386c667.png\",\"PRICE\":\"7\",\"RETRIEVETYPE_ID\":\"92594f6a7a2440c59deb58bc9b2b54e2\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/f3d384927b4f417cb2412d30c386c667.png\",\"CREATETIME\":\"2019-01-16 14:50:56\",\"RETRIEVETYPE_NAME\":\"书桌\",\"BZ\":\"书桌\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"4\",\"PICTURENAME\":\"4e1009fbf8fc4ae6af5eec501ce98314.png\",\"PRICE\":\"3\",\"RETRIEVETYPE_ID\":\"27a4cb23863443ac83999872fdf554d0\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/201\n" +
                                "    90116/4e1009fbf8fc4ae6af5eec501ce98314.png\",\"CREATETIME\":\"2019-01-16 14:51:23\",\"RETRIEVETYPE_NAME\":\"破旧陶瓷品\",\"BZ\":\"破旧陶瓷品\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"4\",\"PICTURENAME\":\"68638d4f72ab4dcfb09df3b4934f92d7.png\",\"PRICE\":\"4\",\"RETRIEVETYPE_ID\":\"b646744f208848a6a02e9d6d04f9c148\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/68638d4f72ab4dcfb09df3b4934f92d7.png\",\"CREATETIME\":\"2019-01-16 14:34:06\",\"RETRIEVETYPE_NAME\":\"塑料\",\"BZ\":\"塑料\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"4\",\"PICTURENAME\":\"f457527b8ec94d4f993f50de7c363746.png\",\"PRICE\":\"2\",\"RETRIEVETYPE_ID\":\"ebabb5d1a7354269af1e614a11056c85\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/f457527b8ec94d4f993f50de7c363746.png\",\"CREATETIME\":\"2019-01-16 14:49:52\",\"RETRIEVETYPE_NAME\":\"废油漆桶\",\"BZ\":\"废油漆桶\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"5\",\"PICTURENAME\":\"9ef977e43ef342f097d85795fb15a822.png\",\"PRICE\":\"6\",\"RETRIEVETYPE_ID\":\"9d1572529eba423baf665e803c3cc357\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/9ef977e43ef342f097d85795fb15a822.png\",\"CREATETIME\":\"2019-01-16 14:34:56\",\"RETRIEVETYPE_NAME\":\"易拉罐\",\"BZ\":\"易拉罐\"},{\"RETRIEVETYPE\":\"1\",\"ORDER_ID\":\"6\",\"PICTURENAME\":\"5504e67927364230b1b5c47d6e554a95.png\",\"PRICE\":\"4\",\"RETRIEVETYPE_ID\":\"32b8323d076d458ab4aa37574ae71cc0\",\"PICTUREPATH\":\"http://47.92.55.233:8080/hdrra/uploadFiles/uploadImgs/20190116/5504e67927364230b1b5c47d6e554a95.png\",\"CREATETIME\":\"2019-01-16 14:45:55\",\"RETRIEVETYPE_NAME\":\"纸张\",\"BZ\":\"纸张\"}]";

                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            Gson gson1 = new Gson();
                            List<HuishouBean> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<HuishouBean>>() {
                            }.getType());
                            Logger.e("nnnnn", khsllist2.size() + "fzwk");
                            onLoadSucceed(page, khsllist2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("lkk", "throwable22222222222" + throwable.getLocalizedMessage());
                        showShortToast("验证码核对异常，请稍后再试", true);

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        showShortToast("访问正常", true);
//                        Logger.e("lkk", "onCompleted");
                    }
                });

    }
}