package com.example.administrator.runningpermissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 申请多个权限
 * 注：做动态权限处理时，必须在AndroidManifest进行注册
 */
public class MorePermissionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_permission;

    //权限请求码
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_READ_CALENDAR = 1;
    public static final int CODE_WRITE_CALENDAR = 2;
    public static final int CODE_CAMERA = 3;
    public static final int CODE_READ_CONTACTS = 4;
    public static final int CODE_WRITE_CONTACTS = 5;
    public static final int CODE_GET_ACCOUNTS = 6;
    public static final int CODE_ACCESS_COARSE_LOCATION = 7;
    public static final int CODE_ACCESS_FINE_LOCATION = 8;
    public static final int CODE_CALL_PHONE = 9;
    public static final int CODE_READ_PHONE_STATE = 10;
    public static final int CODE_READ_CALL_LOG = 11;
    public static final int CODE_WRITE_CALL_LOG = 12;
    public static final int CODE_USE_SIP = 13;
    public static final int CODE_PROCESS_OUTGOING_CALLS = 14;
    public static final int CODE_BODY_SENSORS = 15;
    public static final int CODE_SEND_SMS = 16;
    public static final int CODE_RECEIVE_SMS = 17;
    public static final int CODE_READ_SMS = 18;
    public static final int CODE_RECEIVE_WAP_PUSH = 19;
    public static final int CODE_RECEIVE_MMS = 20;
    public static final int CODE_READ_EXTERNAL_STORAGE = 21;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 22;

    public static final int CODE_MULTI_PERMISSION = 23;
    //权限
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;//麦克风
    public static final String PERMISSION_READ_CALENDAR = Manifest.permission.READ_CALENDAR;//日历
    public static final String PERMISSION_WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;//相机
    public static final String PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS;//联系人
    public static final String PERMISSION_WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;//位置
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;//手机
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String PERMISSION_WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String PERMISSION_USE_SIP = Manifest.permission.USE_SIP;
    public static final String PERMISSION_PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String PERMISSION_BODY_SENSORS = Manifest.permission.BODY_SENSORS;//传感器
    public static final String PERMISSION_SEND_SMS = Manifest.permission.SEND_SMS;//SMS（短信）
    public static final String PERMISSION_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String PERMISSION_READ_SMS = Manifest.permission.READ_SMS;
    public static final String PERMISSION_RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String PERMISSION_RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;//存储卡
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //权限集
    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO, PERMISSION_READ_CALENDAR, PERMISSION_WRITE_CALENDAR,
            PERMISSION_CAMERA, PERMISSION_READ_CONTACTS,
            PERMISSION_WRITE_CONTACTS, PERMISSION_GET_ACCOUNTS, PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_ACCESS_FINE_LOCATION, PERMISSION_CALL_PHONE, PERMISSION_READ_PHONE_STATE,
            PERMISSION_READ_CALL_LOG, PERMISSION_WRITE_CALL_LOG, PERMISSION_USE_SIP,
            PERMISSION_PROCESS_OUTGOING_CALLS, PERMISSION_BODY_SENSORS, PERMISSION_SEND_SMS,
            PERMISSION_RECEIVE_SMS, PERMISSION_READ_SMS, PERMISSION_RECEIVE_WAP_PUSH,
            PERMISSION_RECEIVE_MMS, PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        bt_permission = (Button) findViewById(R.id.bt_permission);
        bt_permission.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_permission:
                applyPermission();
                break;
        }
    }

    /**
     * 动态权限申请
     */
    private void applyPermission() {

        if (ContextCompat.checkSelfPermission(this,//检测某个权限是否已经被授予
                Manifest.permission.CALL_PHONE)//权限
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                Toast.makeText(this, "你曾经拒绝过此权限,需要重新获取", Toast.LENGTH_SHORT).show();
            } else {
                // 没有权限，申请权限。
                Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        requestPermissions,//申请权限，可申请多个权限
                        CODE_SEND_SMS);//权限请求码
            }
        } else {
            //权限已被授予，执行操作
            callPhone();
        }
    }

    /**
     * 业务处理
     */
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);
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
            case CODE_RECORD_AUDIO: {
                grantResult(grantResults);
                return;
            }
            case CODE_READ_CALENDAR: {
                grantResult(grantResults);
                return;
            }
            case CODE_WRITE_CALENDAR: {
                grantResult(grantResults);
                return;
            }
            case CODE_CAMERA: {
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
            case CODE_GET_ACCOUNTS: {
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
            case CODE_CALL_PHONE: {
                grantResult(grantResults);
                return;
            }
            case CODE_READ_PHONE_STATE: {
                grantResult(grantResults);
                return;
            }
            case CODE_READ_CALL_LOG: {
                grantResult(grantResults);
                return;
            }
            case CODE_WRITE_CALL_LOG: {
                grantResult(grantResults);
                return;
            }
            case CODE_USE_SIP: {
                grantResult(grantResults);
                return;
            }
            case CODE_PROCESS_OUTGOING_CALLS: {
                grantResult(grantResults);
                return;
            }
            case CODE_BODY_SENSORS: {
                grantResult(grantResults);
                return;
            }
            case CODE_SEND_SMS: {
                grantResult(grantResults);
                return;
            }
            case CODE_RECEIVE_SMS: {
                grantResult(grantResults);
                return;
            }
            case CODE_READ_SMS: {
                grantResult(grantResults);
                return;
            }
            case CODE_RECEIVE_WAP_PUSH: {
                grantResult(grantResults);
                return;
            }
            case CODE_RECEIVE_MMS: {
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






