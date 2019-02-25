package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import recstation.lkk.com.recstation.util.TestUtil;
import zuo.biao.library.base.BaseActivity;

public class SucessActivity extends BaseActivity implements View.OnClickListener {

    Button btn;
    ImageView mImageView;

    public static Intent createIntent(Context context) {
        return new Intent(context, SucessActivity.class);
    }

    String title = "成功";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        mImageView = findView(R.id.iv_sucess);
        btn = findView(R.id.btn_sucess, this);
        title = getIntent().getStringExtra("title");
    }

    @Override
    public void initData() {

        btn.setText(title);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View view) {
        finish();
//        if (view.getId() == R.id.btn_sucess) {
//
//            ((MainTabActivity) context).selectFragment(1);
//        }
    }

}
