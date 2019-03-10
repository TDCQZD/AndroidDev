package com.example.zhangdai.recyclerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangdai.recyclerviewtest.R;

import java.util.ArrayList;

/**
 * Created by zhangdai on 2017/3/21.
 * 作用：RecyclerView的适配器
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<String> datas;
    private OnItemClickListener onItemClickListener;

    /**
     * 构造器
     *
     * @param context
     * @param datas
     */
    public MyRecyclerViewAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    /**
     * 相当于于getView方法中创建View和ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recyclerview, null);
        return new ViewHolder(itemView);
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 相当于于getView绑定数据部分的代码
     * 数据和View绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //根据位置得到对应的数据
        String data = datas.get(position);
        holder.tv_title.setText(data);

    }

    /**
     * 添加数据
     *
     * @param position
     * @param data
     */
    public void addData(int position, String data) {
        datas.add(position, data);
        //刷新适配器
        notifyItemInserted(position);
    }

    /**
     * 移除数据
     *
     * @param position
     */
    public void removeData(int position) {
        datas.remove(position);
        //刷新适配器
        notifyItemRemoved(position);
    }

    /**
     * 设置RecyclerView条目监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 自定义接口
     * RecyclerView条目监听
     */
    public interface OnItemClickListener {

        /**
         * 当RecyclerView某个被点击的时候回调
         *
         * @param view 点击item的视图
         * @param data 点击得到的数据
         */
        public void onItemClick(View view, String data);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            /**
             * 条目监听
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Toast.makeText(context, "data=="+datas.get(getLayoutPosition()), Toast.LENGTH_SHORT).show();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, datas.get(getLayoutPosition()));
                    }
                }
            });
            /**
             * 控件监听
             */
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "我是图片==" + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
