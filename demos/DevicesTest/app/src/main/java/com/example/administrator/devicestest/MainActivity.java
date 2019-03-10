package com.example.administrator.devicestest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.devicestest.activity.CallRecordsActivity;
import com.example.administrator.devicestest.activity.ContactsActivity;
import com.example.administrator.devicestest.activity.DevicesInfoActivity;
import com.example.administrator.devicestest.activity.SmsActivityActivity;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btDevicesInfo;
    private Button btContacts;
    private Button btCallRecords;
    private Button btSms;
    //请求码
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final int CODE_READ_CONTACTS = 9;
    public static final int CODE_WRITE_CONTACTS = 10;
    public static final int CODE_READ_CALL_LOG = 11;
    public static final int CODE_READ_SMS = 12;
    public static final int CODE_RECEIVE_SMS = 13;


    public static final int CODE_MULTI_PERMISSION = 100;
    //权限
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String PERMISSION_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String PERMISSION_READ_SMS = Manifest.permission.READ_SMS;
    public static final String PERMISSION_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    private static final int REQUEST_CONTACTS = 16;

    //权限集
    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_WRITE_CONTACTS,
            PERMISSION_READ_CONTACTS,
            PERMISSION_READ_CALL_LOG,
            PERMISSION_READ_SMS,
            PERMISSION_RECEIVE_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applyPermission();
        findViews();
    }


    private void findViews() {
        btDevicesInfo = (Button) findViewById(R.id.bt_devices_info);
        btContacts = (Button) findViewById(R.id.bt_contacts);
        btCallRecords = (Button) findViewById(R.id.bt_call_records);
        btSms = (Button) findViewById(R.id.bt_sms);


        btDevicesInfo.setOnClickListener(this);
        btContacts.setOnClickListener(this);
        btCallRecords.setOnClickListener(this);
        btSms.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == btDevicesInfo) {
            startActivity(new Intent(MainActivity.this, DevicesInfoActivity.class));
        } else if (v == btContacts) {
            startActivity(new Intent(MainActivity.this, ContactsActivity.class));
        } else if (v == btCallRecords) {
            startActivity(new Intent(MainActivity.this, CallRecordsActivity.class));
        } else if (v == btSms) {
            startActivity(new Intent(MainActivity.this, SmsActivityActivity.class));
        }
    }

    /**
     * 动态权限申请
     */
    private void applyPermission() {

        if (ContextCompat.checkSelfPermission(this,//检测某个权限是否已经被授予
                Manifest.permission.CALL_PHONE)//权限
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CONTACTS)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                Toast.makeText(this, "你曾经拒绝过此权限,需要重新获取", Toast.LENGTH_SHORT).show();
            } else {
                // 没有权限，申请权限。
                Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        requestPermissions,//申请权限，可申请多个权限
                        REQUEST_CONTACTS);//权限请求码
            }
        } else {
            //权限已被授予，执行操作

        }
    }


    /**
     * 处理权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case CODE_CAMERA: {

                grantResult(grantResults);
                return;
            }
            case CODE_CALL_PHONE: {

                grantResult(grantResults);
                return;
            }
            case CODE_ACCESS_COARSE_LOCATION: {

                grantResult(grantResults);
                return;
            }
            case CODE_ACCESS_FINE_LOCATION: {

                grantResult(grantResults);
                return;
            }
            case CODE_GET_ACCOUNTS: {

                grantResult(grantResults);
                return;
            }
            case CODE_READ_PHONE_STATE: {

                grantResult(grantResults);
                return;
            }
            case CODE_RECORD_AUDIO: {

                grantResult(grantResults);
                return;
            }
            case CODE_READ_EXTERNAL_STORAGE: {

                grantResult(grantResults);
                return;
            }
            case CODE_WRITE_EXTERNAL_STORAGE: {

                grantResult(grantResults);
                return;
            }
            case CODE_READ_CONTACTS: {

                grantResult(grantResults);
                return;
            }
            case CODE_WRITE_CONTACTS: {

                grantResult(grantResults);
                return;
            }
            case CODE_READ_CALL_LOG: {

                grantResult(grantResults);
                return;
            }
            case CODE_READ_SMS: {

                grantResult(grantResults);
                return;
            }
            case CODE_RECEIVE_SMS: {

                grantResult(grantResults);
                return;
            }
        }
    }


    /**
     * 处理权限申请回调
     *
     * @param grantResults
     */
    private void grantResult(int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 用户同意申请权限，执行操作
            Toast.makeText(this, "用户同意申请权限", Toast.LENGTH_SHORT).show();

        } else {
            // 用户拒绝申请权限
            Toast.makeText(this, "用户拒绝申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
