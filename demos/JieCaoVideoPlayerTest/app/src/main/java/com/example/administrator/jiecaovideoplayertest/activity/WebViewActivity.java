package com.example.administrator.jiecaovideoplayertest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;

import com.example.administrator.jiecaovideoplayertest.R;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCUtils;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author ZD
 *         created at 2017/6/29 11:19
 *         description：在WebView中使用
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        /*
        标题栏设置
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("AboutWebView");

        webView = (WebView) findViewById(R.id.webview);
        //设置值JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //添加JavascriptInterface
        webView.addJavascriptInterface(new JCCallBack(), "jcvd");
        webView.loadUrl("file:///android_asset/jcvd.html");
    }

    private class JCCallBack {

        @JavascriptInterface
        public void adViewJieCaoVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        //代码实例化
                        JCVideoPlayerStandard webVieo = new JCVideoPlayerStandard(WebViewActivity.this);
                        //使用标准的UI控件
                        webVieo.setUp("http://video.jiecao.fm/11/16/c/68Tlrc9zNi3JomXpd-nUog__.mp4",
                                JCVideoPlayer.SCREEN_LAYOUT_LIST, "嫂子骑大马");
                        Picasso.with(WebViewActivity.this)
                                .load("http://img4.jiecaojingxuan.com/2016/11/16/1d935cc5-a1e7-4779-bdfa-20fd7a60724c.jpg@!640_360")
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JCUtils.dip2px(WebViewActivity.this, top);
                        layoutParams.x = JCUtils.dip2px(WebViewActivity.this, left);
                        layoutParams.height = JCUtils.dip2px(WebViewActivity.this, height);
                        layoutParams.width = JCUtils.dip2px(WebViewActivity.this, width);
                        //添加到webview中
                        webView.addView(webVieo, layoutParams);
                    } else {
                        //代码实例化
                        JCVideoPlayerStandard webVieo = new JCVideoPlayerStandard(WebViewActivity.this);
                        //使用标准的UI控件
                        webVieo.setUp("http://video.jiecao.fm/11/14/xin/%E5%90%B8%E6%AF%92.mp4",
                                JCVideoPlayer.SCREEN_LAYOUT_LIST, "嫂子失态了");
                        Picasso.with(WebViewActivity.this)
                                .load("http://img4.jiecaojingxuan.com/2016/11/14/a019ffc1-556c-4a85-b70c-b1b49811d577.jpg@!640_360")
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JCUtils.dip2px(WebViewActivity.this, top);
                        layoutParams.x = JCUtils.dip2px(WebViewActivity.this, left);
                        layoutParams.height = JCUtils.dip2px(WebViewActivity.this, height);
                        layoutParams.width = JCUtils.dip2px(WebViewActivity.this, width);
                        webView.addView(webVieo, layoutParams);
                    }

                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
