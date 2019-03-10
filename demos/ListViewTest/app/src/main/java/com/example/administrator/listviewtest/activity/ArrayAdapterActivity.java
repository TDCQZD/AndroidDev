package com.example.administrator.listviewtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.listviewtest.R;

/**
 *
 * ArrayAdapter 用来绑定一个数组，支持泛型操作
 * 使用的步骤:（1）定义一个数组来存放ListView中item的内容。
 * （2）通过实现ArrayAdapter的构造函数来创建一个ArrayAdapter的对象。
 * （3）通过ListView的setAdapter()方法绑定ArrayAdapter。
 * ArrayAdapter有多个构造函数，最常用的一种是：第一个参数为上下文，
 * 第二个参数为一个包含TextView，用来填充ListView的每一行的布局资源ID。
 * 第三个参数为ListView的内容
 * 注：其中第二个参数可以自定义一个layout，但是这个layout必须要有TextView控件
 * （1）通过指定android.R.layout.simple_list_item_checked这个资源，实现带选择框的ListView。需要用setChoiceMode()方法设定选择为多选还是单选，否则将不能实现选择效果。
 * (2)通过指定android.R.layout.simple_list_item_multiple_choice这个资源实现带CheckBox的ListView。同样的，需要用setChoiceMode()方法来设置单选或者多选.
 * (3)通过指定android.R.layout.simple_list_item_single_choice这个资源实现带RadioButton的ListView。这里要注意的是，这里并不是指定了单选。是多选还是单选要通过setChoiceMode()方法来指定
 */

public class ArrayAdapterActivity extends Activity {
    private static final String[] strs = new String[]{
            "张三", "李四", "王五", "赵六", "韩七"};//定义一个String数组用来显示ListView的内容
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(R.layout.my_list);
        lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("ArrayAdapter绑定数组数据");
        //为ListView设置Adapter来绑定数据
//        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));

         // 实现带选择框的ListView
//        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, strs));
//        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

         // 带CheckBox的ListView
//        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strs));
//        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

//        带RadioButton的ListView

        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        /*
        listview 监听事件
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
                //点击后在标题上显示点击了第几行
                Toast.makeText(ArrayAdapterActivity.this,"你点击了第"+arg2+"行",Toast.LENGTH_LONG).show();
            }
        });
    }
}
