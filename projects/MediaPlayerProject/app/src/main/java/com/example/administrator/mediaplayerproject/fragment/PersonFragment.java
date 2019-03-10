package com.example.administrator.mediaplayerproject.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.mediaplayerproject.base.BaseFragment;

/**
*个人界面
*@author ZD
*created at 2017/8/14 17:10
*description：
*/

public class PersonFragment extends BaseFragment {


    private static final String TAG = PersonFragment.class.getSimpleName();//PersonFragment
    private TextView textView;

    @Override
    protected View initView() {
        Log.e(TAG, "个人页面被初始化了...");
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "个人Fragment数据被初始化了...");
        textView.setText("个人页面");
    }
}
