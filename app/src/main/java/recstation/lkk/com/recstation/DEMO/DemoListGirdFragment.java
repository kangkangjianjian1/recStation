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

package recstation.lkk.com.recstation.DEMO;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.util.TestUtil;
import zuo.biao.library.base.BaseListFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.model.Entry;
import zuo.biao.library.ui.GridAdapter;
import zuo.biao.library.util.Log;


/**
 * 使用方法：复制>粘贴>改名>改代码
 */

/**列表Fragment示例
 * @author Lemon
 * @use new DemoListFragment(),具体参考.DemoTabActivity(getFragment方法内)
 */
public class DemoListGirdFragment extends BaseListFragment<Entry<String, String>, GridView, GridAdapter> {
//	private static final String TAG = "DemoListFragment";

//    TextView tv;
    public  static int a =1;
    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**创建一个Fragment实例
     * @return
     */
    public static DemoListGirdFragment createInstance() {
        return new DemoListGirdFragment();
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.demo_list_gird_fragment);

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
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
        for (int i = 0; i < 16; i++) {
            list.add(new Entry<String, String>(getPictureUrl(i + 6 * page), "联系人" + i + 6 * page));
        }
        onLoadSucceed(page, list);
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

    /**获取图片地址，仅供测试用
     * @param userId
     * @return
     */
    private String getPictureUrl(int userId) {
        return TestUtil.getPicture(userId % 6);
    }

}