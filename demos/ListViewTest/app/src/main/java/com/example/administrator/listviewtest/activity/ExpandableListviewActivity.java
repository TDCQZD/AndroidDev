package com.example.administrator.listviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.administrator.listviewtest.R;
/**
*可以扩展的ListVIew-ExpandableListview
*@author ZD
*created at 2017/7/21 17:30
*description：
*/ 
public class ExpandableListviewActivity extends AppCompatActivity {
private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_expandable_listview);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("可扩展的ListView");
        initData();
    }

    private void initData() {
        //创建一个BaseExpandableListAdapter对象
        ExpandableListAdapter adapter = new BaseExpandableListAdapter()
        {
            int[] logos = new int[]
                    {
                            R.drawable.p,
                            R.drawable.z,
                            R.drawable.t
                    };
            private String[] armTypes = new String[]
                    { "神族兵种", "虫族兵种", "人族兵种"};
            private String[][] arms = new String[][]
                    {
                            { "狂战士", "龙骑士", "黑暗圣堂", "电兵" },
                            { "小狗", "刺蛇", "飞龙", "自爆飞机" },
                            { "机枪兵", "护士MM" , "幽灵" }
                    };
            // 获取指定组位置、指定子列表项处的子列表项数据
            @Override
            public Object getChild(int groupPosition, int childPosition)
            {
                return arms[groupPosition][childPosition];
            }
            @Override
            public long getChildId(int groupPosition, int childPosition)
            {
                return childPosition;
            }
            @Override
            public int getChildrenCount(int groupPosition)
            {
                return arms[groupPosition].length;
            }
            private TextView getTextView()
            {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 64);
                TextView textView = new TextView(ExpandableListviewActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(20);
                return textView;
            }
            // 该方法决定每个子选项的外观
            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent)
            {
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition)
                        .toString());
                return textView;
            }
            // 获取指定组位置处的组数据
            @Override
            public Object getGroup(int groupPosition)
            {
                return armTypes[groupPosition];
            }
            @Override
            public int getGroupCount()
            {
                return armTypes.length;
            }
            @Override
            public long getGroupId(int groupPosition)
            {
                return groupPosition;
            }
            // 该方法决定每个组选项的外观
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent)
            {
                LinearLayout ll = new LinearLayout(ExpandableListviewActivity.this);
                ll.setOrientation(0);
                ImageView logo = new ImageView(ExpandableListviewActivity.this);
                logo.setImageResource(logos[groupPosition]);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);
                return ll;
            }
            @Override
            public boolean isChildSelectable(int groupPosition,
                                             int childPosition)
            {
                return true;
            }
            @Override
            public boolean hasStableIds()
            {
                return true;
            }
        };
        ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.list);
        expandListView.setAdapter(adapter);
    }


}
