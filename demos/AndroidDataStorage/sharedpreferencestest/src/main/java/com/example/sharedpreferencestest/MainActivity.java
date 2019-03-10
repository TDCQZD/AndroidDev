package com.example.sharedpreferencestest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btDataSave;
    private Button btDataRead;
    private TextView tvShow;
    private SharedPreferences sp;
    private SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getPreferences(MODE_PRIVATE);
        spe = sp.edit();
        findViews();
    }


    private void findViews() {
        btDataSave = (Button) findViewById(R.id.bt_Data_save);
        btDataRead = (Button) findViewById(R.id.bt_Data_read);
        tvShow = (TextView) findViewById(R.id.tv_show);

        btDataSave.setOnClickListener(this);
        btDataRead.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btDataSave) {
            saveData();
        } else if (v == btDataRead) {
            readData();
        }
    }

    private void readData() {
        String name = sp.getString("name", "");
        String sex = sp.getString("sex", "");
        int hight = sp.getInt("身高", 0);
        Boolean b = sp.getBoolean("婚姻状况", false);
        tvShow.setText("name:" + name + "\n" + "sex:" + sex + "\n" + "身高:" + hight + "\n" + "婚姻状况:" + b);
//        Log.i("TAG", "MainActivity readData()--->" + name);
    }

    private void saveData() {

        spe.putString("name", "张三");
        spe.putString("sex", "张三");
        spe.putInt("身高", 170);
        spe.putBoolean("婚姻状况", false);
        spe.commit();
        Toast.makeText(MainActivity.this, "数据写入成功", Toast.LENGTH_SHORT).show();
    }

}
