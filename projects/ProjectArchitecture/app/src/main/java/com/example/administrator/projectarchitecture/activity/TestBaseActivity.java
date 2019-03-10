package com.example.administrator.projectarchitecture.activity;

import android.content.Intent;
import android.view.View;

import com.example.administrator.projectarchitecture.MainActivity;
import com.example.administrator.projectarchitecture.R;
import com.example.administrator.projectarchitecture.base.BaseActivity;

/**
 * @Description:测试Activity基类封装
 * @author: Administrator
 * @Date: 2017/9/6 10:00
 */

public class TestBaseActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_title;
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setToolbar() {
        getToolbar().setTitle("封装Activity测试");
    }


    public void intentActivity(View view) {
//        this.openActivity(MainActivity.class);
        Intent intent=new Intent(this,MainActivity.class);
        this.openActivityByIntent(intent);
    }

    public void toast(View view) {
        showToastMsgLong("长时间显示");
        showToastMsgShort("短暂显示");
    }


}
