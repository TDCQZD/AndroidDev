package com.example.administrator.androidutillists.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import com.example.administrator.androidutillists.R;


/**
 * Created by lenovo on 2017/4/12.
 */

public class MyEditText extends EditText {
    private int mBorderColor;
    private int mBorderWidth;
    private int mBorderRadius;

    private int mCodeLength;
    private int mCodeColor;
    private int mCodeSize;

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private RectF mRectF;

    private String mTextContent;
    private int mTextLength;

    //当不需要使用xml声明或者不需要使用inflate动态加载时候，实现此构造函数即可
    public MyEditText(Context context) {
        this(context, null);
    }

    //在xml创建但是没有指定style的时候被调用
    public MyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //有指定style的时候被调用（注：虽然我将代码写在这地方，但是我并没有指定style，事实上，程序会使用第二个构造函数，只是将第三个参数传0而已）
    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们在attrs定义的自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerifyEditView, defStyleAttr, 0);
        mBorderColor = a.getColor(R.styleable.VerifyEditView_VerifyBorderColor, Color.BLACK);//第二个参数为默认值，当我们在使用此控件但是没有定义此属性值的时候，会使用此处设置的默认值
        mBorderWidth = a.getDimensionPixelSize(R.styleable.VerifyEditView_VerifyBorderWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        mBorderRadius = a.getDimensionPixelSize(R.styleable.VerifyEditView_VerifyBorderRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        mCodeColor = a.getColor(R.styleable.VerifyEditView_VerifyCodeTextColor, Color.BLACK);
        mCodeLength = a.getInt(R.styleable.VerifyEditView_VerifyCodeLength, 6);
        mCodeSize = a.getDimensionPixelSize(R.styleable.VerifyEditView_VerifyCodeTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, getResources().getDisplayMetrics()));
        a.recycle();
        //定义一个Paint，并支持抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    //在绘制控件时候会自动调用
    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();//获取控件的宽度
        mHeight = getHeight();//获取控件的高度
        mRectF = new RectF(0, 0, mWidth, mHeight);
        //绘制圆角矩形边框，但是发现，相对于在xml设置相同参数的shape,此处绘制会有明显的不顺滑，如有知道为什么的，请留言告知，谢谢
        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setColor(mBorderColor);
        //mPaint.setStrokeWidth(mBorderWidth);
        //canvas.drawRoundRect(mRectF,mBorderRadius,mBorderRadius,mPaint);

        //分割线
        float offset = mRectF.width() / mCodeLength;
        float lineX;
        mPaint.setStrokeWidth(1);
        for (int i = 1; i < mCodeLength; i++) {
            lineX = mRectF.left + offset * i;
            canvas.drawLine(lineX, 0, lineX, mRectF.height(), mPaint);
        }

        mPaint.setColor(mCodeColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mCodeSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLine = (mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
        float codeX;
        //更新数字
        for (int i = 0; i < mTextLength; i++) {
            codeX = mRectF.left + offset * i + offset / 2;
            canvas.drawText(mTextContent.substring(i, i + 1), codeX, baseLine, mPaint);
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.mTextContent = text.toString();
        this.mTextLength = text.toString().length();
        invalidate();//当text改变的时候，重新绘制控件
    }
}
