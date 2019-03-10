package com.example.administrator.frescotest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
*
*@author ZD
*created at 2017/6/27 14:34
*description：图片修改和旋转
*/


public class FrescoResizeActivity extends AppCompatActivity {
    private SimpleDraweeView sdvFrescoResize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_resize);
        sdvFrescoResize = (SimpleDraweeView) findViewById(R.id.sdv_fresco_resize);

    }
    // 修内存中改图片大小
    public void resize(View view) {
        // 图片地址
        Uri uri = Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/962bd40735fae6cd21a519680db30f2442a70fa1.jpg");

        // 图片的请求
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(50,50))
                .build();

        // 控制图片的加载
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(sdvFrescoResize.getController())
                .setImageRequest(request)
                .build();

        // 加载图片
        sdvFrescoResize.setController(controller);
    }
    // 旋转图片
    public void rotate(View view) {
        Uri uri = Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/962bd40735fae6cd21a519680db30f2442a70fa1.jpg");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)
                .build();

        // 控制图片的加载
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(sdvFrescoResize.getController())
                .setImageRequest(request)
                .build();


        // 加载图片
        sdvFrescoResize.setController(controller);
    }
}
