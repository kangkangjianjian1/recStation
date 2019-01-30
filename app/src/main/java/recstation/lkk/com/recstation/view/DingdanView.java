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
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.Dingdanbean;
import recstation.lkk.com.recstation.model.User;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.util.CommonUtil;
import zuo.biao.library.util.StringUtil;

/**
 * 用户View
 *
 * @author Lemon
 * @use <br> UserView userView = new UserView(context, resources);
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
public class DingdanView extends BaseView<Dingdanbean> {
    private static final String TAG = "DingdanView";

    public DingdanView(Activity context, ViewGroup parent) {
        super(context, R.layout.dingdanbean_view, parent);
    }

    public ImageView iv_dingdanbeanview_Head;


    public TextView tv_dingdanbeanview_fenlei;

    public TextView tv_dingdanbeanview_time;
    public TextView tv_dingdanbeanview_adress;
    public TextView tv_dingdanbeanview_status;

    @SuppressLint("InflateParams")
    @Override
    public View createView() {
        iv_dingdanbeanview_Head = findView(R.id.iv_dingdanbeanview_Head);

        tv_dingdanbeanview_fenlei = findView(R.id.tv_dingdanbeanview_fenlei);

        tv_dingdanbeanview_adress = findView(R.id.tv_dingdanbeanview_adress);
        tv_dingdanbeanview_time = findView(R.id.tv_dingdanbeanview_time);
        tv_dingdanbeanview_status = findView(R.id.tv_dingdanbeanview_status);

        return super.createView();
    }

    @Override
    public void bindView(Dingdanbean data_) {
        super.bindView(data_ != null ? data_ : new Dingdanbean());

        iv_dingdanbeanview_Head.setImageResource(R.drawable.ic_launcher);
        tv_dingdanbeanview_fenlei.setText(data.getRETRIEVETYPE_NAME());
        tv_dingdanbeanview_adress.setText(data.getPROVINCE()+data.getCITY()+data.getAREA()+data.getADDRESS());
        tv_dingdanbeanview_time.setText(data.getYDATE()+" "+data.getYTIME());


        //根据订单状态显示，目前应该市没有此数据。
        tv_dingdanbeanview_status.setText("等待分配");

    }

}