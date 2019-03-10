package com.example.handwrite;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView mIVSign;
    private TextView mTVSign;
    private Bitmap mSignBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIVSign = (ImageView) findViewById(R.id.iv_sign);
        mTVSign = (TextView) findViewById(R.id.tv_sign);

        mTVSign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                WritePadDialog mWritePadDialog = new WritePadDialog(
                        MainActivity.this, new WriteDialogListener() {

                    @Override
                    public void onPaintDone(Object object) {
                        mSignBitmap = (Bitmap) object;
//                        createSignFile();
                        mIVSign.setImageBitmap(mSignBitmap);
                        String BaseToString = BitmapAndStringUtils.convertIconToString(mSignBitmap);
                        Log.i("TAG", "图片转换String>>>>>>>"+BaseToString);
                        mTVSign.setVisibility(View.GONE);
                    }
                });

                mWritePadDialog.show();
            }
        });
    }

    //创建签名文件
    private void createSignFile() {
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        String path = null;
        File file = null;
        try {
            path = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".jpg";
            file = new File(path);
            fos = new FileOutputStream(file);
            baos = new ByteArrayOutputStream();
            //如果设置成Bitmap.compress(CompressFormat.JPEG, 100, fos) 图片的背景都是黑色的
            mSignBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            if (b != null) {
                fos.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
