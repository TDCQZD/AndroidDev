package com.example.administrator.listviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.listviewtest.activity.ArrayAdapterActivity;
import com.example.administrator.listviewtest.activity.BaseAdapterActivity;
import com.example.administrator.listviewtest.activity.DeleteItemActivity;
import com.example.administrator.listviewtest.activity.ExpandableListviewActivity;
import com.example.administrator.listviewtest.activity.LinkageActivity;
import com.example.administrator.listviewtest.activity.SimpleAdapterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btArrayAdapter;
    private Button btSimpleAdapter;
    private Button btBaseAdapter;
    private Button btAdapter;
    private Button btDeleteItem;
    private Button btLinkage;
    private Button btListviewZoom;
    private Button btListviewRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("ListView使用整理");
        findViews();
    }


    private void findViews() {
        btArrayAdapter = (Button) findViewById(R.id.bt_arrayAdapter);
        btSimpleAdapter = (Button) findViewById(R.id.bt_simpleAdapter);
        btBaseAdapter = (Button) findViewById(R.id.bt_baseAdapter);
        btAdapter = (Button) findViewById(R.id.bt_adapter);
        btDeleteItem = (Button) findViewById(R.id.bt_delete_item);
        btLinkage = (Button) findViewById(R.id.bt_linkage);
        btListviewZoom = (Button) findViewById(R.id.bt_listview_zoom);
        btListviewRefresh = (Button) findViewById(R.id.bt_listview_refresh);

        btArrayAdapter.setOnClickListener(this);
        btSimpleAdapter.setOnClickListener(this);
        btBaseAdapter.setOnClickListener(this);
        btAdapter.setOnClickListener(this);
        btDeleteItem.setOnClickListener(this);
        btLinkage.setOnClickListener(this);
        btListviewZoom.setOnClickListener(this);
        btListviewRefresh.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btArrayAdapter) {
            // ArrayAdapter
            startActivity(new Intent(MainActivity.this, ArrayAdapterActivity.class));
        } else if (v == btSimpleAdapter) {
            // SimpleAdapter
            startActivity(new Intent(MainActivity.this, SimpleAdapterActivity.class));
        } else if (v == btBaseAdapter) {
            // BaseAdapter
            startActivity(new Intent(MainActivity.this, BaseAdapterActivity.class));
        } else if (v == btAdapter) {
            // 万能适配器
        } else if (v == btDeleteItem) {
            // 侧滑删除Item
            startActivity(new Intent(MainActivity.this, DeleteItemActivity.class));
//            startActivity(new Intent(MainActivity.this,SwipeMenuListViewActivity.class));
        } else if (v == btLinkage) {
            // ListView联动
            startActivity(new Intent(MainActivity.this, LinkageActivity.class));
        } else if (v == btListviewZoom) {
            // 可缩放的ListView
        } else if (v == btListviewRefresh) {
            // ListView刷新
        }
    }

    public void ExpandableListview(View view) {
        startActivity(new Intent(MainActivity.this, ExpandableListviewActivity.class));
    }

}
