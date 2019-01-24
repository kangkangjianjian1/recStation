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
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.flyco.tablayout.widget.MsgView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import recstation.lkk.com.recstation.adapter.SystemMsgAdapter;
import recstation.lkk.com.recstation.adapter.UserAdapter;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.Msg;
import recstation.lkk.com.recstation.model.User;
import recstation.lkk.com.recstation.util.HKEapiManager;
import recstation.lkk.com.recstation.util.HttpRequest;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.TestUtil;
import recstation.lkk.com.recstation.util.URLConfig;
import recstation.lkk.com.recstation.view.SystemMsgView;
import recstation.lkk.com.recstation.view.UserView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zuo.biao.library.base.BaseHttpRecyclerFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.util.JSON;

/**用户列表界面fragment
 * @author Lemon
 * @use new UserListFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 * @must 查看 .HttpManager 中的@must和@warn
 *       查看 .SettingUtil 中的@must和@warn
 */
public class SystemMsgRecyclerFragment extends BaseHttpRecyclerFragment<Msg,SystemMsgView, SystemMsgAdapter>  {
	//	private static final String TAG = "UserListFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public static SystemMsgRecyclerFragment createInstance() {
		SystemMsgRecyclerFragment fragment = new SystemMsgRecyclerFragment();
		return fragment;
	}

	//与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;

	private int range = RANGE_ALL;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);


		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		srlBaseHttpRecycler.autoRefresh();

		return view;
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		super.initView();

	}

	@Override
	public void setList(final List<Msg> list) {
		setList(new AdapterCallBack<SystemMsgAdapter>() {

			@Override
			public SystemMsgAdapter createAdapter() {
				return new SystemMsgAdapter(context);
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

		getSystemMsgData(page);
	}

	@Override
	public List<Msg> parseArray(String json) {
		return JSON.parseArray(json, Msg.class);
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	public void getSystemMsgData(final int page) {
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

						//s="[{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}]";
						s="[{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"},{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}，{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}，{\"msgType\":\"消息类型\",\"title\":\"消息标题\",\"preMsg\":\"消息摘要\",\"time\":\"2018_12_12 08:00:09\",\"proMsg\":\"消息内容详细信息\"}]";

						Msg msg = new Msg();
						msg.setMsgType("系统消息类型");
						msg.setTime("2018_12_12_08:00:09");
						msg.setTitle("系统消息标题");
						msg.setPreMsg("系统消息摘要");
						msg.setProMsg("系统消息内容详细信息");
//						try {
//							JSONArray jsonArray = new JSONArray(s);
//							Gson gson1 = new Gson();
						//	List<Msg> khsllist2 = gson1.fromJson(jsonArray.toString(), new TypeToken<List<Msg>>() {
							List<Msg> khsllist2 = new ArrayList<Msg>();
							khsllist2.add(msg);
							khsllist2.add(msg);
							khsllist2.add(msg);
							Logger.e("nnnnn",khsllist2.size()+"fzwk");

							onHttpResponse(-page, page >= 2 ? null : JSON.toJSONString(khsllist2), null);

//						} catch (JSONException e) {
//							e.printStackTrace();
//						}


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






	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initEvent() {//必须调用
		super.initEvent();

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (id > 0) {
		//	toActivity(UserActivity.createIntent(context, id));
		}
	}


	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}