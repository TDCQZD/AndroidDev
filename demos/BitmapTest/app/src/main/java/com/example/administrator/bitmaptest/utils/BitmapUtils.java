package com.example.administrator.bitmaptest.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bitmap工具类
 *
 * @author ZD
 *         created at 2017/8/1 16:19
 *         description：
 */

public class BitmapUtils {


    /**
     * 将Bitmap保存为本地文件
     * @param filePath
     * @param b
     * @param quality 质量 最大100
     */
    public static void writeBitmapToFile(String filePath, Bitmap b, int quality) {
        try {
            File desFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(desFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            b.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//保存图片
    public static void saveBitmap(Bitmap bitmap,String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    view转Bitmap
     */
    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    /*
    将控件转换为bitmap
     */
    public static Bitmap convertViewToBitMap(View view) { // 打开图像缓存
        view.setDrawingCacheEnabled(true); // 必须调用measure和layout方法才能成功保存可视组件的截图到png图像文件
        // 测量View大小
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        // 发送位置和尺寸到View及其所有的子View
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 获得可视组件的截图
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) bgDrawable.draw(canvas);
        else canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }


    /*
    Drawable转化成Bitmap
     */

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /*
    Bitmap转换成Drawable
     */
    public static Drawable bitmapToDrawable(Resources resources, Bitmap bm) {
        Drawable drawable = new BitmapDrawable(resources, bm);
        return drawable;
    }

    /*
    Bitmap转换成byte[]
     */
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /*
    byte[]转换成Bitmap
     */
//    Bitmap bitmap = BitmapFactory.decodeByteArray(
//    byte,0,b.length);

    /*
    InputStream转换成Bitmap
     */
//    InputStream is = getResources().openRawResource(id);
//    Bitmap bitmap = BitmaoFactory.decodeStream(is);

    /*
    InputStream转换成byte[]
     */
//    InputStream is = getResources().openRawResource(id);//也可以通过其他方式接收一个InputStream对象
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    byte[] b = new byte[1024 * 2];
//    int len = 0;
//     while((len =is.read(b,0,b.length))!=-1)
//
//    {
//        baos.write(b, 0, len);
//        baos.flush();
//    }
//    byte[] bytes = baos.toByteArray();


    /**
     * 图片缩放
     *
     * @param bitmap
     * @param scaleWidht  宽缩小的比例
     * @param scaleHeight 高缩小的比例
     * @return
     */
    public static Bitmap scaleMatrixBitmap(Bitmap bitmap, float scaleWidht, float scaleHeight) {

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return newbmp;
    }

    /**
     * @param mBitmap
     * @param degree  指定旋转角度
     * @return
     */
    public static Bitmap rotateMatrixBitmap(Bitmap mBitmap, final int degree) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(degree);
        Bitmap mRotateBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
        return mRotateBitmap;
//        if (width > height) {
//            Matrix matrix = new Matrix();
//            matrix.postRotate(degree);
//            if (mBitmap != null && !mBitmap.isRecycled()) {
//                Bitmap resizedBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
//                return resizedBitmap;
//            } else {
//                return null;
//            }
//        } else {
//            return mBitmap;
//        }
    }


    /*
    图片圆角处理
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    //图片圆角处理
    public static Bitmap getRoundedBitmap(Bitmap mBitmap, float roundPx) {

        //创建新的位图
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //把创建的位图作为画板
        Canvas mCanvas = new Canvas(bgBitmap);

        Paint mPaint = new Paint();
        Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF mRectF = new RectF(mRect);
        //设置圆角半径为20
//        float roundPx = 15;
        mPaint.setAntiAlias(true);
        //先绘制圆角矩形
        mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);

        //设置图像的叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制图像
        mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

        return bgBitmap;
    }

    /*
    图片裁剪
     */
    public static Bitmap clipBitmap(Bitmap mBitmap, int reqWidth, int reqHeight) {

        if (mBitmap.getWidth() > reqWidth && mBitmap.getHeight() > reqHeight) {
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, reqWidth, reqHeight);
        } else {
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        }
        return mBitmap;
    }
    //图片灰化处理
    public static Bitmap getGrayBitmap(Bitmap mBitmap) {

        Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mGrayBitmap);
        Paint mPaint = new Paint();

        //创建颜色变换矩阵
        ColorMatrix mColorMatrix = new ColorMatrix();
        //设置灰度影响范围
        mColorMatrix.setSaturation(0);
        //创建颜色过滤矩阵
        ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
        //设置画笔的颜色过滤矩阵
        mPaint.setColorFilter(mColorFilter);
        //使用处理后的画笔绘制图像
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

        return mGrayBitmap;
    }
    //图片倾斜
    public static Bitmap getScrewBitmap(Bitmap mBitmap,float kx,float ky) {


        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
//        matrix.preSkew(1.0f, 0.15f);
        matrix.preSkew(kx, ky);
        Bitmap mScrewBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

        return mScrewBitmap;
    }
    //图片倒影
    public static Bitmap getReflectedBitmap( Bitmap mBitmap) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        // 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
        matrix.preScale(1, -1);

        //创建反转后的图片Bitmap对象，图片高是原图的一半。
        //Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, height/2, width, height/2, matrix, false);
        //创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
        //注意两种createBitmap的不同
        //Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height*3/2, Config.ARGB_8888);

        Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
        Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height*2, Bitmap.Config.ARGB_8888);

        // 把新建的位图作为画板
        Canvas mCanvas = new Canvas(mReflectedBitmap);
        //绘制图片
        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        mCanvas.drawBitmap(mInverseBitmap, 0, height, null);

        //添加倒影的渐变效果
        Paint mPaint = new Paint();
        Shader mShader = new LinearGradient(0, height, 0, mReflectedBitmap.getHeight(), 0x70ffffff, 0x00ffffff, Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        //设置叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //绘制遮罩效果
        mCanvas.drawRect(0, height, width, mReflectedBitmap.getHeight(), mPaint);

        return mReflectedBitmap;
    }

    //提取图像Alpha位图
    public static  Bitmap getAlphaBitmap( Bitmap mBitmap) {

//        BitmapDrawable mBitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.enemy_infantry_ninja);
//        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        //注意这两个方法的区别
//        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(Color.GRAY);
        //从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }





}
