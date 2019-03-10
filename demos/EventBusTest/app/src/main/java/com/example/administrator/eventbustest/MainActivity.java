package com.example.administrator.eventbustest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.eventbustest.enent.MessageEvent;
import com.example.administrator.eventbustest.enent.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btEventbusSend;
    private Button btEventbusSticky;
    private TextView tvEventbusResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        EventBus.getDefault().register(MainActivity.this); //注册事件
    }


    private void findViews() {
        btEventbusSend = (Button) findViewById(R.id.bt_eventbus_send);
        btEventbusSticky = (Button) findViewById(R.id.bt_eventbus_sticky);
        tvEventbusResult = (TextView) findViewById(R.id.tv_eventbus_result);

        btEventbusSend.setOnClickListener(this);
        btEventbusSticky.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == btEventbusSend) {// 跳转到发送事件页面
            Intent intent = new Intent(MainActivity.this, SendMessageActivity.class);
            startActivity(intent);
        } else if (v == btEventbusSticky) {//发送粘性事件到发送事件页面

            // 发送粘性事件
            EventBus.getDefault().postSticky(new StickyEvent("主线程通过EventBus发送过来粘性事件数据"));
            // 跳转到发送数据页面
            Intent intent = new Intent(MainActivity.this, SendMessageActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 接受事件
     * @param event
     * ThreadMode.MAIN 表示这个方法在主线程中执行
     * ThreadMode.BACKGROUND 表示该方法在后台执行，不能并发处理
     * ThreadMode.ASYNC 也表示在后台执行，可以异步并发处理。
     * ThreadMode.POSTING 表示该方法和消息发送方在同一个线程中执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//事件说明
    public void MesssageEventBus(MessageEvent event) {
        tvEventbusResult.setText(event.message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MainActivity.this);////取消注册事件
    }
}
