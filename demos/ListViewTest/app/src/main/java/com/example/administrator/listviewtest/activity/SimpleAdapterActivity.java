package com.example.administrator.listviewtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.listviewtest.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/21.
 * SimpleAdapter 用来绑定在xml中定义的控件对应的数据
 * SimpleAdapter的步骤:（1）根据需要定义ListView每行所实现的布局。
 * （2）定义一个HashMap构成的列表，将数据以键值对的方式存放在里面。
 * （3）构造SimpleAdapter对象。
 * （4）将LsitView绑定到SimpleAdapter上。
 *  使用simpleAdapter的数据一般都是用HashMap构成的列表，列表的每一节对应ListView的每一行。通过SimpleAdapter的构造函数，将HashMap的每个键的数据映射到布局文件中对应控件上。
 *  这个布局文件一般根据自己的需要来自己定义
 */

public class SimpleAdapterActivity extends Activity {
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(R.layout.my_list);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("SimpleAdapter自定义数据类型");
        lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用
           /*定义一个动态数组*/
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
          /*在数组中存放数据*/
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_launcher);//加入图片
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemText", "这是第" + i + "行");
            listItem.add(map);
        }

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem,//需要绑定的数据
                R.layout.my_list_item,//每一行的布局
                  //动态数组中的数据源的键对应到定义布局的View中
                new String[]{"ItemImage", "ItemTitle", "ItemText"},
                new int[]{R.id.ItemImage, R.id.ItemTitle, R.id.ItemText}
        );

        lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(SimpleAdapterActivity.this,"你点击了第"+arg2+"行",Toast.LENGTH_LONG).show();

            }
        });

    }
}
