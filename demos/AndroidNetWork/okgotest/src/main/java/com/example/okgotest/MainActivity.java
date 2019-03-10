package com.example.okgotest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * @author ：Administrator
 * @Description：OKGO使用
 * @date ：2017/9/7 17:05
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvResult;
    private ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView) findViewById(R.id.tv_result);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
    }

    public void GET(View view) {
        tvResult.setText("请求返回数据");
        new Thread() {
            public void run() {
                OkGo.<String>
                        get(Constants.TEST_URL)//GET请求
                        .tag(this)//取消对应的请求的设置
                        .retryCount(3)
                        .cacheKey("cache")// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                        .cacheMode(CacheMode.DEFAULT) // 缓存模式，详细请看缓存介绍
                        .cacheTime(5000)//缓存的过期时间，单位毫秒
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.i("TAG", "" + response.body().toString());
                                tvResult.setText(response.body().toString());
                            }

                            @Override
                            public void onCacheSuccess(Response<String> response) {
                                super.onCacheSuccess(response);
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        })
                ;
            }
        }.start();
    }

    public void Post(View v) {
        tvResult.setText("请求返回数据");
        iv_icon.setImageResource(R.mipmap.ic_launcher);
        new Thread() {
            public void run() {
                OkGo.<String>
                        post(Constants.OKHTTP_STRING)
                        .tag(this)//取消对应的请求的设置
                        .retryCount(3)
                        .cacheKey("cache")// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                        .cacheMode(CacheMode.DEFAULT) // 缓存模式，详细请看缓存介绍
                        .cacheTime(5000)//缓存的过期时间，单位毫秒
                        .params("username", "lisi")
                        .params("password", "123456")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.i("TAG", "" + response.body().toString());
                                tvResult.setText(response.body().toString());
                            }

                            @Override
                            public void onError(Response<String> response) {
//                                super.onError(response);
//                                Log.i("TAG", "" + response.body().toString());
//                                tvResult.setText(response.body().toString());
                            }
                        });
                ;
            }
        }.start();
    }

    public void images(View view) {
        tvResult.setText("请求返回数据");
        iv_icon.setImageResource(R.mipmap.ic_launcher);
        new Thread() {
            public void run() {
                OkGo.<Bitmap>
                        get(Constants.BITMAP_IMAGES)
                        .tag(this)//取消对应的请求的设置
                        .retryCount(3)
                        .cacheKey("Bitmap")// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                        .cacheMode(CacheMode.DEFAULT) // 缓存模式，详细请看缓存介绍
                        .cacheTime(5000)//缓存的过期时间，单位毫秒
                        .execute(new BitmapCallback() {
                            @Override
                            public void onSuccess(Response<Bitmap> response) {
                                iv_icon.setImageBitmap(response.body());
                            }

                        });
            }
        }.start();
    }

    public void download(View view) {
        new Thread() {
            public void run() {
                OkGo.<File>get(Constants.OKHTTP_URL)
                        .tag(this)
                        .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {

                    }

                });
            }
        }.start();
    }

    public void upLoad(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
}
