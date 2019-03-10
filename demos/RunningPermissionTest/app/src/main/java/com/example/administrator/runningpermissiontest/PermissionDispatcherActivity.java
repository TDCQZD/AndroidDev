package com.example.administrator.runningpermissiontest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author ZD
 * created at 2017/6/8 16:43
 * description：
 */

/**
 * @NeedsPermission 当申请的权限被用户允许后，调用此方法。
 * @OnShowRationale 当第一次申请权限时，用户选择拒绝，再次申请时调用此方法，在此方法中提示用户为什么需要这个权限。
 * @OnPermissionDenied 当申请的权限被用户拒绝后，调用此方法
 * @OnNeverAskAgain 当用户点击不再询问后，调用此方法。
 * <p>
 * 方法名：权限申请成功，执行操作
 */
/*
PermissionDispatcher库使用说明：
1、添加Jar
  使用PermissionDispatcher库添加:
    compile('com.github.hotchemi:permissionsdispatcher:2.4.0') {
        // if you don't use android.app.Fragment you can exclude support for them
        exclude module: "support-v13"
    }
    annotationProcessor 'com.github.hotchemi:permissionsdispatcher-processor:2.4.0'
   2、 插件安装
   在Android Studio中选择File——Setting——Plugins，搜索PermissionsDispatcher，点击install安装
   3、使用
   在Android Studio中选择Code——Generate——Generate Runtime Permissions
 */
@RuntimePermissions
public class PermissionDispatcherActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_permission;

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
                PermissionDispatcherActivityPermissionsDispatcher.callPhoneWithCheck(this);
                break;
        }
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS})
    void callPhone() {//权限申请成功，执行操作
        Toast.makeText(PermissionDispatcherActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionDispatcherActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS})
    void ShowRationale(final PermissionRequest request) {//当第一次申请权限时，用户选择拒绝，再次申请时调用此方法，在此方法中提示用户为什么需要这个权限。
        new AlertDialog.Builder(this)
                .setMessage("申请相机权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //再次执行请求
                        request.proceed();
                    }
                })
                .show();

    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS})
    void PermissionDenied() {//当申请的权限被用户拒绝后，调用此方法
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS})
    void NeverAskAgain() {//当用户点击不再询问后，调用此方法
        Toast.makeText(this, "不再询问", Toast.LENGTH_SHORT).show();
    }
}
