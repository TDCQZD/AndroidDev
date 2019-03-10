package com.example.zhangdai.recyclerviewtest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhangdai.recyclerviewtest.R;
import com.example.zhangdai.recyclerviewtest.adapter.RecyclerViewAdapter;
import com.example.zhangdai.recyclerviewtest.bean.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BaseRecyclerViewAdapterHelper基本用法
 *
 * @author ZD
 *         created at 2017/8/14 15:11
 *         description：
 */

public class RecyclerViewHelperActivity extends AppCompatActivity {
    private RecyclerView rv;
    private SwipeRefreshLayout sr;
    private ArrayList<String> datas;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_base);
        sr = (SwipeRefreshLayout) findViewById(R.id.sr);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
//        rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, R.layout.item_recyclerview, genData());
        rv.setAdapter(mRecyclerViewAdapter);
        //设置条目的点击监听
        mRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerViewHelperActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //设置条目的长点击监听
        mRecyclerViewAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerViewHelperActivity.this, "删除了" + position, Toast.LENGTH_SHORT).show();
                mRecyclerViewAdapter.remove(position);
                return false;
            }
        });
        //设置子控件监听
        mRecyclerViewAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
               switch (view.getId()) {
                   case  R.id.iv_icon:
               Toast.makeText(RecyclerViewHelperActivity.this, "点击了"+ position+"子控件的图片", Toast.LENGTH_SHORT).show();
                       break;
                   case  R.id.tv_title:
                       Toast.makeText(RecyclerViewHelperActivity.this, "点击了"+ position+"子控件的标题", Toast.LENGTH_SHORT).show();
                       break;
               }
            }
        });
        //设置适配器的加载动画
        mRecyclerViewAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);



//        sr.setRefreshing(true);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        sr.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        sr.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { // 开始刷新，设置当前为刷新状态
                sr.setRefreshing(true);
                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行

                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        String name = "我是天才" + random.nextInt(100) + "号";
//                        Items items = new Items();
//                        items.setTitle(name);
//                        genData().add(1, items);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        Toast.makeText(RecyclerViewHelperActivity.this, "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        sr.setRefreshing(false);
                    }
                }, 1200);
                // System.out.println(Thread.currentThread().getName());
                // 这个不能写在外边，不然会直接收起来
                sr.setRefreshing(false);
            }
        });


    }


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

}
