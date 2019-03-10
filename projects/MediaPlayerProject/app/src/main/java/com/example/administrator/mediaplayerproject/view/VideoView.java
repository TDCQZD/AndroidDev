package com.example.administrator.mediaplayerproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
/**
*自定义VideoView播放器
*@author ZD
*created at 2017/8/15 14:38
*description：重新调用VideoView的宽和高
*/

public class VideoView extends android.widget.VideoView{


    /*
    空的构造器
     */
    public VideoView(Context context) {
        super(context);
    }

    /*
    当这个类在布局文件的时候，系统通过该构造方法实例化该类
     */
    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    当需要设置样式的时候调用该方法
     */
    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    /*
    设置视频的宽和高
     */
    public void setVideoSize(int videoWidth,int videoHeight){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = videoWidth;
        params.height = videoHeight;
        setLayoutParams(params);
    }
}
