package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import recstation.lkk.com.recstation.util.Logger;
import zuo.biao.library.base.BaseActivity;

public class GonggaoDetailActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, GonggaoDetailActivity.class);
    }

    WebView mWebView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao_detail);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        mWebView = findView(R.id.gonggao_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setDefaultFontSize(16);
        mWebView.getSettings().setBlockNetworkImage(false);
    }

    @Override
    public void initData() {

      url=getIntent().getStringExtra("url");
        Logger.e("ssss",url+"qqq");
      mWebView.loadUrl("http://"+url);

    }

    @Override
    public void initEvent() {

    }
}
