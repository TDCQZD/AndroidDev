package com.example.administrator.eventbustest;

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
/**
*
*@author ZD
*created at 2017/6/26 11:28
*description：eventbus的发送事件页面
*/
public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btEventbusSendMain;
    private Button btEventbusSendSticky;
    private TextView tvEventbusSendResult;
    boolean isFirstFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        findViews();
    }


    private void findViews() {
        btEventbusSendMain = (Button) findViewById(R.id.bt_eventbus_send_main);
        btEventbusSendSticky = (Button) findViewById(R.id.bt_eventbus_send_sticky);
        tvEventbusSendResult = (TextView) findViewById(R.id.tv_eventbus_send_result);

        btEventbusSendMain.setOnClickListener(this);
        btEventbusSendSticky.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btEventbusSendMain) { // 主线程发送数据按钮点击事件处理
            // 发送消息
            EventBus.getDefault().post(new MessageEvent("主线程通过EventBus发送过来的数据"));

            // 结束当前页面
            finish();
        } else if (v == btEventbusSendSticky) { // 接收粘性事件数据按钮的点击事件处理
            if (isFirstFlag) {

                isFirstFlag = false;

                // 注册事件
                EventBus.getDefault().register(SendMessageActivity.this);
            }
        }
    }

    // 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(StickyEvent event) {//参数必须与事件类一致
        // 显示接收的数据
        tvEventbusSendResult.setText(event.message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册事件
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(SendMessageActivity.this);
    }
}
