package com.example.frame03_fragmenttabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.frame03_fragmenttabhost.bean.Tab;
import com.example.frame03_fragmenttabhost.fragment.CartFragment;
import com.example.frame03_fragmenttabhost.fragment.CategoryFragment;
import com.example.frame03_fragmenttabhost.fragment.HomeFragment;
import com.example.frame03_fragmenttabhost.fragment.HotFragment;
import com.example.frame03_fragmenttabhost.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;
/**
 *@Name：软件框架-TabHost
 *@author ：Administrator
 *@date ：2018/1/5 10:24
 *@Description：软件框架
 */
public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabhost;
    private LayoutInflater mInflator;
    private List<Tab> mTabs = new ArrayList<>(5);

    private CartFragment cartFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }
    /**
     * 初始化tab数据
     * FragmentTabHost基本使用
     * 1. Activity要继承FragmentActivity
     * 2.调⽤setup()⽅法
     * 3.添加TabSpec(indicator)
     */
    private void initTab() {
        mTabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(), R.id.realtabcontent);
        mInflator = LayoutInflater.from(this);

        /**
         * 添加tab显示的文字和图片，绑定fragment
         */
        Tab tab_home = new Tab(R.string.home,R.drawable.selector_icon_home, HomeFragment.class);
        Tab tab_hot = new Tab(R.string.hot,R.drawable.selector_icon_hot, HotFragment.class);
        Tab tab_catagory = new Tab(R.string.catagory,R.drawable.selector_icon_category, CategoryFragment.class);
        Tab tab_cart = new Tab(R.string.cart,R.drawable.selector_icon_cart, CartFragment.class);
        Tab tab_mine = new Tab(R.string.mine,R.drawable.selector_icon_mine, MineFragment.class);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_catagory);
        mTabs.add(tab_cart);
        mTabs.add(tab_mine);

        for (Tab tab:mTabs){
            //实例化TabSpec对象
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            //设置indicator
            tabSpec.setIndicator(buildIndicator(tab));
            //添加tabSpec
            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }

        //刷新数据
//        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//
//                if (tabId == getString(R.string.cart)){
//
//                    refreshCartData();
//
//                }
//
//            }
//        });

        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);
    }
    /**
     * indicator包括ImageView和TextView
     * @param tab
     * @return
     */
    private View buildIndicator(Tab tab) {
        View view = mInflator.inflate(R.layout.tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setImageResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            this.gotoMineFragment();
        }
    }

    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;
    private void gotoMineFragment() {
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();
        MineFragment  mineFragment = new MineFragment();
        ftransaction.replace(R.id.realtabcontent, mineFragment);
        ftransaction.commit();
    }
}
