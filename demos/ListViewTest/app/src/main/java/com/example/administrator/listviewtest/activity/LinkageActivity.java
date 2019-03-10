package com.example.administrator.listviewtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.listviewtest.R;


/**
 *listview联动
 * 使用数据库的四级联动参考导入库FourLevelLinkageapp
 * 直接写入死数据参考导入库ConnectListViewapp
 */

public class LinkageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage);

    }
}