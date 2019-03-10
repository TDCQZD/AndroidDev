package com.example.administrator.retrofitdemo.view;

import com.example.administrator.retrofitdemo.Bean.CustomCall;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/17 16:40
 */

public  class CustomCallAdapter implements CallAdapter<CustomCall<?>,CustomCall<?>>{

    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    CustomCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public CustomCall<?> adapt(Call<CustomCall<?>> call) {
        // 由 CustomCall 决定如何使用
        return new CustomCall<>(call);
    }
}
