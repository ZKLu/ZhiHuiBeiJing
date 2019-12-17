package com.samlu.zhbj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsDetailActivity extends AppCompatActivity {
    @ViewInject(R.id.ib_back)
    private ImageButton ib_back;
    @ViewInject(R.id.ib_menu)
    private ImageButton ib_menu;
    @ViewInject(R.id.ll_control)
    private LinearLayout ll_control;
    @ViewInject(R.id.ib_text_size)
    private ImageButton ib_text_size;
    @ViewInject(R.id.ib_share)
    private ImageButton ib_share;
    @ViewInject(R.id.wv_webview)
    private WebView wv_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);

        ViewUtils.inject(this);
        initView();
        wv_webview.loadUrl();
        WebSettings settings = wv_webview.getSettings();
        settings.setJavaScriptEnabled(true);//启用js功能
        //给wv_webview设置监听,可以监听网页加载的开始、跳转和加载结束
        wv_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv_webview.loadUrl(url);
                //返回true的作用是本应用加载网页，不用跳转到浏览器
                return true;
            }
        });
    }

    private void initView() {
        ib_back.setVisibility(View.VISIBLE);
        ib_menu.setVisibility(View.GONE);
        ll_control.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (wv_webview.canGoBack()){
            wv_webview.goBack();
        }else {
            finish();
        }

    }
}
