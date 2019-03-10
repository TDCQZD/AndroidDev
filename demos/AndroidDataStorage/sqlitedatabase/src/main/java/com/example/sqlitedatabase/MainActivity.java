package com.example.sqlitedatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btDataCreate;
    private Button btDataAdd;
    private Button btDataUpdate;
    private Button btDataDelete;
    private Button btDataQuery;
    private SQLiteDataBaseHelper sdbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdbHelper = new SQLiteDataBaseHelper(this, "data.db", null, 2);

        findViews();
    }


    private void findViews() {
        btDataCreate = (Button) findViewById(R.id.bt_data_create);
        btDataAdd = (Button) findViewById(R.id.bt_data_add);
        btDataUpdate = (Button) findViewById(R.id.bt_data_update);
        btDataDelete = (Button) findViewById(R.id.bt_data_delete);
        btDataQuery = (Button) findViewById(R.id.bt_data_query);

        btDataCreate.setOnClickListener(this);
        btDataAdd.setOnClickListener(this);
        btDataUpdate.setOnClickListener(this);
        btDataDelete.setOnClickListener(this);
        btDataQuery.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        db = sdbHelper.getWritableDatabase();
        if (v == btDataCreate) {
            createDB();
        } else if (v == btDataAdd) {
            addData();
        } else if (v == btDataUpdate) {
            UpdateData();
        } else if (v == btDataDelete) {
            deleteData();
        } else if (v == btDataQuery) {
            queryData();
        }
    }

    private void queryData() {
//        db.rawQuery("select * from Book",null);//SQL操作
        // 查询Book表中所有的数据
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d("MainActivity", "book name is " + name);
                Log.d("MainActivity", "book author is " + author);
                Log.d("MainActivity", "book pages is " + pages);
                Log.d("MainActivity", "book price is " + price);
            } while (cursor.moveToNext());
        }
        cursor.close();

    }

    private void deleteData() {
//        db.execSQL("delete from Book where pages > ？",new String[] {"500"});
        db.delete("Book", "pages > ?", new String[]{"500"});
    }

    private void UpdateData() {
//        db.execSQL("update Book set price = ? where name = ?", new String[]{"10.99", "The Da Vinci Code"});
        ContentValues values = new ContentValues();
        values.put("price", 10.99);
        db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
    }

    private void addData() {
//db.execSQL("insert into Book （name,author,pages,price） values(?,?,?,?)",new String []{"The Da Vinci Code","Dan Brown","454","16.96"});

        ContentValues values = new ContentValues();
        // 开始组装第一条数据
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.96);
        db.insert("Book", null, values); // 插入第一条数据
        values.clear();
        // 开始组装第二条数据
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.95);
        db.insert("Book", null, values); // 插入第二条数据
    }

    private void createDB() {
        Toast.makeText(MainActivity.this, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

}
