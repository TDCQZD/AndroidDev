package com.example.administrator.mediaplayerproject.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.mediaplayerproject.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/10.
 */

public class NetVideoFragment extends BaseFragment {
    private static final String TAG = NetVideoFragment.class.getSimpleName();//NetVideoFragment
    private TextView textView;

    @Override
    protected View initView() {
        Log.e(TAG, "网络视频页面被初始化了...");
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "网络视频Fragment数据被初始化了...");
        textView.setText("网络视频页面");
    }
}
