package com.example.administrator.retrofitdemo.view;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/13 11:17
 */

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义Converter实现RequestBody到String的转换
 */
public  class StringConverter implements Converter<ResponseBody, String> {

    public static final StringConverter INSTANCE = new StringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
