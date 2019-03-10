package com.example.litepaltest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.litepaltest.bean.Book;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btDataAdd;
    private Button btDataUpdate;
    private Button btDataDelete;
    private Button btDataQuery;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Connector.getDatabase();
        findViews();

    }


    private void findViews() {
        btDataAdd = (Button) findViewById(R.id.bt_data_add);
        btDataUpdate = (Button) findViewById(R.id.bt_data_update);
        btDataDelete = (Button) findViewById(R.id.bt_data_delete);
        btDataQuery = (Button) findViewById(R.id.bt_data_query);

        btDataAdd.setOnClickListener(this);
        btDataUpdate.setOnClickListener(this);
        btDataDelete.setOnClickListener(this);
        btDataQuery.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Connector.getDatabase();
        if (v == btDataAdd) {
            addData();
        } else if (v == btDataUpdate) {
            updateData();
        } else if (v == btDataDelete) {
            deleteData();
        } else if (v == btDataQuery) {
            queryData();
        }
    }

    private void queryData() {
        List<Book> books = DataSupport.findAll(Book.class);
        for (Book book : books) {
            Log.d("MainActivity", "book name is " + book.getName());
            Log.d("MainActivity", "book author is " + book.getAuthor());
            Log.d("MainActivity", "book pages is " + book.getPages());
            Log.d("MainActivity", "book price is " + book.getPrice());
            Log.d("MainActivity", "book press is " + book.getPress());
        }
    }

    private void deleteData() {
        DataSupport.deleteAll(Book.class, "price > ?", "15");//删除符合条件的数据
//        DataSupport.deleteAll(Book.class);//删除表
    }

    private void updateData() {

        Book book = new Book();
        book.setPrice(14.95);
        book.setPress("Anchor");
        book.updateAll("price = ? and press = ?", "16.96", "Unknow");

//        book.setAuthor("ZD");
//        book.updateAll("author = ?", "Dan Brown");
    }

    private void addData() {
        Book book = new Book();
        book.setName("The Da Vinci Code");
        book.setAuthor("Dan Brown");
        book.setPages(454);
        book.setPrice(16.96);
        book.setPress("Unknow");
        book.save();
    }

}
