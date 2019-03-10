package com.example.administrator.natificationtest;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        /*
        取消通知②
         */
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.cancel(1);
        Intent intent=getIntent();
        String data=intent.getStringExtra("data");
        Toast.makeText(NotificationActivity.this, "接受的数据："+data, Toast.LENGTH_SHORT).show();
        Log.i("TAG", "数据："+data);
    }
}
