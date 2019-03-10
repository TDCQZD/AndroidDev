package com.example.administrator.runningpermissiontest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限封装库
 * EasyPermisssion
 * PermissionDispatcher
 * <p>
 * 使用EasyPermisssion库申请权限步骤
 * 添加jar包
 * 1 检查权限
 * 2 申请权限
 * 3 实现EasyPermissions.PermissionCallbacks接口，直接处理权限是否成功申请
 */
public class EasyPermisssionActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_AND_WIFI = 0;
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
                applyPermission();
                break;
        }
    }

    private void applyPermission() {
        // 检查权限
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //业务处理
            callPhone();

        } else {
            //申请权限
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
                    RC_CAMERA_AND_WIFI, perms);
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

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
