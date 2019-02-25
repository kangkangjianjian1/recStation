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
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.RecPerson;
import zuo.biao.library.base.BaseView;
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
public class RecpersonView extends BaseView<RecPerson> {
	private static final String TAG = "RecpersonView";

	public RecpersonView(Activity context, ViewGroup parent) {
		super(context, R.layout.recperson_view, parent);
	}

	public ImageView iv_recpersionViewHead;
	public TextView tv_recperson_name;
	public TextView tv_recperson_price;
	public TextView tv_recperson_phone;
	public TextView tv_recperson_content;
	@SuppressLint("InflateParams")
	@Override
	public View createView() {
		iv_recpersionViewHead = findView(R.id.iv_recpersionViewHead);
		tv_recperson_name = findView(R.id.tv_recperson_name);
		tv_recperson_price = findView(R.id.tv_recperson_price);
		tv_recperson_phone = findView(R.id.tv_recperson_phone);
		tv_recperson_content = findView(R.id.tv_recperson_content);

		return super.createView();
	}

	@Override
	public void bindView(RecPerson data_){
		super.bindView(data_ != null ? data_ : new RecPerson());
//		Glide.with(context).asBitmap().load(data.getPICTUREPATH()).into(iv_recpersionViewHead);
		iv_recpersionViewHead.setImageResource(R.drawable.shanghutouxiang);
//		iv_recpersionViewHead.setBackgroundColor(Color.GREEN);
		tv_recperson_name.setText(StringUtil.getTrimedString(data.getUSERNAME()));
		tv_recperson_price.setText(data.getPROVINCE()+data.getCITY()+data.getAREA());
		tv_recperson_phone.setText(StringUtil.getNoBlankString(data.getUSERNAME()));
		tv_recperson_content.setText(StringUtil.getNoBlankString(data.getRETRIEVETYPE_NAMES()));
	}


	}