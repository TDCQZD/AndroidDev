package com.example.administrator.dialogtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.dialogtest.util.CustomDialog;

/**
 * Created by Administrator on 2017/3/20.
 * 自定义对话框
 * 第一步: 给Dialog设置一个风格主题（基本都是用这个主题）无边框全透明背景：
 * 第二步：给自定的Dialog设置自定义的 xml界面
 * 第三步：继承Dialog实现自定义的Dialog
 * 最后，自定义的dialog中包含了一些按钮的时候，这个时候要想让按钮有点击事件，
 * 并且把这个点击事件能够传递给activity，让acitvity做一些事情，这里就需要设置监听接口，让button的点击事件能够让外界activity知道。
 */

public class CustomDialogActivity extends Activity {

    private CustomDialog customDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        findViewById(R.id.custom_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = new CustomDialog(CustomDialogActivity.this);
                customDialog.setTitle("提示");
                customDialog.setMessage("确定退出应用?");
                customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Toast.makeText(CustomDialogActivity.this, "点击了--确定--按钮", Toast.LENGTH_LONG).show();
                        customDialog.dismiss();
                    }
                });
                customDialog.setNoOnclickListener("取消", new CustomDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(CustomDialogActivity.this, "点击了--取消--按钮", Toast.LENGTH_LONG).show();
                        customDialog.dismiss();
                    }
                });
                customDialog.show();

            }
        });
    }
}
