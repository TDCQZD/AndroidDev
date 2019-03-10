package com.example.administrator.listviewtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.listviewtest.R;
import com.example.administrator.listviewtest.adapter.MyBaseAdapter;
import com.example.administrator.listviewtest.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 * 通用的基础适配器
 */

public class BaseAdapterActivity extends Activity {
    private ListView lv;
    private MyBaseAdapter myBaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(R.layout.my_list);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("BaseAdapter通用的基础适配器");
        List<ItemBean> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(new ItemBean(R.mipmap.ic_launcher, "Title" + i, "Content" + i));
        }

        myBaseAdapter = new MyBaseAdapter(list, this);

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(myBaseAdapter);

    }
}