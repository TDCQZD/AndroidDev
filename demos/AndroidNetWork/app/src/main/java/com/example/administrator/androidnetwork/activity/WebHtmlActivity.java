package com.example.administrator.androidnetwork.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.androidnetwork.R;

/**
 * @Description:WebView加载Html
 * @author: Administrator
 * @Date: 2017/10/12 10:28
 */

public class WebHtmlActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        // 获取程序中的WebView组件
        webView = (WebView) findViewById(R.id.web_view);
        StringBuilder sb = new StringBuilder();
        // 拼接一段HTML代码
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title> 欢迎您 </title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h2> 欢迎您访问<a href=\"http://www.baidu.com\">"
                + "百度网页</a></h2>");
        sb.append("</body>");
        sb.append("</html>");
        // 使用简单的loadData方法会导致乱码，可能是Android API的Bug
        // show.loadData(sb.toString() , "text/html" , "utf-8");
        // 加载、并显示HTML代码
        webView.setWebViewClient(new WebViewClient());//界面跳转时，仍然在当前的WebView,而不是打开系统浏览器
        webView.loadDataWithBaseURL(null, sb.toString()
                , "text/html" , "utf-8", null);
    }
}
