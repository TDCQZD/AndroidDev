package com.example.administrator.frescotest.activity;

import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
*
*@author ZD
*created at 2017/6/27 14:32
*description：图片的不同裁剪
*/

public class FrescoCropActivity extends AppCompatActivity implements View.OnClickListener {
    private SimpleDraweeView sdvFrescoCrop;
    private TextView tvFrescoExplain;
    private Button btFrescoCenter;
    private Button btFrescoCentercrop;
    private Button btFrescoFocuscrop;
    private Button btFrescoCenterinside;
    private Button btFrescoFitcenter;
    private Button btFrescoFitstart;
    private Button btFrescoFitend;
    private Button btFrescoFitxy;
    private Button btFrescoNone;

    private GenericDraweeHierarchyBuilder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_crop);
        findViews();
        builder = new GenericDraweeHierarchyBuilder(getResources());
    }


    private void findViews() {
        sdvFrescoCrop = (SimpleDraweeView) findViewById(R.id.sdv_fresco_crop);
        tvFrescoExplain = (TextView) findViewById(R.id.tv_fresco_explain);
        btFrescoCenter = (Button) findViewById(R.id.bt_fresco_center);
        btFrescoCentercrop = (Button) findViewById(R.id.bt_fresco_centercrop);
        btFrescoFocuscrop = (Button) findViewById(R.id.bt_fresco_focuscrop);
        btFrescoCenterinside = (Button) findViewById(R.id.bt_fresco_centerinside);
        btFrescoFitcenter = (Button) findViewById(R.id.bt_fresco_fitcenter);
        btFrescoFitstart = (Button) findViewById(R.id.bt_fresco_fitstart);
        btFrescoFitend = (Button) findViewById(R.id.bt_fresco_fitend);
        btFrescoFitxy = (Button) findViewById(R.id.bt_fresco_fitxy);
        btFrescoNone = (Button) findViewById(R.id.bt_fresco_none);

        btFrescoCenter.setOnClickListener(this);
        btFrescoCentercrop.setOnClickListener(this);
        btFrescoFocuscrop.setOnClickListener(this);
        btFrescoCenterinside.setOnClickListener(this);
        btFrescoFitcenter.setOnClickListener(this);
        btFrescoFitstart.setOnClickListener(this);
        btFrescoFitend.setOnClickListener(this);
        btFrescoFitxy.setOnClickListener(this);
        btFrescoNone.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btFrescoCenter) { // 居中，无缩放
            // 设置描述
            tvFrescoExplain.setText("居中，无缩放");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoCentercrop) {// 保持宽高比缩小或放大，使得两边都大于或等于显示边界。居中显示
            // 设置描述
            tvFrescoExplain.setText("保持宽高比缩小或放大，使得两边都大于或等于显示边界。居中显示");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoFocuscrop) { // 同centerCrop, 但居中点不是中点，而是指定的某个点,这里我设置为图片的左上角那点
            // 设置描述
            tvFrescoExplain.setText("同centerCrop, 但居中点不是中点，而是指定的某个点,这里我设置为图片的左上角那点");

            // 样式设置
            PointF point = new PointF(0, 0);
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                    .setActualImageFocusPoint(point).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoCenterinside) {
            // 使两边都在显示边界内，居中显示。如果图尺寸大于显示边界，则保持长宽比缩小图片
            // 设置描述
            tvFrescoExplain.setText("使两边都在显示边界内，居中显示。如果图尺寸大于显示边界，则保持长宽比缩小图片");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoFitcenter) {
            // 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内。居中显示
            // 设置描述
            tvFrescoExplain.setText("保持宽高比，缩小或者放大，使得图片完全显示在显示边界内。居中显示");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoFitstart) {
            // 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，不居中，和显示边界左上对齐
            // 设置描述
            tvFrescoExplain.setText("保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，不居中，和显示边界左上对齐");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoFitend) {
            // 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，不居中，和显示边界右下对齐
            // 设置描述
            tvFrescoExplain.setText("保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，不居中，和显示边界右下对齐");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoFitxy) {
            // 不保持宽高比，填充满显示边界
            // 设置描述
            tvFrescoExplain.setText("不保持宽高比，填充满显示边界");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY).build();

            // 图片显示
            imageDisplay(hierarchy);
        } else if (v == btFrescoNone) {
            // 如要使用title mode显示, 需要设置为none
            // 设置描述
            tvFrescoExplain.setText("如要使用title mode显示, 需要设置为none");

            // 样式设置
            GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(null).build();

            // 图片显示
            imageDisplay(hierarchy);
        }
    }

    private void imageDisplay(GenericDraweeHierarchy hierarchy) {
        sdvFrescoCrop.setHierarchy(hierarchy);

        // 加载图片
        Uri uri = Uri.parse("http://img4q.duitang.com/uploads/item/201305/20/20130520115416_VrUUR.jpeg");
        sdvFrescoCrop.setImageURI(uri);
    }


}
