package com.example.administrator.listviewtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.listviewtest.R;
import com.example.administrator.listviewtest.bean.ItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 * 自定义类继承BaseAdapter，实现视图与数据的绑定
 */

public class MyBaseAdapter extends BaseAdapter {
    private List<ItemBean> list = null;

    private Context context = null;

    private LayoutInflater inflater = null;

    public MyBaseAdapter(List<ItemBean> list, Context context) {
        this.list = list;
        this.context = context;
        // 布局装载器对象
        inflater = LayoutInflater.from(context);
    }

    // 适配器中数据集中数据的个数

    @Override
    public int getCount() {
        return   list.size();//返回数组的长度
    }
    // 获取数据集中与指定索引对应的数据项
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    // 获取指定行对应的ID
    @Override
    public long getItemId(int i) {
        return i ;
    }
    // 获取每一个Item显示的内容
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        // 判断convertView的状态，来达到复用效果
        if (null == convertView) {
            // 如果convertView为空，则表示第一次显示该条目，需要创建一个view
           convertView = inflater.inflate(R.layout.baseadapter_listview_item, null);
            //新建一个viewholder对象
            holder = new ViewHolder();
            //将findviewbyID的结果赋值给holder对应的成员变量
            holder.imageView = (ImageView) convertView.findViewById(R.id.id_iv);//此处是将内容与控件绑定。
            holder.tvTitle = (TextView) convertView.findViewById(R.id.id_tvTitle);//注意：此处的findVIewById前要加convertView.
           holder.tvContent = (TextView) convertView.findViewById(R.id.id_tvContent);
            // 将holder与view进行绑定
            convertView.setTag(holder);// 通过setTag将ViewHolder和convertView绑定
        } else {
            // 否则表示可以复用convertView

            holder = (ViewHolder) convertView.getTag();// 获取，通过ViewHolder找到相应的控件
        }

        ItemBean itemBean = list.get(position);
        holder.imageView.setImageResource(itemBean.ItemImageResid);
        holder.tvTitle.setText(itemBean.ItemTitle);
        holder.tvContent.setText(itemBean.ItemContent);

        return convertView;

    }




    /*存放控件*/
    public final class ViewHolder {
        ImageView imageView;
        TextView tvTitle;
        TextView tvContent;

    }
}
