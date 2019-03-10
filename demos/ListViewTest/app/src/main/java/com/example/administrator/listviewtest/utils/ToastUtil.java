package com.example.administrator.listviewtest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast类
 */
public class ToastUtil {

    public static Toast toast;

    /**
     * 能够连续弹吐司，不用等上个消失
     * @param context
     * @param string
     */
    public static void showToast(Context context, String string){
        if(toast==null){
            toast= Toast.makeText(context,string, Toast.LENGTH_LONG);
        }
        toast.setText(string);
        toast.show();
    }
}