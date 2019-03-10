package com.example.administrator.androidutillists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.androidutillists.Utils.ActionSheetDialog;
import com.example.administrator.androidutillists.Utils.MyEditText;
import com.example.administrator.androidutillists.Utils.ZProgressHUD;

import java.util.ArrayList;

/**
*Util测试使用
*@author ZD
*created at 2017/7/10 15:30
*description：
*/
public class MainActivity extends AppCompatActivity {
    private ZProgressHUD progressHUD;
    private ArrayList stringList = new ArrayList(){

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void customDialog(View view) {
        progressHUD = ZProgressHUD.getInstance(MainActivity.this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
//        progressHUD.dismiss();
    }

    public void ActionSheetDialog(View view) {

        ActionSheetDialog dialog = new ActionSheetDialog(this).builder();
        dialog.setTitle("选择发送地址");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.addShemItems(stringList, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {

                String account = stringList.get(which - 1).toString();

            }
        });
        dialog.show();

//        new ActionSheetDialog(MainActivity.this).builder()
//                .setTitle("验签方式")
//                .setCancelable(false)
//                .setCanceledOnTouchOutside(false)
//                .addSheetItem("本地", ActionSheetDialog.SheetItemColor.FEN, new ActionSheetDialog.OnSheetItemClickListener() {
//                    @Override
//                    public void onClick(int which) {
//
//                    }
//                })
//                .addSheetItem("网络", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
//                    @Override
//                    public void onClick(int which) {
//
//                    }
//                })
//                .show();

    }
    public void EditText(){
        MyEditText edit=new MyEditText(MainActivity.this);
    }
}