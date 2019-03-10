package com.example.administrator.daggerdemo;

import dagger.Component;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/9/19 9:36
 */
@Component(modules = {UserMoudle.class})
public interface UserComponent {
    void inject(MainActivity activity);
}
