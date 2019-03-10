package com.example.administrator.frescotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.frescotest.activity.FrescoActivity;
import com.example.administrator.frescotest.activity.FrescoAutoSizeActivity;
import com.example.administrator.frescotest.activity.FrescoCircleAndCornerActivity;
import com.example.administrator.frescotest.activity.FrescoCropActivity;
import com.example.administrator.frescotest.activity.FrescoGifAcitivity;
import com.example.administrator.frescotest.activity.FrescoJpegActivity;
import com.example.administrator.frescotest.activity.FrescoListenerActivity;
import com.example.administrator.frescotest.activity.FrescoModifyActivity;
import com.example.administrator.frescotest.activity.FrescoMultiActivity;
import com.example.administrator.frescotest.activity.FrescoResizeActivity;
import com.example.administrator.frescotest.activity.FrescoSpimgActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btFrescoUse;
    private Button btFrescoSpimg;
    private Button btFrescoCrop;
    private Button btFrescoCircleAndCorner;
    private Button btFrescoJpeg;
    private Button btFrescoGif;
    private Button btFrescoMulti;
    private Button btFrescoListener;
    private Button btFrescoResize;
    private Button btFrescoModifyImg;
    private Button btFrescoAutoSizeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }


    private void findViews() {
        btFrescoUse = (Button) findViewById(R.id.bt_fresco_use);
        btFrescoSpimg = (Button) findViewById(R.id.bt_fresco_spimg);
        btFrescoCrop = (Button) findViewById(R.id.bt_fresco_crop);
        btFrescoCircleAndCorner = (Button) findViewById(R.id.bt_fresco_circleAndCorner);
        btFrescoJpeg = (Button) findViewById(R.id.bt_fresco_jpeg);
        btFrescoGif = (Button) findViewById(R.id.bt_fresco_gif);
        btFrescoMulti = (Button) findViewById(R.id.bt_fresco_multi);
        btFrescoListener = (Button) findViewById(R.id.bt_fresco_listener);
        btFrescoResize = (Button) findViewById(R.id.bt_fresco_resize);
        btFrescoModifyImg = (Button) findViewById(R.id.bt_fresco_modifyImg);
        btFrescoAutoSizeImg = (Button) findViewById(R.id.bt_fresco_autoSizeImg);

        btFrescoUse.setOnClickListener(this);
        btFrescoSpimg.setOnClickListener(this);
        btFrescoCrop.setOnClickListener(this);
        btFrescoCircleAndCorner.setOnClickListener(this);
        btFrescoJpeg.setOnClickListener(this);
        btFrescoGif.setOnClickListener(this);
        btFrescoMulti.setOnClickListener(this);
        btFrescoListener.setOnClickListener(this);
        btFrescoResize.setOnClickListener(this);
        btFrescoModifyImg.setOnClickListener(this);
        btFrescoAutoSizeImg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btFrescoUse) { // 基本使用
            Intent intent = new Intent(MainActivity.this, FrescoActivity.class);
            startActivity(intent);
        } else if (v == btFrescoSpimg) { // 带进度条的图片
            Intent intent = new Intent(MainActivity.this, FrescoSpimgActivity.class);
            startActivity(intent);
        } else if (v == btFrescoCrop) {// 图片的不同裁剪
            Intent intent = new Intent(MainActivity.this, FrescoCropActivity.class);
            startActivity(intent);
        } else if (v == btFrescoCircleAndCorner) {// 圆形和圆角图片
            Intent intent = new Intent(MainActivity.this, FrescoCircleAndCornerActivity.class);
            startActivity(intent);
        } else if (v == btFrescoJpeg) {
            // 渐进式展示图片
            Intent intent = new Intent(MainActivity.this, FrescoJpegActivity.class);
            startActivity(intent);
        } else if (v == btFrescoGif) {
            // GIF动画图片
            Intent intent = new Intent(MainActivity.this, FrescoGifAcitivity.class);
            startActivity(intent);
        } else if (v == btFrescoMulti) {  // 多图请求及图片复用
            Intent intent = new Intent(MainActivity.this, FrescoMultiActivity.class);
            startActivity(intent);
        } else if (v == btFrescoListener) { // 图片加载监听
            Intent intent = new Intent(MainActivity.this, FrescoListenerActivity.class);
            startActivity(intent);
        } else if (v == btFrescoResize) {  // 图片修改和旋转
            Intent intent = new Intent(MainActivity.this, FrescoResizeActivity.class);
            startActivity(intent);
        } else if (v == btFrescoModifyImg) {// 修改图片
            Intent intent = new Intent(MainActivity.this, FrescoModifyActivity.class);
            startActivity(intent);
        } else if (v == btFrescoAutoSizeImg) {  // 动态展示图片
            Intent intent = new Intent(MainActivity.this, FrescoAutoSizeActivity.class);
            startActivity(intent);
        }
    }

}
