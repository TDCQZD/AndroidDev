package com.example.administrator.androidnetwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.androidnetwork.R;

/**
 * @author ：Administrator
 * @Description：Android网络处理：WebView、HttpURLConnection
 * @date ：2017/9/7 15:51
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * WebView使用
     */
    public void webview_net(View view) {//加载Url
        startActivity(new Intent(MainActivity.this, WebViewActivity.class));
    }

    public void webview_html(View view) {//加载HTML
        startActivity(new Intent(MainActivity.this, WebHtmlActivity.class));
    }

    public void webview_js(View view) {//JS调用
        startActivity(new Intent(MainActivity.this, JavaAndJSActivity.class));
    }

    /**
     * HttpURLConnection的封装使用
     */
    /*
    带参POST
     */
//    String str = "name=" + URLEncoder.encode(name, "UTF-8") + "&" + "identified=" + URLEncoder.encode(identified, "UTF-8") + "&" + "userName=" + URLEncoder.encode(username, "UTF-8") + "&" + "certSN=" + URLEncoder.encode(certSN, "UTF-8") + "&" + "serial=" + URLEncoder.encode(serial, "UTF-8") + "&" + "appCode=" + URLEncoder.encode(appCode, "UTF-8") + "&" + "method=" + URLEncoder.encode(method, "UTF-8");
//    state = HttpUtils.doPost(ParameterList.PATH_USER, str);
//    String result = HttpUtils.doPost(Constants.BASE_URL, str);
    /*
    带参GET
     */
//    String str = "uuid=" + URLEncoder.encode(uuid, "UTF-8");
//    urlData = HttpUtils.sendGet(url, str);
    /*
    异步的Get请求
     */
//      HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
//        @Override
//        public void onRequestComplete(String result){
//            try {
//                JSONObject jsonObject=new JSONObject(result);
//                String returncode=jsonObject.getString("returncode");
//                if (returncode.equals("0")){
//                    JSONArray list=new JSONArray();
//                    list=jsonObject.getJSONArray("list");
//                    addressList.clear();
//                    for (int i = 0; i <list.length() ; i++) {
//                        Gson gson=new Gson();
//                        addressList.add(gson.fromJson(list.get(i).toString(),AdressItem.class));
//                    }
//                    mHandler.sendEmptyMessage(1);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    });

}
