package com.example.administrator.devicestest.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Administrator
 * Time: 2017/6/8  10:00
 * Description：
 */
public class SmsActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSmsPhone();
//        getSmsInPhone();
    }

    private void getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";
        ContentResolver cr = getContentResolver();
        String[] projection = new String[]{"_id", "address", "person",
                "body", "date", "type"};
        Uri uri = Uri.parse(SMS_URI_ALL);
        Cursor cur = cr.query(uri, projection, null, null, "date desc");

        if (cur.moveToFirst()) {
            String phoneNumber;
            String smsbody;
            String date;
            String type;
            String moblie = "";

            int nameColumn = cur.getColumnIndex("person");
            int phoneNumberColumn = cur.getColumnIndex("address");
            int smsbodyColumn = cur.getColumnIndex("body");
            int dateColumn = cur.getColumnIndex("date");
            int typeColumn = cur.getColumnIndex("type");


            do {

                phoneNumber = cur.getString(phoneNumberColumn);
                smsbody = cur.getString(smsbodyColumn);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                date = dateFormat.format(d);

                String number = cur.getString(cur.getColumnIndex("address"));//手机号
                String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
                String body = cur.getString(cur.getColumnIndex("body"));
                //这里我是要获取自己短信服务号码中的验证码~~
                Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    moblie = matcher.group().substring(1, 11);
                }

                int typeId = cur.getInt(typeColumn);
                if (typeId == 1) {
                    type = "接收";
                } else if (typeId == 2) {
                    type = "发送";
                } else {
                    type = "";
                }

                if (smsbody == null) smsbody = "";
                String smsOne = "类型：" + type + ", 称呼：" + name + ", 号码："
                        + number + ",时间:" + date + "，短信内容：" + body + ",验证码:" + moblie
                        + "\n---------------------\n";
                Log.i("TAG", "短信--->" + smsOne);
            } while (cur.moveToNext());


        }


    }

    public String getSmsPhone() {
        final String SMS_URI_ALL = "content://sms/"; // 所有短信
        final String SMS_URI_INBOX = "content://sms/inbox"; // 收件箱
        final String SMS_URI_SEND = "content://sms/sent"; // 已发送
        final String SMS_URI_DRAFT = "content://sms/draft"; // 草稿
        final String SMS_URI_OUTBOX = "content://sms/outbox"; // 发件箱
        final String SMS_URI_FAILED = "content://sms/failed"; // 发送失败
        final String SMS_URI_QUEUED = "content://sms/queued"; // 待发送列表

        StringBuilder smsBuilder = new StringBuilder();

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type",};
            Cursor cur = getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信
            // 获取短信中最新的未读短信
            // Cursor cur = getContentResolver().query(uri, projection,
            // "read = ?", new String[]{"0"}, "date desc");
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");

                do {
                    String strAddress = cur.getString(index_Address);
                    String intPerson = cur.getString(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else if (intType == 3) {
                        strType = "草稿";
                    } else if (intType == 4) {
                        strType = "发件箱";
                    } else if (intType == 5) {
                        strType = "发送失败";
                    } else if (intType == 6) {
                        strType = "待发送列表";
                    } else if (intType == 0) {
                        strType = "所以短信";
                    } else {
                        strType = "null";
                    }

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(strDate + ", ");
                    smsBuilder.append(strType);
                    smsBuilder.append(" ]\n\n");

                    String smsOne = "类型：" + strType + ", 称呼：" + intPerson + ", 号码："
                            + strAddress + ",时间:" + strDate + "，短信内容：" + strbody
                            + "\n---------------------\n";
                    Log.i("TAG", "短信--->" + smsOne);
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            }

            smsBuilder.append("getSmsInPhone has executed!");

        } catch (SQLiteException ex) {
            Log.d("TAG", "短信" + ex.getMessage());
        }

        return smsBuilder.toString();
    }
}

