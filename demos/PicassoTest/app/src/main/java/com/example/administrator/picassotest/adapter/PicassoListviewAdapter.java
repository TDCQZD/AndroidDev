package com.example.administrator.picassotest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.picassotest.R;
import com.example.administrator.picassotest.view.Constants;
import com.squareup.picasso.Picasso;

/**
*
*@author ZD
*created at 2017/6/27 10:33
*description：Listview适配器
*/

public class PicassoListviewAdapter extends BaseAdapter {
    private Context context;

    public PicassoListviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Constants.IMAGES.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_picasso_listview, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_picasso_item);
            holder.name = (TextView) convertView.findViewById(R.id.tv_picasso_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 名称
        holder.name.setText("item" + (position + 1));
        // 加载图片
        Picasso.with(context)
                .load(Constants.IMAGES[position])
                .placeholder(R.mipmap.ic_launcher)//设置资源加载过程中的显示的Drawable
                .error(R.mipmap.ic_launcher)//设置load失败时显示的Drawable。
                .into(holder.iv);// 设置资源加载到的目标 包括ImageView Target等

        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView name;
    }
}
