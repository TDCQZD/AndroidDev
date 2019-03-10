package com.example.administrator.listviewtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.listviewtest.R;
import com.example.administrator.listviewtest.adapter.CommonAdapter;
import com.example.administrator.listviewtest.adapter.ViewHolder;
import com.example.administrator.listviewtest.bean.TestBean;
import com.example.administrator.listviewtest.view.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
*一个满足ListView、RecyclerView以及其他View通用的侧滑删除
*@author ZD
*created at 2017/7/21 16:54
*description：
*/ 

public class DeleteItemActivity extends Activity{
    private  ListView lv;
    private List<TestBean> lists;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(R.layout.my_list);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("仿QQ侧滑删除");
        lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用
        lists = new ArrayList<>();
        for(int i=0;i<20;i++){
            TestBean bean = new TestBean();
            bean.setName("name："+i);
            bean.setImgRes(R.mipmap.ic_launcher);
            lists.add(bean);
        }

        lv.setAdapter(new CommonAdapter<TestBean>(this,lists, R.layout.item_layout) {

            @Override
            public void convert(final ViewHolder holder, TestBean testBean, final int position, View convertView) {
                holder.setText(R.id.listview_tv,testBean.getName());
                holder.setImageResource(R.id.listview_iv,testBean.getImgRes());
                //可以根据自己需求设置一些选项(这里设置了IOS阻塞效果以及item的依次左滑、右滑菜单)
                ((SwipeMenuLayout)holder.getConvertView()).setIos(true).setLeftSwipe(position % 2 == 0 ? true : false);
                //监听事件
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DeleteItemActivity.this,"点击了："+position,Toast.LENGTH_SHORT).show();
                    }
                });
                holder.setOnClickListener(R.id.btn_zd, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DeleteItemActivity.this,"点击了置顶选项",Toast.LENGTH_SHORT).show();
                        //在ListView里，点击侧滑菜单上的选项时，如果想让侧滑菜单同时关闭，调用这句话
                        ((SwipeMenuLayout) holder.getConvertView()).quickClose();
                    }
                });
                holder.setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DeleteItemActivity.this,"点击了删除选项",Toast.LENGTH_SHORT).show();
                        //在ListView里，点击侧滑菜单上的选项时，如果想让侧滑菜单同时关闭，调用这句话
                        ((SwipeMenuLayout) holder.getConvertView()).quickClose();
                        //删除操作
                        lists.remove(position);
                        notifyDataSetChanged();
                    }
                });
                //长按监听
                holder.setOnLongClickListener(R.id.ll_content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(DeleteItemActivity.this,"正在进行长按操作",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        });
    }
}
