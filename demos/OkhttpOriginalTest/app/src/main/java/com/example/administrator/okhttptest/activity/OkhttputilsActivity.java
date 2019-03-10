package com.example.administrator.okhttptest.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.okhttptest.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.example.administrator.okhttptest.R.id.iv_icon;
import static com.example.administrator.okhttptest.R.id.tv_result;


/**
 * 二次封装库okhttputils
 *
 * @author ZD
 *         created at 2017/7/27 17:30
 *         description：
 */
public class OkhttputilsActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    /**
     * get请求
     */
    private static final int GET = 1;
    /**
     * post请求
     */
    private static final int POST = 2;
    private TextView tvResult;
    private OkHttpClient client = new OkHttpClient();
    private ProgressBar mProgressBar;
    private ImageView ivIcon;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    //获取数据
                    tvResult.setText((String) msg.obj);
                    break;
                case POST:
                    //获取数据
                    tvResult.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_file_processing);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("二次封装库okhttputils");
        tvResult = (TextView) findViewById(tv_result);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        ivIcon = (ImageView) findViewById(iv_icon);
    }

    public void get_okhttputils(View view) {
        new Thread() {
            public void run() {
                getDataGetByOkhttpUtils();
            }
        }.start();
    }

    public void post_okhttputils(View view) {
        new Thread() {
            public void run() {
                getDataPostByOkhttpUtils();
            }
        }.start();
    }

    public void downloadfile(View view) {
        new Thread() {
            public void run() {
                downloadFile();
            }
        }.start();
    }

    public void uploadfile(View view) {
        new Thread() {
            public void run() {
                multiFileUpload();
            }
        }.start();
    }

    public void getImage(View view) {
        new Thread() {
            public void run() {
                getImage();
            }
        }.start();
    }

    /**
     * 使用okhttp-utils的get请求网络文本数据
     */
    public void getDataGetByOkhttpUtils() {

        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 使用okhttp-utils的post请求网络文本数据
     */
    public void getDataPostByOkhttpUtils() {

        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 使用okhttp-utils下载大文件
     */
    public void downloadFile() {
        String url = "http://vfx.mtime.cn/Video/2016/07/24/mp4/160724055620533327_480.mp4";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-utils-test.mp4")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressBar.setProgress((int) (100 * progress));
                        Log.d("TAG", "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("TAG", "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.d("TAG", "onResponse :" + file.getAbsolutePath());
                    }
                });
    }


    /**
     * 使用okhttp-utils上传多个或者单个文件
     */
    public void multiFileUpload() {
        String mBaseUrl = "https://api.github.com/markdown/raw";
        File file = new File(Environment.getExternalStorageDirectory(), "n.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test.txt");
        if (!file.exists() || !file2.exists()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkhttputilsActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "杨光福");
        params.put("password", "123");

        String url = mBaseUrl;
        OkHttpUtils.post()//
                .addFile("mFile", "server_n.png", file)//
                .addFile("mFile", "server_test.txt", file2)//
                .url(url)
//                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    /**
     * 使用okhttp-utils请求网络图片
     */
    public void getImage() {

        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tvResult.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        ivIcon.setImageBitmap(bitmap);
                    }
                });
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("okHttp封装");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tvResult.setText("onError:" + e.getMessage());
            Log.d("TAG", "onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d("TAG", "onResponse：complete");
            Log.d("TAG", "onResponse:" + response);
            tvResult.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(OkhttputilsActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkhttputilsActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.d("YAG", "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }
}
