package com.example.administrator.androidndkproject;

/**
*
*@author ZD
*created at 2017/6/20 15:13
*description：Java调用对应的C代码的
*/

public class JNIS {
    {
        System.loadLibrary("Hello");
    }

    /**
     * 定义native方法
     * 调用C代码对应的方法
     * @return
     */
    public native String helloJNI();
}
