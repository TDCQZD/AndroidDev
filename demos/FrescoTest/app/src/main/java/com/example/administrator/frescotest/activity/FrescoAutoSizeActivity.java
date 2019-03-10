package com.example.administrator.frescotest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * @author ZD
 *         created at 2017/6/27 11:55
 *         description：动态展示图片
 */

public class FrescoAutoSizeActivity extends AppCompatActivity {

    private Button btFrescoLoadsmall;
    private LinearLayout llFresco;
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_auto_size);

        btFrescoLoadsmall = (Button) findViewById(R.id.bt_fresco_loadsmall);
        llFresco = (LinearLayout) findViewById(R.id.ll_fresco);

        simpleDraweeView = new SimpleDraweeView(this);
        // 设置宽高比
        simpleDraweeView.setAspectRatio(3.0f);
    }


    public void fresco_loadsmall(View view) {
        // 图片的地址
        Uri uri = Uri.parse("http://img4q.duitang.com/uploads/item/201304/27/20130427043538_wAfHC.jpeg");
        // 图片的请求
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();

        // 加载图片的控制
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();

        // 加载图片
        simpleDraweeView.setController(controller);

        // 添加View到线性布局中
        llFresco.addView(simpleDraweeView);
    }


}
