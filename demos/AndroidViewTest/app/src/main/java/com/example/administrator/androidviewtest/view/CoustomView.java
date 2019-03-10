package com.example.administrator.androidviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Px;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/8/23.
 */

public class CoustomView extends View {

    public CoustomView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (onFilterTouchEventForSecurity(event)) {

        }
        return onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(@Px int l, @Px int t, @Px int r, @Px int b) {//确定View在父容器的位置
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {//父容器遍历确定子元素的位置
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {//将View绘制在屏幕上
        super.onDraw(canvas);
    }
}
