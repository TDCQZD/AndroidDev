package com.example.administrator.picassotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.picassotest.activity.PicassoListviewActivity;
import com.example.administrator.picassotest.activity.PicassoTransfromationsActivity;
import com.squareup.picasso.Picasso;

/**
 * @author ZD
 *         created at 2017/6/27 9:47
 *         description：Picasso使用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btPicassoBase;
    private Button btPicassoListview;
    private Button btPicassoTranformations;
    private ImageView ivPicassoResult1;
    private ImageView ivPicassoResult2;
    private ImageView ivPicassoResult3;
    private ImageView ivPicassoResult4;
    private ImageView ivPicassoResult5;
    private ImageView ivPicassoResult6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        btPicassoBase = (Button) findViewById(R.id.bt_picasso_base);
        btPicassoListview = (Button) findViewById(R.id.bt_picasso_listview);
        btPicassoTranformations = (Button) findViewById(R.id.bt_picasso_tranformations);
        ivPicassoResult1 = (ImageView) findViewById(R.id.iv_picasso_result1);
        ivPicassoResult2 = (ImageView) findViewById(R.id.iv_picasso_result2);
        ivPicassoResult3 = (ImageView) findViewById(R.id.iv_picasso_result3);
        ivPicassoResult4 = (ImageView) findViewById(R.id.iv_picasso_result4);
        ivPicassoResult5 = (ImageView) findViewById(R.id.iv_picasso_result5);
        ivPicassoResult6 = (ImageView) findViewById(R.id.iv_picasso_result6);

        btPicassoBase.setOnClickListener(this);
        btPicassoListview.setOnClickListener(this);
        btPicassoTranformations.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btPicassoBase) {
            // 基本用法
            picassoBaseUse();
        } else if (v == btPicassoListview) {
            // 跳转到listview页面
            Intent intent = new Intent(MainActivity.this, PicassoListviewActivity.class);

            startActivity(intent);
        } else if (v == btPicassoTranformations) {
            // 跳转到图片转换页面
            Intent intent = new Intent(MainActivity.this, PicassoTransfromationsActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Picasso基本用法
     */
    private void picassoBaseUse() {
        // 普通加载图片
        Picasso.with(MainActivity.this)
                .load("http://n.sinaimg.cn/translate/20160819/9BpA-fxvcsrn8627957.jpg")
                .into(ivPicassoResult1);

        // 裁剪的方式加载图片
        Picasso.with(MainActivity.this)
                .load("http://n.sinaimg.cn/translate/20160819/9BpA-fxvcsrn8627957.jpg")
                .resize(100, 100)
                .into(ivPicassoResult2);

        // 选择180度
        Picasso.with(MainActivity.this)
                .load("http://n.sinaimg.cn/translate/20160819/9BpA-fxvcsrn8627957.jpg")
                .rotate(180)
                .into(ivPicassoResult3);

        // load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
        Picasso.with(MainActivity.this)
                .load("file://+ Environment.getExternalStorageDirectory().getPath()+/test.jpg")
                .placeholder(R.mipmap.ic_launcher)//设置资源加载过程中的显示的Drawable
                .error(R.mipmap.ic_launcher)//设置load失败时显示的Drawable。
                .into(ivPicassoResult4);

        // load assets资源：load("file:///android_asset/f003.gif")
        Picasso.with(MainActivity.this)
                .load("file:///android_asset/f003.gif")
                .placeholder(R.mipmap.ic_launcher)//设置资源加载过程中的显示的Drawable
                .error(R.mipmap.ic_launcher)//设置load失败时显示的Drawable。
                .into(ivPicassoResult5);

        // load drawable资源：load("android.resource://com.frank.glide/drawable/news")
        Picasso.with(MainActivity.this)
                .load(R.drawable.demo)
                .placeholder(R.mipmap.ic_launcher)//设置资源加载过程中的显示的Drawable
                .error(R.mipmap.ic_launcher)//设置load失败时显示的Drawable。
                .into(ivPicassoResult6);

    }

}
