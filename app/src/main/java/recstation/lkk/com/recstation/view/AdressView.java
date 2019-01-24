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

package recstation.lkk.com.recstation.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import recstation.lkk.com.recstation.AddAdressActivity;
import recstation.lkk.com.recstation.AdressListActivity;
import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.Adress;
import recstation.lkk.com.recstation.model.User;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;

/**用户View
 * @author Lemon
 * @use
 * <br> UserView userView = new UserView(context, resources);
 * <br> adapter中使用:[具体参考.BaseViewAdapter(getView使用自定义View的写法)]
 * <br> convertView = userView.createView(inflater);
 * <br> userView.bindView(position, data);
 * <br> 或  其它类中使用:
 * <br> containerView.addView(userView.createView(inflater));
 * <br> userView.bindView(data);
 * <br> 然后
 * <br> userView.setOnDataChangedListener(onDataChangedListener);data = userView.getData();//非必需
 * <br> userView.setOnClickListener(onClickListener);//非必需
 */
public class AdressView extends BaseView<Adress> implements OnClickListener {
	private static final String TAG = "AdressView";

	public AdressView(Activity context, ViewGroup parent) {
		super(context, R.layout.adress_view, parent);
	}
	public LinearLayout ll_adressview_edit;
	public TextView tv_adressview_adress_name;
	public TextView tv_adressview_isdefault;
	public TextView tv_adressview_phone;
	public TextView tv_adressview_name;
	@SuppressLint("InflateParams")
	@Override
	public View createView() {
		ll_adressview_edit = findView(R.id.ll_adressview_edit, this);

		tv_adressview_adress_name = findView(R.id.tv_adressview_adress_name, this);

		tv_adressview_isdefault = findView(R.id.tv_adressview_isdefault);
		tv_adressview_phone = findView(R.id.tv_adressview_phone);
		tv_adressview_name = findView(R.id.tv_adressview_name);

		return super.createView();
	}

	@Override
	public void bindView(Adress data_){
		super.bindView(data_ != null ? data_ : new Adress());

		tv_adressview_adress_name.setText(data.getAdressName());
		tv_adressview_isdefault.setBackgroundColor(getColor(data.getIsDefault().equals("1") ? R.color.green2 : R.color.gray_1));

		tv_adressview_phone.setText(":"+StringUtil.getTrimedString(data.getPhone()));
		tv_adressview_name.setText(data.getName());
	}

	@Override
	public void onClick(View v) {
//		if (BaseModel.isCorrect(data) == false) {
//			return;
//		}
		switch (v.getId()) {
		case R.id.ll_adressview_edit:

			Intent i = AddAdressActivity.createIntent(context);
			i.putExtra("name",data.getName());
			i.putExtra("phone",data.getPhone());
			i.putExtra("adressName",data.getAdressName());
			i.putExtra("realAdress",data.getRealAdress());
			i.putExtra("isDefault",data.getIsDefault());
			i.putExtra("id","110");
			context.startActivity(i);
			//toActivity(WebViewActivity.createIntent(context, data.getName(), data.getHead()));
			break;
		default:
			break;
		}
	}
}