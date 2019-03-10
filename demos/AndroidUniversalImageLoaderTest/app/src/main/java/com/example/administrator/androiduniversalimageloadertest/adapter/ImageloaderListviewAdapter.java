package com.example.administrator.androiduniversalimageloadertest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.androiduniversalimageloadertest.R;
import com.example.administrator.androiduniversalimageloadertest.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Administrator on 2017/7/24.
 */

public class ImageloaderListviewAdapter extends BaseAdapter {
    private Context mContext;
    private ImageLoader imageLoader;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
            .build();
    public ImageloaderListviewAdapter(Context context) {
        this.mContext = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return Constants.IMAGES.length;
    }

    @Override
    public Object getItem(int position) {
        return  Constants.IMAGES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // 判断convertView的状态，来达到复用效果
        if (null == convertView) {
            // 如果convertView为空，则表示第一次显示该条目，需要创建一个view
            convertView = View.inflate(mContext, R.layout.item_imageloader_listview, null);
            //新建一个viewholder对象
            holder = new ViewHolder();
            //将findviewbyID的结果赋值给holder对应的成员变量
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_imageloader_listview);//此处是将内容与控件绑定。

            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_imageloader_name);
            // 将holder与view进行绑定
            convertView.setTag(holder);// 通过setTag将ViewHolder和convertView绑定
        } else {
            // 否则表示可以复用convertView
            holder = (ViewHolder) convertView.getTag();// 获取，通过ViewHolder找到相应的控件
        }
        // 显示数据
        holder.tvContent.setText("item"+(position + 1));
        imageLoader.displayImage(Constants.IMAGES[position],holder.imageView,options);
        return convertView;
    }
    /*存放控件*/
    public final class ViewHolder {
        ImageView imageView;
        TextView tvContent;

    }
}
