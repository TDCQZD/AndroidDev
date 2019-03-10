package com.example.administrator.picassotest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.administrator.picassotest.R;
import com.example.administrator.picassotest.adapter.PicassoListviewAdapter;
/**
*
*@author ZD
*created at 2017/6/27 10:27
*description：在listview中加载图片
*/
public class PicassoListviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_listview);
        // 初始化listview
        PicassoListviewAdapter picassoListviewAdapter = new PicassoListviewAdapter(PicassoListviewActivity.this);
        ListView lvPicasso = (ListView) findViewById(R.id.lv_picasso);
        lvPicasso.setAdapter(picassoListviewAdapter);
    }
}
