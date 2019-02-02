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

package recstation.lkk.com.recstation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.model.HuishouBean;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;


/**
 * 通用网格Adapter(url, name)
 * *适用于gridView
 *
 * @author lkk
 * @use new GridAdapter(...); 具体参考.DemoAdapter
 */
public class GridHuishouAdapter extends BaseAdapter {
    private static final String TAG = "GridHuishouAdapter";

    public GridHuishouAdapter(Activity context) {
        this(context, R.layout.grid_huishou_item);
    }

    private final Activity context;
    private final LayoutInflater inflater;

    public GridHuishouAdapter(Activity context, int layoutRes) {
        this.context = context;
        this.inflater = context.getLayoutInflater();
        setLayoutRes(layoutRes);
    }


    private int layoutRes;//item视图资源

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    private boolean hasName = true;//是否显示名字

    public GridHuishouAdapter setHasName(boolean hasName) {
        this.hasName = hasName;
        return this;
    }

    private boolean hasCheck = false;//是否使用标记功能

    public GridHuishouAdapter setHasCheck(boolean hasCheck) {
        this.hasCheck = hasCheck;
        return this;
    }

    //item标记功能，不需要可以删除<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private HashMap<Integer, Boolean> hashMap;//实现选中标记的列表，不需要可以删除。这里可用List<Integer> checkedList代替

    public boolean getItemChecked(int position) {
        if (hasCheck == false) {
            Log.e(TAG, "<<< !!! hasCheck == false  >>>>> ");
            return false;
        }
        return hashMap.get(position);
    }

    public void setItemChecked(int position, boolean isChecked) {
        if (hasCheck == false) {
            Log.e(TAG, "<<< !!! hasCheck == false >>>>> ");
            return;
        }
        hashMap.put(position, isChecked);
        notifyDataSetChanged();
    }
    //item标记功能，不需要可以删除>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    private List<HuishouBean> list;

    /**
     * 刷新列表
     *
     * @param list
     */
    public synchronized void refresh(List<HuishouBean> list) {
        if (list != null && list.size() > 0) {
            initList(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 标记List<String>中的值是否已被选中。
     * 不需要可以删除，但“this.list = list;”这句
     * 要放到constructor【这个adapter只有ModleAdapter(Context context, List<Object> list)这一个constructor】里去
     *
     * @param list
     * @return
     */
    @SuppressLint("UseSparseArrays")
    private void initList(List<HuishouBean> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public HuishouBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public int selectedCount = 0;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
        if (holder == null) {
            convertView = inflater.inflate(layoutRes, parent, false);

            holder = new ViewHolder();
            holder.ivGridItemHead = convertView.findViewById(R.id.ivGridItemHead);
            holder.tvGridItemName = convertView.findViewById(R.id.tvGridItemName);
            holder.tvGridItemPrice = convertView.findViewById(R.id.tvGridItemTime);
            holder.tvGridItem_personname = convertView.findViewById(R.id.tvGridItem_personname);


            convertView.setTag(holder);
        }

        final HuishouBean kvb = getItem(position);
        final String adress = kvb.getPROVINCE()+kvb.getCITY()+kvb.getAREA();
        final String time =kvb.getCREATETIME();
        final String phone = kvb.getYDATE();

        Glide.with(context).load(kvb.getPICTUREPATH()).into(holder.ivGridItemHead);

        holder.tvGridItemName.setVisibility(View.VISIBLE);
        holder.tvGridItemName.setText(StringUtil.getTrimedString(adress));
        holder.tvGridItemPrice.setText(StringUtil.getTrimedString(time));
        holder.tvGridItem_personname.setText(StringUtil.getTrimedString(phone));


        return convertView;
    }

    static class ViewHolder {
        public ImageView ivGridItemHead;
        public TextView tvGridItemName;
        public TextView tvGridItemPrice;
        public TextView tvGridItem_personname;
    }


}
