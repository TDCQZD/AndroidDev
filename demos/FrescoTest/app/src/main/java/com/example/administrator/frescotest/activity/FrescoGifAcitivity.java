package com.example.administrator.frescotest.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.frescotest.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author ZD
 *         created at 2017/6/27 12:06
 *         description：GIF动画图片
 */
public class FrescoGifAcitivity extends AppCompatActivity {

    private SimpleDraweeView sdvFrescoGif;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_gif_acitivity);
        sdvFrescoGif = (SimpleDraweeView) findViewById(R.id.sdv_fresco_gif);
    }

    /**
     * 请求图片
     */
    public void askImg(View view) {
        Uri uri = Uri.parse("http://www.sznews.com/humor/attachement/gif/site3/20140902/4487fcd7fc66156f51db5d.gif");

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(false)
                .setOldController(sdvFrescoGif.getController())
                .build();

        sdvFrescoGif.setController(controller);
    }

    /**
     * 停止动画
     */
    public void stopAnim(View view) {
        Animatable animatable = sdvFrescoGif.getController().getAnimatable();

        if (animatable != null && animatable.isRunning()) {
            animatable.stop();
        }
    }

    /**
     * 开始动画
     */
    public void startAnim(View view) {
        Animatable animatable = sdvFrescoGif.getController().getAnimatable();

        if (animatable != null && !animatable.isRunning()) {
            animatable.start();
        }
    }
}
