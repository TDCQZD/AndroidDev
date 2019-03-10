package com.example.zhangdai.qqlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Tencent mTencent;
    private IUiListener loginListener;
    private Button btLogin;
    private Button btLogout;
    private CircleImageView icon_image;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTencent = Tencent.createInstance("101403678", this.getApplicationContext());
        findViews();
    }


    private void findViews() {
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogout = (Button) findViewById(R.id.bt_logout);
        icon_image = (CircleImageView) findViewById(R.id.icon_image);
        tvUsername = (TextView) findViewById(R.id.tv_username);

        btLogin.setOnClickListener(this);
        btLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btLogin) {
            qqLogin();
        } else if (v == btLogout) {
            qqLogout();
        }
    }

    private void qqLogout() {
        mTencent.logout(MainActivity.this);
    }

    private void qqLogin() {
        mTencent.login(this, "all", loginListener);
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) { //登录成功后回调该方法,可以跳转相关的页面
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                JSONObject object = (JSONObject) o;

                try {

                    String accessToken = object.getString("access_token");

                    String expires = object.getString("expires_in");

                    String openID = object.getString("openid");

                    mTencent.setAccessToken(accessToken, expires);

                    mTencent.setOpenId(openID);

                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onError(UiError uiError) {
                Toast.makeText(MainActivity.this, "授权出错！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "授权取消！", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {

            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);

                Tencent.handleResultData(data, loginListener);

                UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {

                        try {
                            JSONObject info = (JSONObject) o;
                            String nickName = info.getString("nickname");//获取用户昵称
                            String iconUrl = info.getString("figureurl_qq_2");//获取用户头像的url

                            Log.i("TAG", "Username" + nickName);
                            tvUsername.setText(nickName);
                            Glide.with(MainActivity.this).load(iconUrl).into(icon_image);//Glide解析获取用户头像
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {

                        Log.e("GET_QQ_INFO_ERROR", "获取qq用户信息错误");
                        Toast.makeText(MainActivity.this, "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                        Log.e("GET_QQ_INFO_CANCEL", "获取qq用户信息取消");
                        Toast.makeText(MainActivity.this, "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
