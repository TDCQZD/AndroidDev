package com.example.administrator.bitmaptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView iv1;
    private Bitmap bitmap = null, defaultBitmapMap = null;
    private BitmapFactory.Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("Bitmap加载图片");
        iv1 = (ImageView) findViewById(R.id.iv1);


    }


    /*
    从文件系统加载
     */
    public void DecodeFile(View view) {

        iv1.setImageResource(R.mipmap.ic_launcher);
        String path = "/sdcard/Download/mei3.jpg";
        bitmap = BitmapFactory.decodeFile(path);//普通加载
        Log.i("TAG", "Bitmap--->" + bitmap);
        iv1.setImageBitmap(bitmap);
    }

    /*
        从本地资源中加载
         */
    public void DecodeResource(View view) {
        iv1.setImageResource(R.mipmap.ic_launcher);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mei);//普通加载
        iv1.setImageBitmap(bitmap);
    }

    // 计算合适的采样率，height、width为期望的图片大小，单位是px
    private int calculateSampleSize(BitmapFactory.Options options, int width, int height) {

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }
        options.inSampleSize = inSampleSize;
        return inSampleSize;
    }


    /*
     从输入流加载
      */
    public void DecodeStream(View view) {
        iv1.setImageResource(R.mipmap.ic_launcher);
        InputStream in = null;
        try {
            in = this.getAssets().open("mei1.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap = BitmapFactory.decodeStream(in);//普通加载
        Log.i("TAG", "Bitmap--->" + bitmap);
        iv1.setImageBitmap(bitmap);
    }

    /*
    从字节数组中加载
     */
    public void DecodeByteArray(View view) {
        iv1.setImageResource(R.mipmap.ic_launcher);
        String path = "/sdcard/Download/m5.jpg";
        File file = new File(path);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);

            bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);//普通加载
            Log.i("TAG", "Bitmap--->" + bitmap);
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iv1.setImageBitmap(bitmap);
    }

    /*
    优化加载
         */
    public void Optimization(View view) {
        iv1.setImageResource(R.mipmap.ic_launcher);
           /*
        Bitmap优化加载
        1、使用BitmapFactory.Options。普通加载使用BitmapFactory
        2、将BitmapFacpry.Options的inJustDecodeBounds参数设为true，并执行decodeXXX方法加载图片的原始宽高信息
     （当inJustDecodeBounds为true时，执行decodeXXX方法时，BitmapFactory只会解析图片的原始宽高信息，并不会真正的加载图片）
        3、使用采样率Options.inSampleSize处理图片
        4、 将BitmapFacpry.Options的inSampleSize参数设为false并重新执行decodeXXX方法加载图片,同时对OutOfMemory异常处理。
        5、在onDestroy()、onStop()中回收Bitmap资源
         */
        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.mei, options);
        calculateSampleSize(options, 300, 300);
        options.inJustDecodeBounds = false;

        try {//捕获OutOfMemory异常
            // 实例化Bitmap
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mei, options);
        } catch (OutOfMemoryError e) {

        }
        if (bitmap == null) {
//            return defaultBitmapMap; // 如果实例化失败 返回默认的Bitmap对象
        }


        iv1.setImageBitmap(bitmap);
    }

    /*
    Bitmap图片使用
     */
    public void BitmapUse(View view) {
        startActivity(new Intent(MainActivity.this, BitmapUseActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {

            bitmap.recycle();  //回收图片所占的内存
            bitmap = null;
        }
        System.gc();//提醒系统及时回收
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {

            bitmap.recycle();  //回收图片所占的内存
            bitmap = null;
        }
        System.gc();//提醒系统及时回收
    }
}
