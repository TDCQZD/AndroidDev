package com.example.frame03_fragmenttabhost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frame03_fragmenttabhost.R;


/**
 * 购物车
 * 添加商品到购物车，CartProvider获取购物车数据，并显示总价，选中的商品可进行购买跳到结算页面
 * 购物车为空则不能购买
 */
public class CartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);



        return view;
    }

}
