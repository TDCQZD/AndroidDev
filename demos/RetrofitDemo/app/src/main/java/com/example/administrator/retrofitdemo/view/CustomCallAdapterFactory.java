package com.example.administrator.retrofitdemo.view;

import android.support.annotation.Nullable;

import com.example.administrator.retrofitdemo.Bean.CustomCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/17 17:02
 */

public class CustomCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 获取原始类型
        Class<?> rawType = getRawType(returnType);
        // 返回值必须是CustomCall并且带有泛型
        if (rawType == CustomCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new CustomCallAdapter(callReturnType);
        }
        return null;

    }
}
