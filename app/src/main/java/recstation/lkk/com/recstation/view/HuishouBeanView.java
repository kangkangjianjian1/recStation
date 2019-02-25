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
import android.graphics.Picture;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.HuishouBean;
import recstation.lkk.com.recstation.model.User;
import recstation.lkk.com.recstation.util.Logger;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.base.BaseView;
import zuo.biao.library.ui.WebViewActivity;
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
public class HuishouBeanView extends BaseView<HuishouBean> {
    private static final String TAG = "HuishouBeanView";

    String type = "0";

    public HuishouBeanView(Activity context, ViewGroup parent) {
        super(context, R.layout.huishoubean_view, parent);
    }

    public HuishouBeanView(Activity context, ViewGroup parent, String type) {
        super(context, R.layout.huishoubean_view, parent);
        this.type = type;
    }

    public ImageView iv_huishouViewHead;
    public LinearLayout huishouview_select_layout;
    public CheckBox huishouview_select;


    public TextView tv_huishoubeanView_typename;
    public TextView tv_huishoubean_miaoshu;
    public TextView tv_huishoubeanView_typeprice;

    @SuppressLint("InflateParams")
    @Override
    public View createView() {
        iv_huishouViewHead = findView(R.id.iv_huishouViewHead);
        huishouview_select_layout = findView(R.id.huishouview_select_layout);
        huishouview_select = findView(R.id.huishouview_select);

        tv_huishoubeanView_typename = findView(R.id.tv_huishoubeanView_typename);
        tv_huishoubean_miaoshu = findView(R.id.tv_huishoubean_miaoshu);
        tv_huishoubeanView_typeprice = findView(R.id.tv_huishoubeanView_typeprice);

        return super.createView();
    }

    @Override
    public void bindView(final HuishouBean data_) {
        super.bindView(data_ != null ? data_ : new HuishouBean());

        Glide.with(context).asBitmap().load(data.getPICTUREPATH()).into(iv_huishouViewHead);
        tv_huishoubeanView_typename.setText(StringUtil.getTrimedString(data.getRETRIEVETYPE_NAME()));
        tv_huishoubean_miaoshu.setText(data.getRETRIEVETYPE_NAME());
        tv_huishoubeanView_typeprice.setText(StringUtil.getNoBlankString("7") + "元/公斤");

        //huishouview_select.setChecked();
        if ("".equals(data.getSTATUS()) || null == data.getSTATUS()) {
            huishouview_select.setChecked(false);

        } else {
            huishouview_select.setChecked(true);

        }


        huishouview_select.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (huishouview_select.isChecked()) {

                    data.setSTATUS("000");
                    //	showShortToast(selected+data_.getRETRIEVETYPE_NAME());

                } else {
                    data.setSTATUS("");
                    //showShortToast(selected+data_.getRETRIEVETYPE_NAME());
                }

//				showShortToast(data_.getRETRIEVETYPE_NAME()+"ddd");
            }
        });

        if (type.equals("1")) {
            huishouview_select_layout.setVisibility(View.VISIBLE);
        } else {
            huishouview_select_layout.setVisibility(View.GONE);
        }


    }


}