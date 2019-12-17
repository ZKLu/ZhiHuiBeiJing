package com.samlu.zhbj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsDetailActivity extends Activity implements View.OnClickListener{
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
    @ViewInject(R.id.pb_loading)
    private ProgressBar pb_loading;
    private int currentTextZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        ViewUtils.inject(this);
        initView();

        String url = getIntent().getStringExtra("url");

        wv_webview.loadUrl(url);
        WebSettings settings = wv_webview.getSettings();
        settings.setJavaScriptEnabled(true);//启用js功能
        settings.setBuiltInZoomControls(true);//页面右下角有放大缩小的按钮，不支持已经适配好移动端的页面
        currentTextZoom = settings.getTextZoom();
        //给wv_webview设置监听,可以监听网页加载的开始、跳转和加载结束
        wv_webview.setWebViewClient(new WebViewClient(){
            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv_webview.loadUrl(url);
                //返回true的作用是本应用加载网页，不用跳转到浏览器
                return true;
            }

            //页面加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_loading.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        ib_back.setVisibility(View.VISIBLE);
        ib_menu.setVisibility(View.GONE);
        ll_control.setVisibility(View.VISIBLE);
        ib_back.setOnClickListener(this);
        ib_text_size.setOnClickListener(this);
        ib_share.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (wv_webview.canGoBack()){
            wv_webview.goBack();
        }else {
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_text_size:
                showChooseDialog();
                break;
            case R.id.ib_share:
            break;
        }
    }

    private int mTempWhich;
    private int mCurrentWhich = 2;//当前选中的字体大小
    /**显示选择字体大小的弹窗
    *@param
    *@return
    */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        String[] items = new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
        builder.setSingleChoiceItems(items, mCurrentWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTempWhich = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = wv_webview.getSettings();

                switch (mTempWhich){
                    case 0:
                        //超大号字体
                        settings.setTextZoom(currentTextZoom+20);
                        break;
                    case 1:
                        //大号字体
                        settings.setTextZoom(currentTextZoom+10);
                        break;
                    case 2:
                        //正常字体
                        settings.setTextZoom(currentTextZoom);
                        break;
                    case 3:
                        settings.setTextZoom(currentTextZoom-10);
                        break;
                    case 4:
                        settings.setTextZoom(currentTextZoom-20);
                        break;
                }
                mCurrentWhich = mTempWhich;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
