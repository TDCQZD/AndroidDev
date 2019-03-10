package com.example.administrator.servicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
*绑定本地服务并与Activity通信
*@author ZD
*created at 2017/8/7 14:06
*description：
*/

public class BindService extends Service {
    private int count;
    private boolean quit;
    // 定义onBinder方法所返回的对象
    private MyBinder binder = new MyBinder();
    // 通过继承Binder来实现IBinder类
    public class MyBinder extends Binder  // ①
    {
        public int getCount()
        {
            // 获取Service的运行状态：count
            return count;
        }
    }

    // 必须实现的方法，绑定该Service时回调该方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TAG", "BindService onBind()--->");
        return binder;
    }
    // Service被创建时回调该方法
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "BindService onCreate()--->");
        new Thread(){
            public void run(){
                while(!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }.start();
    }
    // Service被断开连接时回调该方法
    @Override
    public boolean onUnbind(Intent intent) {
       Log.i("TAG", "BindService onUnbind()--->");
        return true;
    }
    // Service被关闭之前回调该方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit=true;
        Log.i("TAG", "BindService onDestroy()--->");
    }
}
