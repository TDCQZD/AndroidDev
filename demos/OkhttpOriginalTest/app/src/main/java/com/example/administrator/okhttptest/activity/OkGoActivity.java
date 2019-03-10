package com.example.administrator.okhttptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.okhttptest.R;

/**
 * 二次封装库OKGOokhttputils
 *
 * @author ZD
 *         created at 2017/7/27 17:30
 *         description：
 */
public class OkGoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_processing);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("二次封装库OKGO");
    }
}
