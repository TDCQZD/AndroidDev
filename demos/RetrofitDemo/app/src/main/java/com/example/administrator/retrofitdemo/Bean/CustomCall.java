package com.example.administrator.retrofitdemo.Bean;

import java.io.IOException;

import retrofit2.Call;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/17 16:40
 */

public class  CustomCall<R> {
    public final Call<R> call;

    public CustomCall(Call<R> call) {
        this.call = call;
    }

    public R get() throws IOException {
        return call.execute().body();
    }


}
