package com.example.administrator.viewpagertest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
*
*@author ZD
*created at 2017/6/23 14:09
*description：Viewpager适配器
*/

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    /*
    显示页面个数
     */
    @Override
    public int getCount() {
        return 5;
    }

    /*
    判断初始化返回的Object是不是一个View对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*
    初始化显示的条目对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        // 准备显示的数据，一个简单的TextView
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setText("我是天才" + position + "号");
        switch (position) {
            case 0:
                tv.setBackgroundColor(Color.GREEN);
                break;
            case 1:
                tv.setBackgroundColor(Color.RED);
                break;
            case 2:
                tv.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                tv.setBackgroundColor(Color.BLUE);
                break;
            case 4:
                tv.setBackgroundColor(Color.CYAN);
                break;

        }

        // 添加到ViewPager容器
        container.addView(tv);

        // 返回填充的View对象
        return tv;
    }

    /*
    销毁条目对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
