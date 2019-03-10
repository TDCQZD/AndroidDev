package com.example.administrator.rxjavademo.Utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 时间转换类
 * @author: Administrator
 * @Date: 2017/10/13 11:52
 */

public class TimeUtil {

    public static String getNowStrTime() {
        long time = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
