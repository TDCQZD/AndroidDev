package com.example.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alarmmanager.service.ChangeWallpaperService;

public class ChangeAlarmActivity extends AppCompatActivity {
    Intent intent;
    PendingIntent pi;
    Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_alarm);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        // 指定启动ChangeService组件
        intent = new Intent(ChangeAlarmActivity.this, ChangeWallpaperService.class);
        // 创建PendingIntent对象
        pi = PendingIntent.getService(ChangeAlarmActivity.this, 0, intent, 0);
    }

    public void start(View view) {
// 获取AlarmManager对象
        AlarmManager aManager = (AlarmManager) getSystemService(
                Service.ALARM_SERVICE);
        // 设置每隔5秒执行pi代表的组件一次
        aManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP
                , 0, 5000, pi);
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(ChangeAlarmActivity.this
                , "壁纸定时更换启动成功啦",
                Toast.LENGTH_SHORT).show();
    }


    public void stop(View view) {

        start.setEnabled(true);
        stop.setEnabled(false);
        // 获取AlarmManager对象
        AlarmManager aManager = (AlarmManager) getSystemService(
                Service.ALARM_SERVICE);
        // 取消对pi的调度
        aManager.cancel(pi);
    }


}
