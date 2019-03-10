package com.example.zhangdai.projectguideview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.example.zhangdai.projectguideview.MainActivity;
import com.example.zhangdai.projectguideview.R;

/**
 * Created by zhangdai on 2017/2/21.
 */
public class SinglePage extends Activity {

    private static final String TAG = SinglePage.class.getSimpleName();

    private Handler handler = new Handler();
    private boolean isStartMain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepage);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //两秒后才执行到这里
                //执行在主线程中
                startMainActivity();
                Log.e(TAG, "当前线程名称==" + Thread.currentThread().getName());
            }
        }, 2000);
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

    //点击跳过时间
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent==Action" + event.getAction());
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
