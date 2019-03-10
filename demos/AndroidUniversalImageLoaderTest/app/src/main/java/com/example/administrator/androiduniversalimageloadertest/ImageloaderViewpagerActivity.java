package com.example.administrator.androiduniversalimageloadertest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.androiduniversalimageloadertest.adapter.ImageloaderViewpagerAdapter;

public class ImageloaderViewpagerActivity extends AppCompatActivity {
    private ViewPager vpImageloaderViewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉原标题
        setContentView(R.layout.activity_imageloader_viewpager);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("Imageloader应用在Viewpager中");
        vpImageloaderViewpager = (ViewPager) findViewById(R.id.vp_imageloader_viewpager);
        initData();
    }
    private void initData() {   // 标题

        // 初始化viewpager
        ImageloaderViewpagerAdapter imageloaderViewpagerAdapter = new ImageloaderViewpagerAdapter(this);

        vpImageloaderViewpager.setAdapter(imageloaderViewpagerAdapter);

        // 显示第一个条目
        vpImageloaderViewpager.setCurrentItem(1);
    }
}
