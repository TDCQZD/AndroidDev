package com.example.zhangdai.recyclerviewtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhangdai.recyclerviewtest.R;

/**
 * BaseRecyclerViewAdapterHelper使用
 *
 * @author ZD
 *         created at 2017/8/14 15:11
 *         description：
 */

public class BaseRecyclerViewAdapterHelperActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_helper);
    }

    public void BaseHelper(View v) {
       startActivity(new Intent(BaseRecyclerViewAdapterHelperActivity.this,RecyclerViewHelperActivity.class));
    }
    public void MultipleItem(View v) {
        startActivity(new Intent(BaseRecyclerViewAdapterHelperActivity.this,MultipleItemActivity.class));
    }
    public void  refresh(View v) {
        startActivity(new Intent(BaseRecyclerViewAdapterHelperActivity.this,PullToRefreshActivity.class));
    }

}
