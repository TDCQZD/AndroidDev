package com.example.administrator.frescotest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

/**
*
*@author ZD
*created at 2017/6/27 14:33
*description：圆形和圆角图片
*/


public class FrescoCircleAndCornerActivity extends AppCompatActivity {
    private SimpleDraweeView simpleDraweeView;
    private Uri uri;
    private GenericDraweeHierarchyBuilder builder;
    private RoundingParams parames;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_circle_and_corner);
        simpleDraweeView= (SimpleDraweeView) findViewById(R.id.sdv_fresco_circleandcorner);
        uri = Uri.parse("http://img4q.duitang.com/uploads/item/201304/27/20130427043538_wAfHC.jpeg");

        builder = new GenericDraweeHierarchyBuilder(getResources());
    }

    /**
     * 设置圆形图片
     */
    public void fresco_circle(View view) {

        // 设置圆形图片
        parames = RoundingParams.asCircle();
        GenericDraweeHierarchy hierarchy = builder.setRoundingParams(parames).build();
        simpleDraweeView.setHierarchy(hierarchy);

        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 设置圆角图片
     */
    public void fresco_corner(View view) {


        parames = RoundingParams.fromCornersRadius(50f);
        parames.setOverlayColor(getResources().getColor(android.R.color.holo_red_light));//覆盖层
        parames.setBorder(getResources().getColor(android.R.color.holo_blue_light), 5);//边框

        GenericDraweeHierarchy hierarchy = builder.setRoundingParams(parames).build();
        simpleDraweeView.setHierarchy(hierarchy);

        // 加载图片
        simpleDraweeView.setImageURI(uri);
    }
}
