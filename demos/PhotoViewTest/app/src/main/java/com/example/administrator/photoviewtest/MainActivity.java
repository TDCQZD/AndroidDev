package com.example.administrator.photoviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {
    private PhotoView photoView;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoView = (PhotoView) findViewById(R.id.photoview);
//        mAttacher = new PhotoViewAttacher(photoView);
        initData();
    }

    private void initData() {
        photoView.setImageResource(R.mipmap.mei1);
//        mAttacher = new PhotoViewAttacher(photoView);
//        mAttacher.update();
        
    }
}
