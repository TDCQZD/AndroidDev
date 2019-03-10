package com.example.launchproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * @author ZD
 *         created at 2017/6/20 9:47
 *         description：使用线程启动Activity
 */
public class HandlerActivity extends AppCompatActivity {
    private static final String TAG = HandlerActivity.class.getSimpleName();
    private Handler handler = new Handler();
    private boolean isStartMain = false;
    private Button mSpJumpBtn;
    //由于CountDownTimer有一定的延迟，所以这里设置3400
    private CountDownTimer countDownTimer = new CountDownTimer(3400, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mSpJumpBtn.setText("跳过(" + 0 + "s)");
            startMainActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mSpJumpBtn = (Button) findViewById(R.id.sp_jump_btn);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {  //定时执行
                startMainActivity(); //执行在主线程中
                Log.e(TAG, "当前线程名称==" + Thread.currentThread().getName());
            }


        }, 5000);//定时

    }

    /**
     * 跳转到主页面，并且把当前页面关闭掉
     */
    private void startMainActivity() {

        if (!isStartMain) {
            isStartMain = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //关闭当前页面
            finish();
        }
    }

    /**
     * 跳过等待时间
     * @param event
     *
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent==Action"+event.getAction());
        startMainActivity();
        return super.onTouchEvent(event);
    }
    @Override
    protected void onDestroy() {
        //把所有的消息和回调移除
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
