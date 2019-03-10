package com.example.smsmanager;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 短信发送
 *
 * @author ZD
 *         created at 2017/8/7 16:17
 *         description：
 */
public class MainActivity extends AppCompatActivity {
    SmsManager sManager;
    EditText number, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  获取SmsManager
        sManager = SmsManager.getDefault();
        sManager = SmsManager.getDefault();
        // 获取程序界面上的两个文本框和按钮
        number = (EditText) findViewById(R.id.number);
        content = (EditText) findViewById(R.id.content);

    }

    public void sendSMS(View view) {
        // 创建一个PendingIntent对象
        PendingIntent pi = PendingIntent.getActivity(
                MainActivity.this, 0, new Intent(), 0);
        // 发送短信
        sManager.sendTextMessage(number.getText().toString(),
                null, content.getText().toString(), pi, null);
        // 提示短信发送完成
        Toast.makeText(MainActivity.this, "短信发送完成", Toast.LENGTH_SHORT).show();
    }

    public void groupSMS(View view) {
        startActivity(new Intent(MainActivity.this, GroupSMSActivity.class));
    }
}
