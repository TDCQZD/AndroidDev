package com.example.administrator.bannertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.bannertest.activity.BannerAnimationActivity;
import com.example.administrator.bannertest.activity.BannerStyleActivity;
import com.example.administrator.bannertest.activity.CustomBannerActivity;
import com.example.administrator.bannertest.activity.CustomViewPagerActivity;
import com.example.administrator.bannertest.activity.IndicatorPositionActivity;
import com.example.administrator.bannertest.activity.ListviewActivity;
import com.example.administrator.bannertest.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.bannertest.App.titles;

/**
 * Banner学习使用
 *
 * @author ZD
 *         created at 2017/6/30 9:46
 *         description：能实现循环播放多个广告图片和手动滑动循环等功能
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
加载本地资源
第三方加载器一般都支持（资源文件、文件、Uri、assets、raw、ContentProvider、sd卡资源）
        这里只是以glide举例加载本地（资源文件）图片，
        加载本地图片的具体方法请参考你使用的图片加载器，
        图片加载的链接格式是你在setImages时设置的格式，
        原样返回的，所以格式问题请仔细检查！
 */
        Banner banner = (Banner) findViewById(R.id.banner);
        //本地图片数据（资源文件）
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    /**
     * 简单使用
     *
     * @param view
     */
    public void BannerBase(View view) {
        Banner banner = (Banner) findViewById(R.id.banner);
//        banner.setImages(App.images).setImageLoader(new GlideImageLoader()).start();
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(App.images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * 详细使用
     *
     * @param view
     */
    public void BannerDetailed(View view) {
        Banner banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(App.images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * ListView中使用
     *
     * @param view
     */
    public void BannerListview(View view) {
        startActivity(new Intent(this, ListviewActivity.class));
    }

    /**
     * banner动画预览
     *
     * @param view
     */
    public void BannerAnimation(View view) {
        startActivity(new Intent(this, BannerAnimationActivity.class));
    }

    /**
     * banner内置样式预览
     *
     * @param view
     */
    public void BannerStyle(View view) {
        startActivity(new Intent(this, BannerStyleActivity.class));
    }

    /**
     * banner指示器位置设置预览
     *
     * @param view
     */
    public void BannerIndicator(View view) {
        startActivity(new Intent(this, IndicatorPositionActivity.class));
    }

    /**
     * banner自定义样式方法预览
     */
    public void bannerCustomize(View view) {
        startActivity(new Intent(this, CustomBannerActivity.class));
    }

    /**
     * 自定义ViewPager
     *
     * @param view
     */
    public void CustomViewPager(View view) {
        startActivity(new Intent(this, CustomViewPagerActivity.class));
    }

}
