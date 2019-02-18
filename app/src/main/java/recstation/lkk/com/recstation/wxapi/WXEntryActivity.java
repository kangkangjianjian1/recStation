package recstation.lkk.com.recstation.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.jiguang.share.wechat.WeChatHandleActivity;
import recstation.lkk.com.recstation.R;

public class WXEntryActivity extends WeChatHandleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
