package com.example.administrator.viewpagertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.viewpagertest.activity.AdRotationActivity;
import com.example.administrator.viewpagertest.activity.GuideActivity;
import com.example.administrator.viewpagertest.activity.ViewPagerActivity;
/**
*@author ZD
*created at 2017/6/23 14:51
*description：ViewPager学习使用，实现广告页，引导页
 * 参考案例：https://github.com/PingerOne/ViewPagerDemo
*/
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Use(View view) {
        startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
    }

    public void AdRotation(View view) {
        startActivity(new Intent(MainActivity.this, AdRotationActivity.class));
    }

    public void Guide(View view) {
        startActivity(new Intent(MainActivity.this, GuideActivity.class));
    }



}
