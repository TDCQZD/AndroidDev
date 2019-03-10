package com.example.administrator.androiduniversalimageloadertest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androiduniversalimageloadertest.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivity extends AppCompatActivity {
    private ImageLoader imageLoader;
    private ImageView iv;
    private String imageUri = Constants.IMAGES[5];
    private String imageUri1 = Constants.IMAGES[10];
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉原标题
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("AndroidUniversalImageloader");
        imageLoader = ImageLoader.getInstance();
    }

    public void listview(View view) {
        startActivity(new Intent(MainActivity.this, ImageloaderListviewActivity.class));
    }

    public void viewpager(View view) {
        startActivity(new Intent(MainActivity.this, ImageloaderViewpagerActivity.class));
    }

    public void simple(View view) {
        imageLoader.displayImage(imageUri, iv);
        imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Toast.makeText(MainActivity.this, "图片加载完成", Toast.LENGTH_SHORT).show();
            }
        });
        Bitmap bmp = imageLoader.loadImageSync(imageUri);
    }

    public void complete(View view) {

        imageLoader.displayImage(imageUri1, iv, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {

            }
        });
        ImageSize targetSize = new ImageSize(80, 50); // result Bitmap will be fit to this size
        imageLoader.loadImage(imageUri, targetSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
            }
        });

//        Bitmap bmp1 = imageLoader.loadImageSync(imageUri, targetSize, options);
    }
}
