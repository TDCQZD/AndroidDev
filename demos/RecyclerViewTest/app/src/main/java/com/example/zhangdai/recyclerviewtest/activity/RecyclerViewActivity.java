package com.example.zhangdai.recyclerviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangdai.recyclerviewtest.R;
import com.example.zhangdai.recyclerviewtest.adapter.MyRecyclerViewAdapter;
import com.example.zhangdai.recyclerviewtest.utils.DividerListItemDecoration;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_add;
    private Button btn_delete;
    private Button btn_list;
    private Button btn_grid;
    private Button btn_flow;
    private RecyclerView recyclerview;
    private TextView tv_title;

    private ArrayList<String> datas;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();

        initData();

        //设置RecyclerView的适配器
        adapter = new MyRecyclerViewAdapter(RecyclerViewActivity.this, datas);
        recyclerview.setAdapter(adapter);

        //LayoutManager
        recyclerview.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false));
//        recyclerview.scrollToPosition(datas.size()-1);

        /**
         * 添加RecyclerView的分割线,可自定义
         * 在styles添加自定义分割线标签
         */

        recyclerview.addItemDecoration(new DividerListItemDecoration(RecyclerViewActivity.this,DividerListItemDecoration.VERTICAL_LIST));

        //设置点击某条的监听
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(RecyclerViewActivity.this, "data==" + data, Toast.LENGTH_SHORT).show();
            }
        });


        //设置动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

    }

    private void initData() {
        datas = new ArrayList<>();
        //准备数据集合
        for (int i = 0; i < 100; i++) {
            datas.add("Content_" + i);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_recyclerview);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_flow = (Button) findViewById(R.id.btn_flow);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_grid.setOnClickListener(this);
        btn_flow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                adapter.addData(0, "New_Content");
                recyclerview.scrollToPosition(0);
                break;
            case R.id.btn_delete:
                adapter.removeData(0);
                break;
            case R.id.btn_list:
                //设置List类型效果
                recyclerview.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.btn_grid:
                //设置Grid类型效果
                recyclerview.setLayoutManager(new GridLayoutManager(RecyclerViewActivity.this, 2, GridLayoutManager.VERTICAL, false));
//                recyclerview.scrollToPosition(99);
                break;
            case R.id.btn_flow:
                //设置瀑布流类型效果
                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }
}
