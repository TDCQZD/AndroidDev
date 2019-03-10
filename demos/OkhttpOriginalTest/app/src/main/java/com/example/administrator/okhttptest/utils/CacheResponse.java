package com.example.administrator.okhttptest.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.administrator.okhttptest.View.Constants;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp缓存处理
 */

public class CacheResponse {

    /*
    1、创建私有OkHttpClient
     */
    private final OkHttpClient client;

    /*
    2、创建构造器 设置缓存的大小
     */
    public CacheResponse(File cacheDirectory) throws Exception {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);

        client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run() throws Exception {
        Request request = new Request.Builder()
                .url(Constants.OKHTTP_CACHE)
                .build();

        String response1Body;
        try (Response response1 = client.newCall(request).execute()) {
            if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

            response1Body = response1.body().string();
            Log.i("TAG","Response 1 response:          " + response1);
             Log.i("TAG","Response 1 cache response:    " + response1.cacheResponse());
             Log.i("TAG","Response 1 network response:  " + response1.networkResponse());
        }

        String response2Body;
        try (Response response2 = client.newCall(request).execute()) {
            if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

            response2Body = response2.body().string();
             Log.i("TAG","Response 2 response:          " + response2);
             Log.i("TAG","Response 2 cache response:    " + response2.cacheResponse());
             Log.i("TAG","Response 2 network response:  " + response2.networkResponse());
        }

         Log.i("TAG","Response 2 equals Response 1? " + response1Body.equals(response2Body));
    }


}
