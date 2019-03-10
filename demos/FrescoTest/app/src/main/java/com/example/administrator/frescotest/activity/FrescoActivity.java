package com.example.administrator.frescotest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author ZD
 *         created at 2017/6/27 11:26
 *         description： Fresco基本使用
 */

public class FrescoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        Uri uri = Uri.parse("http://pic.qiantucdn.com/58pic/22/76/26/57d0ef9f30729_1024.jpg");
        /*
        Drawees 负责图片的呈现
         */
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        draweeView.setImageURI(uri);
    }
}
