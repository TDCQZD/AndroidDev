package com.example.administrator.projectarchitecture;

import android.app.Application;

/**
 * @Description:自定义全局 application
 * @author: Administrator
 * @Date: 2017/9/6 11:39
 * 主要进全局引用,行存储全局变量,全局配置/设置,初始化等相关工作
 */

public class App extends Application {
    private static App instance;
    public  static App getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance=this;
    }
}
