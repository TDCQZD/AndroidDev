package com.example.zhangdai.recyclerviewtest.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhangdai.recyclerviewtest.R;
import com.example.zhangdai.recyclerviewtest.bean.Items;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class RecyclerViewAdapter extends BaseQuickAdapter<Items, BaseViewHolder> {
    private Context mContext;

    public RecyclerViewAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<Items> data) {
        super(layoutResId, data);
        this.mContext=context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Items item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.addOnClickListener(R.id.iv_icon).addOnClickListener(R.id.tv_title);
        Glide.with(mContext).load(R.drawable.m2).into((ImageView) helper.getView(R.id.iv_icon));
    }
}
