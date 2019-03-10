package com.example.administrator.viewpagertest.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.viewpagertest.R;
import com.example.administrator.viewpagertest.adapter.ViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new ViewPagerAdapter(ViewPagerActivity.this));
    }
}
