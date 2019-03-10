package com.example.zhangdai.recyclerviewtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhangdai.recyclerviewtest.R;
import com.example.zhangdai.recyclerviewtest.adapter.MultipleItemQuickAdapter;
import com.example.zhangdai.recyclerviewtest.bean.MultipleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 复杂布局
 */

public class MultipleItemActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MultipleItemQuickAdapter multipleItemQuickAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multipe_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        final List<MultipleItem> data = getMultipleItemData();
        multipleItemQuickAdapter = new MultipleItemQuickAdapter(data);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
        //解决GridLayoutManager复用item问题
        multipleItemQuickAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        multipleItemQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MultipleItemActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        multipleItemQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv:
                        Toast.makeText(MultipleItemActivity.this, "点击了" + position + "子控件的图片", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.iv:
                        Toast.makeText(MultipleItemActivity.this, "点击了" + position + "子控件的文本", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(multipleItemQuickAdapter);

    }

    public static List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
            list.add(new MultipleItem(MultipleItem.TEXT, MultipleItem.TEXT_SPAN_SIZE, "多布局"));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
        }

        return list;
    }
}
