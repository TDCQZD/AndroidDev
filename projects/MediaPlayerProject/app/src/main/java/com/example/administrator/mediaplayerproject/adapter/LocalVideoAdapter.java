package com.example.administrator.mediaplayerproject.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.mediaplayerproject.R;
import com.example.administrator.mediaplayerproject.bean.MediaItem;
import com.example.administrator.mediaplayerproject.utils.Utils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class LocalVideoAdapter extends BaseQuickAdapter<MediaItem, BaseViewHolder> {
    private Context mContext;
    private Utils utils;
    private final boolean isVideo;
    private final List<MediaItem> mediaItems;

    public LocalVideoAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<MediaItem> data, boolean isVideo) {
        super(layoutResId, data);
        this.mContext = context;
        this.mediaItems = data;
        utils = new Utils();
        this.isVideo = isVideo;
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaItem item) {

        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_size, Formatter.formatFileSize(mContext, item.getSize()))
                .setText(R.id.tv_time, utils.stringForTime((int) item.getDuration()));
        if (!isVideo) {
            //音频
            Glide.with(mContext).load(R.drawable.music_default_bg).into((ImageView) helper.getView(R.id.iv_icon));
        }
    }
}
