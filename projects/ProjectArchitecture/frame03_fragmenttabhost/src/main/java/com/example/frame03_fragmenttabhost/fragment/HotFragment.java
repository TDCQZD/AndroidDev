package com.example.frame03_fragmenttabhost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frame03_fragmenttabhost.R;

/**
 * 热卖 MaterialRefreshLayout：进行数据刷新，实现下拉加载和上拉加载更多
 * RecyclerView：显示数据
 */
public class HotFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hot, container, false);



        return view;
    }

}
