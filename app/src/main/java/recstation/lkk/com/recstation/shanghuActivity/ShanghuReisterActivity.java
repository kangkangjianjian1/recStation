package recstation.lkk.com.recstation.shanghuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import recstation.lkk.com.recstation.R;
import recstation.lkk.com.recstation.util.AppToast;
import recstation.lkk.com.recstation.util.Logger;
import recstation.lkk.com.recstation.util.NetUtil;
import zuo.biao.library.base.BaseActivity;

public class ShanghuReisterActivity extends BaseActivity{
    TextView tv_next;
    EditText shanghu_area;
    EditText shanghu_city;
    EditText shanghu_province;
    EditText shanghu_account;
    EditText shanghu_address;
    EditText shanghu_farenname;
    EditText shanghu_name;
    EditText shanghu_mobile;
    public static Intent createIntent(Context context) {
        return new Intent(context, ShanghuReisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanghu_reister);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

         tv_next =findView(R.id.shanghu_next);
         shanghu_area=findView(R.id.shanghu_area);
         shanghu_city=findView(R.id.shanghu_city);
         shanghu_province=findView(R.id.shanghu_province);
         shanghu_account=findView(R.id.shanghu_account);
         shanghu_address=findView(R.id.shanghu_address);
         shanghu_farenname=findView(R.id.shanghu_farenname);
         shanghu_name=findView(R.id.shanghu_name);
         shanghu_mobile=findView(R.id.shanghu_mobile);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (prepareForRegister()){
//                    return;
//                }
                Intent i = ShanghuReisterPhotoActivity.createIntent(ShanghuReisterActivity.this);
                i.putExtra("shanghu_area",shanghu_area.getText().toString());
                Logger.e("shanghu_name",shanghu_name.getText().toString());
                i.putExtra("shanghu_city",shanghu_city.getText().toString());
                i.putExtra("shanghu_province",shanghu_province.getText().toString());
                i.putExtra("shanghu_account",shanghu_account.getText().toString());
                i.putExtra("shanghu_address",shanghu_address.getText().toString());
                i.putExtra("shanghu_farenname",shanghu_farenname.getText().toString());
                i.putExtra("shanghu_name",shanghu_name.getText().toString());
                i.putExtra("shanghu_mobile",shanghu_mobile.getText().toString());
                startActivity(i);
            }
        });

    }


    private boolean prepareForRegister() {
        if (!NetUtil.isNetConnected(this)) {
            AppToast.showShortText(ShanghuReisterActivity.this,"没有网络！");
            return true;
        }

        if (shanghu_area.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"县城名称不能为空!");
            shanghu_area.requestFocus();
            return true;
        }
        if (shanghu_city.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"城市不能为空!");
            shanghu_city.requestFocus();
            return true;
        }

        if (shanghu_province.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"省不能为空");
            shanghu_province.requestFocus();
            return true;
        }

        if (shanghu_account.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"对公账户不能为空");
            shanghu_account.requestFocus();
            return true;
        }
        if (shanghu_address.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"地址不能为空");
            shanghu_address.requestFocus();
            return true;
        }
        if (shanghu_farenname.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"法人不能为空");
            shanghu_farenname.requestFocus();
            return true;
        }
        if (shanghu_name.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"姓名不能为空");
            shanghu_name.requestFocus();
            return true;
        }
        if (shanghu_mobile.getText().length() == 0) {
            AppToast.showShortText(ShanghuReisterActivity.this,"手机不能为空");
            shanghu_mobile.requestFocus();
            return true;
        }
        return false;
    }

}
