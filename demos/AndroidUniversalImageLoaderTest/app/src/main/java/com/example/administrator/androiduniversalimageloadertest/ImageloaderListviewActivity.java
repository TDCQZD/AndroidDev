package com.example.administrator.androiduniversalimageloadertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androiduniversalimageloadertest.adapter.ImageloaderListviewAdapter;

public class ImageloaderListviewActivity extends AppCompatActivity {
private ListView lvImageloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉原标题
        setContentView(R.layout.activity_imageloader_listview);
        TextView tvTitle= (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("Imageloader应用在ListView中");
        lvImageloader= (ListView) findViewById(R.id.lv_imageloader);
        initData();
    }

    private void initData() {
        ImageloaderListviewAdapter imageloaderListviewAdapter = new ImageloaderListviewAdapter(this);

        lvImageloader.setAdapter(imageloaderListviewAdapter);
    }
}
