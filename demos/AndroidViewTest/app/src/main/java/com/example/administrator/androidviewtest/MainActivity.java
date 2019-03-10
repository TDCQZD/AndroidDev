package com.example.administrator.androidviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetector(this, new MyOnGestureListener());
        //解决长按屏幕后无法拖动的现象
        mGestureDetector.setIsLongpressEnabled(false);

        new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        };
    }

    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {//双击
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {//单击
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {//拖动
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {//长按

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {//快速滑动
            return false;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {//事件分发
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//事件处理

        /*
        VelocityTracker 追踪器
         */
        VelocityTracker velocityTracker = VelocityTracker.obtain();//定义追踪器
        velocityTracker.addMovement(event);//添加要追踪的事件
        velocityTracker.computeCurrentVelocity(1000);//时间间隔
        //计算速度并获取速度
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();
        Log.i("TAG", "VelocityTracker:" + "\n" + "xVelocity:" + xVelocity + "\n" + "yVelocity:" + yVelocity);
        velocityTracker.clear();//重置
        velocityTracker.recycle();//回收内存
        boolean consume = mGestureDetector.onTouchEvent(event);
        return consume;
//        return super.onTouchEvent(event);
    }
}
