package com.example.administrator.devicestest.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.devicestest.utils.CallRecordsUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.administrator.devicestest.utils.CallRecordsUtil.getCallHistoryList;

/**
 * Author: Administrator
 * Time: 2017/6/8  9:59
 * Description：
 */
public class CallRecordsActivity extends AppCompatActivity {

    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callRecord();
//        ContentResolver ccr = getContentResolver();
//        String s = CallRecordsUtil.getCallHistoryList(context,ccr);
//        Log.i("TAG", "" + s);
    }

    private void callRecord() {
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {

                //号码
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                //呼叫类型
                String type;
                switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        type = "未接";
                        break;
                    default:
                        type = "挂断";//应该是挂断.根据我手机类型判断出的
                        break;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                //呼叫时间
                String time = sfd.format(date);
                //联系人
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                //通话时间,单位:s
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
                Log.i("CallRecordsActivity", "callRecord()--->" + "\n" + "通话类型： " + type + "->呼叫时间： " + time + "->通话号码： " + number + "->联系人： " + name + "->通话时长： " + duration);
            } while (cursor.moveToNext());
        }
    }

}