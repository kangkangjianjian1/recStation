package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import zuo.biao.library.base.BaseActivity;

public class ProDetailActivity extends BaseActivity {
    ImageView mImageView1,mImageView2,mImageView3;
    public static Intent createIntent(Context context) {
        return new Intent(context, ProDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_detail);

        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        mImageView1 = findView(R.id.imageView1);
        mImageView2 = findView(R.id.imageView2);
        mImageView3 = findView(R.id.imageView3);
    }

    @Override
    public void initData() {
        int id1 = getIntent().getIntExtra("id1",0);
        int id2 = getIntent().getIntExtra("id2",0);
        int id3 = getIntent().getIntExtra("id3",0);
        if (id1!=0){
            mImageView1.setImageResource(id1);
        }
        if (id2!=0){
            mImageView2.setImageResource(id2);
        }
        if (id3!=0){
            mImageView3.setImageResource(id3);
        }

    }

    @Override
    public void initEvent() {

    }
}
