package com.example.administrator.servicetest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.servicetest.service.BindService;
import com.example.administrator.servicetest.service.MyIntentService;
import com.example.administrator.servicetest.service.MyService;
import com.example.administrator.servicetest.service.ServiceUse;

public class MainActivity extends AppCompatActivity {
    // 保持所启动的Service的IBinder对象
    BindService.MyBinder binder;
    // 定义一个ServiceConnection对象
    private ServiceConnection connection = new ServiceConnection() {
        // 当该Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取Service的onBind方法所返回的MyBinder对象

            binder = (BindService.MyBinder) service;
            binder.getCount();
            Log.i("TAG", "MainActivity onServiceConnected()--->");
        }

        // 当该Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("TAG", "MainActivity onServiceDisconnected()--->");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startService(View view) {//开启Service
        Intent intent = new Intent(MainActivity.this, ServiceUse.class);
        startService(intent);
    }

    public void stopService(View view) {//关闭Service
        Intent intent = new Intent(MainActivity.this, ServiceUse.class);
        stopService(intent);
    }

    public void bindService(View view) {
        Intent intent = new Intent(MainActivity.this, BindService.class);
        //绑定Service
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    public void unBindService(View view) {

        //解除绑定Service
        unbindService(connection);
    }

    public void stateService(View view) {

        //获取并显示Services的count值
        int counts = binder.getCount();
        Log.i("TAG", "Service的count值为:" + counts);
        // 获取、并显示Service的count值
        Toast.makeText(MainActivity.this,
                "Service的count值为：" + counts,
                Toast.LENGTH_SHORT).show();
    }

    public void startIntentService(View view) {
        // 创建需要启动的Service的Intent
        Intent intent = new Intent(this, MyIntentService.class);
        // 启动Service
        startService(intent);
    }

    public void forkService(View view) {
        Intent intent = new Intent(this, MyService.class);
        // 启动Service
        startService(intent);
    }
}
