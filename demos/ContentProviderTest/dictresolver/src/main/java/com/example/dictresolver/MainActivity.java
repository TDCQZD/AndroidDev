package com.example.dictresolver;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictresolver.utils.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// 获取系统的ContentResolver对象
        contentResolver = getContentResolver();

    }
    /*
   查找
    */
    public void search(View view) {
        // 获取用户输入
        String key = ((EditText) findViewById(R.id.key))
                .getText().toString();
        // 执行查询
        Cursor cursor = contentResolver.query(
                Words.Word.DICT_CONTENT_URI, null,
                "word like ? or detail like ?", new String[] {
                        "%" + key + "%", "%" + key + "%" }, null);
        // 创建一个Bundle对象
        Bundle data = new Bundle();
        data.putSerializable("data", converCursorToList(cursor));
        // 创建一个Intent
        Intent intent = new Intent(MainActivity.this,
                SearchResultActivity.class);
        intent.putExtras(data);
        // 启动Activity
        startActivity(intent);

    }

    /*
    添加生词
     */
    public void insert(View view) {
        // 获取用户输入
        String word = ((EditText) findViewById(R.id.word))
                .getText().toString();
        String detail = ((EditText) findViewById(R.id.detail))
                .getText().toString();
        // 插入生词记录
        ContentValues values = new ContentValues();
        values.put(Words.Word.WORD, word);
        values.put(Words.Word.DETAIL, detail);
        contentResolver.insert(
                Words.Word.DICT_CONTENT_URI, values);
        // 显示提示信息
        Toast.makeText(MainActivity.this, "添加生词成功！"
                , Toast.LENGTH_SHORT).show();
    }


    private ArrayList<Map<String, String>> converCursorToList(Cursor cursor)
    {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        // 遍历Cursor结果集
        while (cursor.moveToNext())
        {
            // 将结果集中的数据存入ArrayList中
            Map<String, String> map = new HashMap<>();
            // 取出查询记录中第2列、第3列的值
            map.put(Words.Word.WORD, cursor.getString(1));
            map.put(Words.Word.DETAIL, cursor.getString(2));
            result.add(map);
        }
        return result;
    }
}
