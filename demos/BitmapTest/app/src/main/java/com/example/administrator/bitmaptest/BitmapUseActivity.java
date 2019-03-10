package com.example.administrator.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bitmaptest.utils.BitmapUtils;

public class BitmapUseActivity extends AppCompatActivity {
    private ImageView iv, iv1;

    private Bitmap bitmap = null, defaultBitmapMap = null;
    private BitmapFactory.Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bitmap_use);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        //设置标题
        textView.setText("Bitmap图片处理");
        iv = (ImageView) findViewById(R.id.iv);
        iv1 = (ImageView) findViewById(R.id.iv1);

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mei);//普通加载
//
//        iv.setImageBitmap(bitmap);
        initData();//加载图片
    }

    private void initData() {
        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.mei, options);
        calculateSampleSize(options, 300, 300);
        options.inJustDecodeBounds = false;

        try {//捕获OutOfMemory异常
            // 实例化Bitmap
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mei, options);
        } catch (OutOfMemoryError e) {

        }
        if (bitmap == null) {
        }


        iv.setImageBitmap(bitmap);
    }

    /*
    图片缩放
     */
    public void scaleImage(View view) {

        bitmap = BitmapUtils.scaleMatrixBitmap(bitmap, 0.5f, 0.5f);//大于1，放大 ;小于1 ，缩小  。
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片旋转
     */
    public void rotateImage(View view) {
        bitmap = BitmapUtils.rotateMatrixBitmap(bitmap, 350);//0-359 。
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片裁剪
     */
    public void cutImage(View view) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Log.i("TAG", "height:" + height + "\n" + "width:" + width);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, 300, 150);
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片合成
     */
    public void compositeImage(View view) {
//        Bitmap bm = createStarBitmap(3.5f, 5);
//        iv1.setImageBitmap(bm);

/*
  第一步，创建一个新位图作为画板，然后把原图像画到新位图上面
 */
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
/*
  第二步，绘制一个剪切区域，比如要剪切人物的脸部区域，需要在指定的位置绘制一个圆角矩形区域，
  代码中的坐标是在调试中获得，在其他分辨率下会有所不同
 */
        int deltX = 76;
        int deltY = 98;
        DashPathEffect dashStyle = new DashPathEffect(new float[] { 10, 5,        5, 5 }, 2);//创建虚线边框样式
        RectF faceRect = new RectF(0, 0, 88, 106);
        float [] faceCornerii = new float[] {30,30,30,30,75,75,75,75};
//        Paint mPaint = new Paint();//创建画笔
        mPaint.setColor(0xFF6F8DD5);
        mPaint.setStrokeWidth(6);
        mPaint.setPathEffect(dashStyle);
        Path clip = new Path();//创建路径
        clip.reset();
        clip.addRoundRect(faceRect, faceCornerii, Path.Direction.CW);//添加圆角矩形路径
        canvas.save();//保存画布
        canvas.translate(deltX, deltY);
        canvas.clipPath(clip, Region.Op.DIFFERENCE);
        canvas.drawColor(0xDF222222);
        canvas.drawPath(clip, mPaint);//绘制路径
        canvas.restore();
        /*
 第三步，从原图像上获取指定区域的图像，并绘制到屏幕上
         */
        Rect srcRect = new Rect(0, 0, 88, 106);
        srcRect.offset(deltX, deltY);
        PaintFlagsDrawFilter dfd = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG,
                Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(dfd);
        canvas.clipPath(clip);//使用路径剪切画布
        canvas.drawBitmap(bitmap, srcRect, faceRect, mPaint);

    }

    /*
    图片倾斜
     */
    public void ScrewBitmap(View view) {
        bitmap = BitmapUtils.getScrewBitmap(bitmap, 1.0f, 0.15f);
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片倒影
     */
    public void ReflectedBitmap(View view) {

        bitmap = BitmapUtils.getReflectedBitmap(bitmap);
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片圆角处理
     */
    public void RoundedBitmap(View view) {
        bitmap = BitmapUtils.getRoundedBitmap(bitmap, 100);
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片灰化
     */
    public void GrayBitmap(View view) {
        bitmap = BitmapUtils.getGrayBitmap(bitmap);
        iv1.setImageBitmap(bitmap);
    }

    /*
    提取图像Alpha位图
     */
    public void AlphaBitmap(View view) {
        bitmap = BitmapUtils.getAlphaBitmap(bitmap);
        iv1.setImageBitmap(bitmap);
    }

    /*
    图片保存
     */
    public void SaveBitmap(View view) {
        String path = "/sdcard/Download/mmm.jpg";
        BitmapUtils.saveBitmap(bitmap,path);
    }

    /*
    Bitmap转换成drawable
     */
    public void BitToDra(View view) {
        Drawable newBitmapDrawable = new BitmapDrawable(bitmap);
        //        bitmap=mBitmapDrawable.getBitmap();
        bitmap= BitmapUtils.drawableToBitmap(newBitmapDrawable);
        iv1.setImageBitmap(bitmap);
    }

    /*
    drawable转换成Bitmap
     */
    public void DraToBit(View view) {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.m8);
       bitmap= BitmapUtils.drawableToBitmap(mBitmapDrawable);
        iv1.setImageBitmap(bitmap);
    }

    /*
    View转换为Bitmap
     */
    public void ViewToBit(View view) {

        bitmap = BitmapUtils.convertViewToBitmap(iv,bitmap.getWidth(),bitmap.getHeight());
        iv1.setImageBitmap(bitmap);
    }

    // 计算合适的采样率，height、width为期望的图片大小，单位是px
    private int calculateSampleSize(BitmapFactory.Options options, int width, int height) {

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;
        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }
        options.inSampleSize = inSampleSize;
        return inSampleSize;
    }

    private Bitmap createStarBitmap(float grade, int maxGrade) {
        Bitmap empty_star = BitmapFactory.decodeResource(getResources(), R.drawable.m1); // 空星
        Bitmap normal_star = BitmapFactory.decodeResource(getResources(), R.drawable.m2); // 实星
        Bitmap half_star = BitmapFactory.decodeResource(getResources(), R.drawable.m7);
        ; // 半星
        int star_width = empty_star.getWidth();
        int star_height = empty_star.getHeight();
        Bitmap newb = Bitmap.createBitmap(star_width * 5, star_height, Bitmap.Config.ARGB_8888);// 创建一个底层画布
        Canvas cv = new Canvas(newb);
        for (int i = 0; i < maxGrade; i++) {
            if (i < grade && i + 1 > grade) // 画半星
            {
                cv.drawBitmap(half_star, star_width * i, 0, null);// 画图片的位置
            } else if (i < grade) // 画实心
            {
                cv.drawBitmap(normal_star, star_width * i, 0, null);// 画图片的位置
            } else
            // 画空心
            {
                cv.drawBitmap(empty_star, star_width * i, 0, null);// 画图片的位置
            }
        }
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

}
