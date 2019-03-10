package com.example.zhangdai.recyclerviewtest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zhangdai.recyclerviewtest.R;
import com.example.zhangdai.recyclerviewtest.adapter.RecyclerViewAdapter;
import com.example.zhangdai.recyclerviewtest.bean.Items;
import com.example.zhangdai.recyclerviewtest.loadmore.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static com.example.zhangdai.recyclerviewtest.R.id.rv;

/**
 * RecyclerView 刷新加载
 */

public class PullToRefreshActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    private int delayMillis = 1000;

    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_refresh);
        mRecyclerView = (RecyclerView) findViewById(rv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        initAdapter();
        addHeadView();
        addFootView();

    }

    /*
      添加尾布局
       */
    private void addFootView() {
        View footView = getLayoutInflater().inflate(R.layout.footer_view, (ViewGroup) mRecyclerView.getParent(), false);
        footView.findViewById(R.id.iv).setVisibility(View.GONE);

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadMoreEndGone = true;
                mRecyclerViewAdapter.setLoadMoreView(new CustomLoadMoreView());
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                Toast.makeText(PullToRefreshActivity.this, "change complete", Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerViewAdapter.addFooterView(footView);
    }

    /*
    添加头布局
     */
    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        ((TextView) headView.findViewById(R.id.tv)).setText("change load view");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadMoreEndGone = true;
                mRecyclerViewAdapter.setLoadMoreView(new CustomLoadMoreView());
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                Toast.makeText(PullToRefreshActivity.this, "change complete", Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerViewAdapter.addHeaderView(headView);
    }

    //初始化适配器
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, R.layout.item_recyclerview, genData());

        mRecyclerViewAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerViewAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mCurrentCounter = mRecyclerViewAdapter.getData().size();
        //RecyclerView条目监听
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(PullToRefreshActivity.this, "点击的Item的ID是：" + Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    //加载数据
    private List<Items> genData() {
        ArrayList<Items> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String name = "Content_" + i;
            Items items = new Items();
            items.setTitle(name);
            list.add(items);
        }
        return list;
    }

    /*
    SwipeRefreshLayout监听
     */
    @Override
    public void onRefresh() {
        mRecyclerViewAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerViewAdapter.setNewData(genData());
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                mRecyclerViewAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    private static final int TOTAL_COUNTER = 18;

    /*
    加载更多监听
     */
    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (mRecyclerViewAdapter.getData().size() < PAGE_SIZE) {
            mRecyclerViewAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                mRecyclerViewAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
            } else {
                if (isErr) {
                    mRecyclerViewAdapter.addData(genData());
                    mCurrentCounter = mRecyclerViewAdapter.getData().size();
                    mRecyclerViewAdapter.loadMoreComplete();
                } else {
                    isErr = true;
                    Toast.makeText(this, "加载出错", Toast.LENGTH_LONG).show();
                    mRecyclerViewAdapter.loadMoreFail();

                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }
}
