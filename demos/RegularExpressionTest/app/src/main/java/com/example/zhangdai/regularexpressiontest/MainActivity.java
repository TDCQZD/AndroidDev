package com.example.zhangdai.regularexpressiontest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etInput;
    private Button btVerUsername;
    private Button btVerPwd;
    private Button btVerTell;
    private Button btVerEmail;
    private Button btVerChinese;
    private Button btVerUrl;
    private Button btVerIp;
    private Button btVerId;
    private String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();


    }

    private void findViews() {
        etInput = (EditText) findViewById(R.id.et_input);
        btVerUsername = (Button) findViewById(R.id.bt_ver_username);
        btVerPwd = (Button) findViewById(R.id.bt_ver_pwd);
        btVerTell = (Button) findViewById(R.id.bt_ver_tell);
        btVerEmail = (Button) findViewById(R.id.bt_ver_email);
        btVerChinese = (Button) findViewById(R.id.bt_ver_chinese);
        btVerUrl = (Button) findViewById(R.id.bt_ver_url);
        btVerIp = (Button) findViewById(R.id.bt_ver_ip);
        btVerId = (Button) findViewById(R.id.bt_ver_id);

        btVerUsername.setOnClickListener(this);
        btVerPwd.setOnClickListener(this);
        btVerTell.setOnClickListener(this);
        btVerEmail.setOnClickListener(this);
        btVerChinese.setOnClickListener(this);
        btVerUrl.setOnClickListener(this);
        btVerIp.setOnClickListener(this);
        btVerId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        inputText = etInput.getText().toString();
        Log.i("TAG", "输入内容：--->"+inputText);
        if (v == btVerUsername) {
            VerUsername();
        } else if (v == btVerPwd) {
            VerPwd();
        } else if (v == btVerTell) {
            VerTell();
        } else if (v == btVerEmail) {
            VerEmail();
        } else if (v == btVerChinese) {
            VerChinese();
        } else if (v == btVerUrl) {
            VerUrl();
        } else if (v == btVerIp) {
            VerIp();
        } else if (v == btVerId) {
            VerId();
        }
    }

    private void VerId() {
        boolean result = RegularExpressionUtils.isIDCard(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerId--->" + result);
    }

    private void VerIp() {
        boolean result = RegularExpressionUtils.isIPAddr(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerIp--->" + result);
    }

    private void VerUrl() {
        boolean result = RegularExpressionUtils.isUrl(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerUrl--->" + result);
    }

    private void VerChinese() {
        boolean result = RegularExpressionUtils.isChinese(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerChinese--->" + result);
    }

    private void VerEmail() {
        boolean result = RegularExpressionUtils.isEmail(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerEmail--->" + result);
    }

    private void VerTell() {
        boolean result = RegularExpressionUtils.isMobile(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerTell--->" + result);
    }

    private void VerPwd() {
        boolean result = RegularExpressionUtils.isPassword(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerPwd--->" + result);
    }

    private void VerUsername() {
        boolean result = RegularExpressionUtils.isUsername(inputText);
        Toast.makeText(MainActivity.this, "测试结果" + result, Toast.LENGTH_SHORT).show();
        Log.i("MainActivity", "VerUsername--->" + result);
    }

}
