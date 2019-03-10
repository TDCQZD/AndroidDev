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
 * 申请单个权限
 */
public class OnePermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_permission;
    // 打电话权限申请的请求码
    private static final int CALL_PHONE_REQUEST_CODE = 0x0011;

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
                callPhoneTest();
                break;
        }
    }

    private void callPhoneTest() {

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
                        new String[]{Manifest.permission.CALL_PHONE},//申请权限，可申请多个权限
                       CALL_PHONE_REQUEST_CODE);//权限请求码
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

            case CALL_PHONE_REQUEST_CODE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 用户同意申请权限，执行操作
                    Toast.makeText(this, "用户同意申请权限", Toast.LENGTH_SHORT).show();
                    callPhone();
                } else {
                    // 用户拒绝申请权限
                    Toast.makeText(this, "用户拒绝申请权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


}
