package com.example.administrator.daggerdemo;

import android.util.Log;

import javax.inject.Inject;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/9/19 9:37
 */

public class ApiService {
    @Inject
    public ApiService() {
    }

    public void show() {
        Log.i("TAG", "Dagger使用");
    }
}
