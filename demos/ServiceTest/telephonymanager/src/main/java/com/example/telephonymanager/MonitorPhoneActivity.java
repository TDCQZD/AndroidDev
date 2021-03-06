package com.example.telephonymanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
*监听手机来电
*@author ZD
*created at 2017/8/7 15:44
*description：
*/
public class MonitorPhoneActivity extends AppCompatActivity {
    TelephonyManager tManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_phone);

        // 取得TelephonyManager对象
        tManager = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        // 创建一个通话状态监听器
        PhoneStateListener listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String number) {
                switch (state) {
                    // 无任何状态
                    case TelephonyManager.CALL_STATE_IDLE:
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    // 来电铃响时
                    case TelephonyManager.CALL_STATE_RINGING:
                        OutputStream os = null;
                        try {
                            os = openFileOutput("phoneList", MODE_APPEND);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        PrintStream ps = new PrintStream(os);
                        // 将来电号码记录到文件中
                        ps.println(new Date() + " 来电：" + number);
                        ps.close();
                        break;
                    default:
                        break;
                }
                super.onCallStateChanged(state, number);
            }
        };
        // 监听电话通话状态的改变
        tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

    }
}
