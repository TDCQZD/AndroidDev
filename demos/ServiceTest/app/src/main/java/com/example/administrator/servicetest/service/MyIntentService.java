package com.example.administrator.servicetest.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
*IntentService使用
*@author ZD
*created at 2017/8/7 14:44
*description：
*/

public class MyIntentService extends IntentService {



    public MyIntentService() {
        super("MyIntentService");//调用父类的有参构造函数
    }
    // IntentService会使用单独的线程来执行该方法的代码
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 该方法内可以执行任何耗时任务，比如下载文件等，此处只是让线程暂停20秒
       long endTime = System.currentTimeMillis() + 20 * 1000;
        Log.i("TAG", "MyIntentService onHandleIntent()--->"+endTime);
        while(System.currentTimeMillis()<endTime) {
            synchronized (this){
                try
                {
                    wait(endTime - System.currentTimeMillis());
                }
                catch (Exception e)
                {
                }
            }
            Log.i("TAG", "耗时任务执行完成");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "MyIntentService onDestroy()--->");
    }
}
