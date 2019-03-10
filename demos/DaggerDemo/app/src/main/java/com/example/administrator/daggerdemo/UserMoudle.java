package com.example.administrator.daggerdemo;

import dagger.Module;
import dagger.Provides;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/9/19 9:32
 */
@Module
public class UserMoudle {

//    @Provides
//    public ApiService getApiService() {
//        return new ApiService();
//    }

    @Provides
    public ApiService getManaerApiService(ApiService apiService) {
        return apiService;
    }
}
