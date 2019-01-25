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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.Msg;
import recstation.lkk.com.recstation.model.Point;
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
public class PointView extends BaseView<Point> {
	private static final String TAG = "PointView";

	public PointView(Activity context, ViewGroup parent) {
		super(context, R.layout.point_view, parent);
	}



	public TextView tv_point_view_time;
	public TextView tv_point_view_num;
	public TextView tv_point_view_content;
	@SuppressLint("InflateParams")
	@Override
	public View createView() {


		tv_point_view_time = findView(R.id.tv_point_view_time);
		tv_point_view_num = findView(R.id.tv_point_view_num);
		tv_point_view_content = findView(R.id.tv_point_view_content);

		return super.createView();
	}

	@Override
	public void bindView(Point data_){
		super.bindView(data_ != null ? data_ : new Point());


		tv_point_view_time.setText(StringUtil.getTrimedString(data.getTime()));
		tv_point_view_num.setText(data.getNum()+"");
		tv_point_view_content.setText(data.getTitle());
	}


	}