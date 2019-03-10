package com.example.vibratortest;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取系统的Vibrator服务
        vibrator = (Vibrator) getSystemService(
                Service.VIBRATOR_SERVICE);
    }

    // 重写onTouchEvent方法，当用户触碰触摸屏时触发该方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "手机振动"
                , Toast.LENGTH_SHORT).show();
        // 控制手机振动2秒
        vibrator.vibrate(2000);
        return super.onTouchEvent(event);
    }
}
