package com.example.administrator.retrofitdemo.Bean;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/13 10:30
 */

public class Result<T> {
    public int code;
    public String msg;
    public T data;
    public long count;
    public long page;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", count=" + count +
                ", page=" + page +
                '}';
    }
}
