package com.example.administrator.mediaplayerproject.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.administrator.mediaplayerproject.R;
import com.example.administrator.mediaplayerproject.base.BaseFragment;
import com.example.administrator.mediaplayerproject.fragment.LiveFragment;
import com.example.administrator.mediaplayerproject.fragment.LocalVideoFragment;
import com.example.administrator.mediaplayerproject.fragment.NetVideoFragment;
import com.example.administrator.mediaplayerproject.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;

    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new LocalVideoFragment());//本地视频Fragment
        mBaseFragment.add(new NetVideoFragment());//网络视频Fragment
        mBaseFragment.add(new LiveFragment());//直播ragment
        mBaseFragment.add(new PersonFragment());//个人ragment
    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_fragme_local_video);
    }

    /**
     * RadioGroup切换监听
     */
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_fragme_local_video://本地视频
                    position = 0;
                    break;
                case R.id.rb_fragme_net_video://网络视频
                    position = 1;
                    break;
                case R.id.rb_fragme_live://直播
                    position = 2;
                    break;
                case R.id.rb_fragme_person://个人
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }
            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent, to);
        }

        /**
         * 根据位置得到对应的Fragment
         *
         * @return
         */
        private BaseFragment getFragment() {
            BaseFragment fragment = mBaseFragment.get(position);
            return fragment;
        }


    }

    /**
     * 切换Fragment
     *
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            //开启事务
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fragment, to).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }
            }
        }
    }
}
