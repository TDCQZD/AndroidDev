package com.example.administrator.okhttptest.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.okhttptest.R;
import com.example.administrator.okhttptest.View.Constants;
import com.example.administrator.okhttptest.utils.CacheResponse;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okio.BufferedSink;

/**
 * @author ：Administrator
 * @Name：OKhttp
 * @date ：2017/10/18 10:53
 * @Description：OKHTTP官网用法
 */

public class OkhttpActivity extends AppCompatActivity {
    //数据类型
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    private OkHttpClient client;
    private TextView tvResult;
    private ProgressBar progressBar;
    private ImageView ivIcon;
    private Response response;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_basic_use);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        tvResult = (TextView) findViewById(R.id.tv_result);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        //设置标题
        textView.setText("OkHTTP基本使用");
//        client = new OkHttpClient();//普通OkHttpClient
        client = new OkHttpClient.Builder() //超时设置
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void cache(View view) {//Okhttp响应缓存
        new Thread() {
            public void run() {
                try {
                    CacheResponse cacheResponse = new CacheResponse(new File("CacheResponse.tmp"));
                    cacheResponse.run();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    public void GET_Synchronous(View view) {//同步GET
        tvResult.setText("");
        new Thread() {
            public void run() {
                try {
                    result = get_Synchronous(Constants.OKHTTP_URL);
                    Log.i("TAG", "GET_Synchronous--->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tvResult.setText(result);
    }


    public void GET_Asynchronous(View view) {//异步GET
        tvResult.setText("");
        new Thread() {
            public void run() {
                try {
                    result = get_Asynchronous(Constants.TEST_URL);
                    Log.i("TAG", "GET_Asynchronous--->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tvResult.setText(result);
    }


    public void Post_String(View view) {//Post方式提交String
        tvResult.setText("");
        final String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";
        new Thread() {
            public void run() {
                String url = "https://api.github.com/markdown/raw";
                try {
                    result = post_String(url, postBody);
                    Log.i("TAG", "Post_String--->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tvResult.setText(result);
    }

    public void Post_Json(View view) {//Post方式提交Json数据
        tvResult.setText("");
        new Thread() {
            public void run() {
                try {
                    String url = "http://www.roundsapp.com/post";
                    String jsonData = "{'winCondition':'HIGH_SCORE',"
                            + "'name':'Bowling',"
                            + "'round':4,"
                            + "'lastSaved':1367702411696,"
                            + "'dateStarted':1367702378785,"
                            + "'players':["
                            + "{'name':'" + "Jesse" + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                            + "{'name':'" + "Jake" + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                            + "]}";
                    ;
                    result = post_Json(url, jsonData);
                    Log.i("TAG", "Post_Json--->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        if (TextUtils.isEmpty(result)) {
            tvResult.setText(result);
        } else {
            tvResult.setText("请求出错");
        }

    }

    public void Post_Block(View view) {//Post方式提交分块请求
        tvResult.setText("");

        new Thread() {
            public void run() {
                String url = "https://api.imgur.com/3/image";
                try {
                    result = post_Multipart(url);
                    Log.i("TAG", "Post_Json--->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    public void Post_IO(View view) {//Post方式提交流
        tvResult.setText("");
        new Thread() {
            public void run() {
                String url = "https://api.github.com/markdown/raw";
                try {
                    result = post_Streaming(url);
                    Log.i("TAG", "Post方式提交流-->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tvResult.setText(result);
    }

    //上传文件
    public void Post_File(View view) {//Post方式提交文件
        tvResult.setText("");
        new Thread() {
            public void run() {
                String url = "https://api.github.com/markdown/raw";
                File file = new File("/sdcard/Download/README.md");
                try {
                    result = post_File(url, file);
                    Log.i("TAG", "Post方式提交文件-->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tvResult.setText(result);
    }

    public void Post_Form(View view) {//Post方式提交表单
        tvResult.setText("");
        new Thread() {
            public void run() {
                String url = "https://en.wikipedia.org/w/index.php";
                try {
                    result = post_Form(url, "");
                    Log.i("TAG", "Post方式提交表单-->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void AccessingHeaders(View view) {//请求头文件数据

        new Thread() {
            public void run() {
                try {
                    accessingHeaders();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void Gson(View v) {//Gson解析数据

        new Thread() {
            public void run() {

                try {
                    result = gsonData();
                    Log.i("TAG", "Gson解析数据--->" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void Authenticate(View v) {//身份认证
        new Thread() {
            public void run() {
                try {
                    result = HandlingAuthentication();
                    Log.i("TAG", "Authenticate--->" + result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tvResult.setText(result);
    }


    /*
    Okhttp同步GET请求
     */
    private String get_Synchronous(String url) throws IOException {
        Request request = new Request.Builder()//OkHttp请求
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            //打印头部信息
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.i("TAG", responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /*
    Okhttp异步GET请求
     */
    private String get_Asynchronous(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//请求失败操作
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {//响应成功操作
                if (response.isSuccessful()) {
                    //打印头部信息
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.i("TAG", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    result = response.body().string();
                    Log.i("TAG", "GET_Asynchronous--->" + result);
                } else {
                    throw new IOException("Unexpected code " + response);
                }

            }
        });

        return result;
    }

    private String gsonData() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/c2a7c39532239ff261be")
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            Gson gson = new Gson();
            Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
            for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                Log.i("TAG", "entry.getKey-->" + entry.getKey());
                Log.i("TAG", "entry.getValue-->" + entry.getValue().content);
            }
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    static class Gist {
        Map<String, GistFile> files;
    }

    static class GistFile {
        String content;
    }

    /*
    Okhttp post请求传递JSon数据
     */
    private String post_Json(String url, String jsonData) throws IOException {

        RequestBody body = RequestBody.create(JSON, jsonData);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Log.i("TAG", "" + response.code());
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /*
   Okhttp post请求传递String数据
    */
    private String post_String(String url, String data) throws IOException {
        Log.i("TAG", "" + url);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /*
  Okhttp post请求传递Streaming数据
   */
    private String post_Streaming(String url) throws IOException {

        //写入流数据
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /*
  Okhttp post请求传递File数据
  注：API>23请自行做授权处理
   */
    private String post_File(String url, File file) throws IOException {

        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String result = response.body().string();
//            Log.i("TAG", "post请求传递File数据-->" + result);
            return result;
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /*
 Okhttp post请求传递Form表单数据
  */
    private String post_Form(String url, String data) throws IOException {
        Log.i("TAG", "" + url);
        RequestBody formBody = new FormBody.Builder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    /*
    Post方式提交multipart数据
     */
    private String post_Multipart(String url) throws IOException {

        RequestBody body = RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/Download/n.png"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("image", "n.png", body)
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    private String response1Body = "";


    private String accessingHeaders() throws IOException {
        Request request = new Request.Builder()
                .url(Constants.OKHTTP_URL)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.i("TAG", "Server: " + response.header("Server"));
            Log.i("TAG", "Date: " + response.header("Date"));
            Log.i("TAG", "Vary: " + response.headers("Vary"));
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private String HandlingAuthentication() throws IOException {//处理身份认证
        client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        Log.i("TAG", "Authenticating for response: " + response);
                        Log.i("TAG", "Challenges: " + response.challenges());
                        String credential = Credentials.basic("jesse", "password1");
                        return response.request().newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                })
                .build();
        Request request = new Request.Builder()
                .url(Constants.OKHTTP_CACHE)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
