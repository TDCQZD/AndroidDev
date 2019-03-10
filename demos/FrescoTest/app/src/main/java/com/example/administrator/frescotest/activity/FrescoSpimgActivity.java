package com.example.administrator.frescotest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
*
*@author ZD
*created at 2017/6/27 11:42
*description：带进度条的图片
*/

public class FrescoSpimgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_spimg);

        SimpleDraweeView sdvFrescoSpimg = (SimpleDraweeView) findViewById(R.id.sdv_fresco_spimg);

        // 设置样式
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());

        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();

        sdvFrescoSpimg.setHierarchy(hierarchy);

        // 加载图片的地址
        Uri uri = Uri.parse("http://pic.qiantucdn.com/58pic/18/74/91/5652b24db9faf_1024.jpg");

        // 加载图片
        sdvFrescoSpimg.setImageURI(uri);
    }
}
