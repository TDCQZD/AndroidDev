package com.example.administrator.androidnetwork.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.androidnetwork.R;

/**
 * @Description:WebView显示网页
 * @author: Administrator
 * @Date: 2017/10/12 9:49
 *
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);//支持JavaScript脚本
        webView.setWebViewClient(new WebViewClient());//界面跳转时，仍然在当前的WebView,而不是打开系统浏览器
        webView.loadUrl("http://www.baidu.com");
    }
}
