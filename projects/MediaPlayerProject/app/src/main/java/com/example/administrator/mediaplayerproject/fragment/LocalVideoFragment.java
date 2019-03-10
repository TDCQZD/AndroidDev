package com.example.administrator.mediaplayerproject.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.mediaplayerproject.R;
import com.example.administrator.mediaplayerproject.activity.SystemVideoPlayer;
import com.example.administrator.mediaplayerproject.adapter.LocalVideoAdapter;
import com.example.administrator.mediaplayerproject.base.BaseFragment;
import com.example.administrator.mediaplayerproject.bean.MediaItem;
import com.example.administrator.mediaplayerproject.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/10.
 */

public class LocalVideoFragment extends BaseFragment {
    private static final String TAG = LocalVideoFragment.class.getSimpleName();//LocalVideoFragment
    private RecyclerView rv;
    private TextView tv_nomedia;
    private ProgressBar pb_loading;
    /**
     * 装数据集合
     */
    private ArrayList<MediaItem> mediaItems;

    private LocalVideoAdapter mLocalVideoAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("TAG", "handler-->" + mediaItems.size());
            if (mediaItems != null && mediaItems.size() > 0) {
                //有数据
                //设置适配器
                mLocalVideoAdapter = new LocalVideoAdapter(mContext, R.layout.item_video_pager, mediaItems, true);
                rv.setAdapter(mLocalVideoAdapter);
                recyclerViewlistener();
                //把文本隐藏
                tv_nomedia.setVisibility(View.GONE);
            } else {
                //没有数据
                //文本显示
                tv_nomedia.setVisibility(View.VISIBLE);
            }


            //ProgressBar隐藏
            pb_loading.setVisibility(View.GONE);
        }
    };


    @Override
    protected View initView() {
        LogUtil.e("初始化本地视频布局");
        View view = View.inflate(mContext, R.layout.video_pager, null);
        rv = (RecyclerView) view.findViewById(R.id.video_rv);
        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtil.e("初始化本地视频的数据");
        //6.0权限处理
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getDataFromLocal();
        }

    }

    private void recyclerViewlistener() {
        mLocalVideoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MediaItem mediaItem = mediaItems.get(position);
//                Toast.makeText(mContext, "mediaItem==" + mediaItem.toString(), Toast.LENGTH_SHORT).show();

                //1.调起系统所有的播放-隐式意图
//                Intent intent = new Intent();
//                intent.setDataAndType(Uri.parse(mediaItem.getData()), "video/*");
//                mContext.startActivity(intent);

                //2.调用自己写的播放器-显示意图--一个播放地址
                Intent intent = new Intent(mContext, SystemVideoPlayer.class);
                intent.setDataAndType(Uri.parse(mediaItem.getData()), "video/*");
                mContext.startActivity(intent);
                //3.传递列表数据-对象-序列化
//                Intent intent = new Intent(mContext,SystemVideoPlayer.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("videolist",mediaItems);
//                intent.putExtras(bundle);
//                intent.putExtra("position",position);
//                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 从本地的sdcard得到数据
     */
    private void getDataFromLocal() {
        new Thread() {
            public void run() {
                getData();
            }
        }.start();

    }

    private void getData() {
        mediaItems = new ArrayList<>();
        ContentResolver mContentResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;//获取本地视频Uri
        String[] objs = new String[]{
                MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard的名称
                MediaStore.Video.Media.DURATION,//视频总时长
                MediaStore.Video.Media.SIZE,//视频的文件大小
                MediaStore.Video.Media.DATA,//视频的绝对地址
                MediaStore.Video.Media.ARTIST,//歌曲的演唱者
        };
        Cursor mCursor = mContentResolver.query(uri, objs, null, null, null);
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                MediaItem mediaItem = new MediaItem();

                mediaItems.add(mediaItem);//写在上面

                String name = mCursor.getString(0);//视频的名称
                mediaItem.setName(name);

                long duration = mCursor.getLong(1);//视频的时长
                mediaItem.setDuration(duration);

                long size = mCursor.getLong(2);//视频的文件大小
                mediaItem.setSize(size);

                String data = mCursor.getString(3);//视频的播放地址
                mediaItem.setData(data);

                String artist = mCursor.getString(4);//艺术家
                mediaItem.setArtist(artist);


                Log.i("TAG", "getData-->" + mediaItems.size());
            }
            mCursor.close();
        }
        //Handler发消息

        handler.sendEmptyMessage(10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getDataFromLocal();
                } else {
                    Toast.makeText(getActivity(), "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
//                    finish();

                }
                break;
            default:
        }
    }
}
