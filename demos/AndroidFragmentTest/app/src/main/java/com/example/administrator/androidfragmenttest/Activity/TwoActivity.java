package com.example.administrator.androidfragmenttest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidfragmenttest.R;

public class TwoActivity extends AppCompatActivity {
    private TextView tv;
    private String data;
    public final static int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        tv = (TextView) findViewById(R.id.tv_show);
        Intent intent = getIntent();
        //bundle 获取数据
        Bundle bundle = intent.getExtras();
        String getdata = bundle.getString("extra_data");
        Log.i("TAG", "获取数据--->" + getdata);
        //intent 直接获取数据
        data = intent.getStringExtra("extra_data");

    }

    public void twointentActivity(View view) {//onClick要使用public
//        Intent intent = new Intent(TwoActivity.this, OneActivity.class);
        Intent intent = new Intent();
        intent.putExtra("resultdata", "返回数据给上一个Activity");
        setResult(RESULT_CODE, intent);
        finish();

    }

    public void twointentFragment(View view) {
        tv.setText(data);

    }

    /**
     * 返回键返回
     * 问题：上一个Activity无法接受到数据
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.putExtra("resultdata", "返回数据给上一个Activity");
        setResult(RESULT_CODE, intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
