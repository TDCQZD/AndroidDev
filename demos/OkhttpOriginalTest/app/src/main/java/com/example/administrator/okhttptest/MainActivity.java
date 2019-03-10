package com.example.administrator.okhttptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.okhttptest.activity.OkGoActivity;
import com.example.administrator.okhttptest.activity.OkhttpActivity;
import com.example.administrator.okhttptest.activity.OkhttputilsActivity;

/**
 * Okhttp使用
 *
 * @author ZD
 *         created at 2017/7/27 17:11
 *         description：
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("Okhttp学习使用");
    }

    /**
     * 官网示例
     *
     * @param view
     */
    public void basicUse(View view) {
        startActivity(new Intent(MainActivity.this, OkhttpActivity.class));
    }

    /**
     * 二次封装库OKGOokhttputils
     * 张鸿样封装
     *
     * @param view
     */
    public void okhttputils(View view) {
        startActivity(new Intent(MainActivity.this, OkhttputilsActivity.class));

    }

    /**
     * 二次封装库OKGO
     *
     * @param view
     */
    public void okgo(View view) {
        startActivity(new Intent(MainActivity.this, OkGoActivity.class));

    }

    /**
     * @param view
     */
    public void library(View view) {
//        startActivity(new Intent(MainActivity.this,LibraryActivity.class));

    }

}
