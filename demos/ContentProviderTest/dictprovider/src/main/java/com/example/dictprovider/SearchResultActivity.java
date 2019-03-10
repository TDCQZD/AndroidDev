package com.example.dictprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;
/**
*单词查询结果
*@author ZD
*created at 2017/8/5 16:35
*description：
*/ 
public class SearchResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ListView listView = (ListView) findViewById(R.id.show);
        Intent intent = getIntent();
        // 获取该intent所携带的数据
        Bundle data = intent.getExtras();
        // 从Bundle数据包中取出数据
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = (List<Map<String, String>>)
                data.getSerializable("data");
        // 将List封装成SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(SearchResultActivity.this
                , list,
                R.layout.line, new String[] { "word", "detail" }
                , new int[] {R.id.word, R.id.detail });
        // 填充ListView
        listView.setAdapter(adapter);
    }
}
