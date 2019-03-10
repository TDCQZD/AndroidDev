package com.example.administrator.listviewtest.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.listviewtest.R;
import com.example.administrator.listviewtest.adapter.MyBaseAdapter;
import com.example.administrator.listviewtest.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class SwipeMenuListViewActivity extends AppCompatActivity {
    private SwipeMenuListView listView;
    private Context context;
    List<ItemBean> list;

    MyBaseAdapter myBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_list_view);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("ListView使用整理");
        initData();

    }

    //初始化ListView数据，在OnCreate方法中调用
    private void initData() {

       list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(new ItemBean(R.mipmap.ic_launcher, "Title" + i, "Content" + i));
        }

        myBaseAdapter = new MyBaseAdapter(list, this);
        listView.setAdapter(myBaseAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(context);
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("打开");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(SwipeMenuListViewActivity.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                openItem.setTitleSize(18);
                deleteItem.setTitle("删除");
//
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ItemBean itemBean = list.get(position);

                switch (index) {
                    case 0:
                        // open
                        open(itemBean);
                        break;
                    case 1:
                        // delete
//                        delete(itemBean);
                        list.remove(position);
                        myBaseAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });
        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                ItemBean itemBean = list.get(position);

                open(itemBean);
            }
        });
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /**
     * 打开
     *
     * @param item
     */
    private void open(ItemBean item) {

    }

    /**
     * 删除
     *
     * @param item
     */
    private void delete(ItemBean item) {

    }
}
