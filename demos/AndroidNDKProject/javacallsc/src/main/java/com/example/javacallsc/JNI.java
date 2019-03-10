package com.example.javacallsc;

import static android.R.attr.y;

/**
 * Author: Administrator
 * Time: 2017/6/20  15:30
 * Description：Java调用C代码
 */

public class JNI {

    {
        System.loadLibrary("javacallc");//加载动态链接库
    }

    /**
     * 让C代码做加法运算，把结果返回
     *
     * @param x
     * @param y
     * @return
     */
    public native int add(int x, int y);

    /**
     * 从java传入字符串，C代码进程拼接
     *
     * @param s I am from java
     * @return I am form java add I am from C
     */
    public native String sayHello(String s);

    /**
     * 让C代码给每个元素都加上10
     *
     * @param intArray
     * @return
     */
    public native int[] increaseArrayEles(int[] intArray);

    /*
     * 应用: 检查密码是否正确, 如果正确返回200, 否则返回400
     */
    public native int checkPwd(String pwd);
}
