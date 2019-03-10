package com.example.administrator.frescotest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
*
*@author ZD
*created at 2017/6/27 11:12
*description：在全局变量初始化Fresco
*/

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
