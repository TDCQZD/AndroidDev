package com.example.frame01_fragmentradiogroup.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.frame01_fragmentradiogroup.adapter.CommonFrameFragmentAdapter;
import com.example.frame01_fragmentradiogroup.base.BaseFragment;


/**
 * 作者：尚硅谷-杨光福 on 2016/7/21 19:27
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：常用框架Fragment
 */
public class CommonFrameFragment extends BaseFragment {

    private TextView textView;

    private CommonFrameFragmentAdapter adapter;


    private static final String TAG = CommonFrameFragment.class.getSimpleName();//"CommonFrameFragment"

    @Override
    protected View initView() {
        Log.e(TAG, "主页面被初始化了...");
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "主页面Fragment数据被初始化了...");
        textView.setText("主页面");
    }
}
