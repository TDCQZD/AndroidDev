package com.example.administrator.dialogtest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.dialogtest.fragment.CommonDialogFragment;
import com.example.administrator.dialogtest.fragment.EditDialogFragment;
import com.example.administrator.dialogtest.fragment.LoginDialogFragment;

/**
 * Created by Administrator on 2017/3/20.
 */

public class DialogFragmentActivity extends Activity implements LoginDialogFragment.LoginInputListener, View.OnClickListener {

    private Button btDialogfragmentCommon;
    private Button btDialogfragmentEdit;
    private Button btDialogfragmentLogin;
    private Button btDialogfragmentScreenAdaption;

    private FrameLayout fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fragment);
        findViews();
    }


    private void findViews() {
        btDialogfragmentCommon = (Button) findViewById(R.id.bt_dialogfragment_common);
        btDialogfragmentEdit = (Button) findViewById(R.id.bt_dialogfragment_edit);
        btDialogfragmentLogin = (Button) findViewById(R.id.bt_dialogfragment_login);
        btDialogfragmentScreenAdaption = (Button) findViewById(R.id.bt_dialogfragment_screen_adaption);
        fragment = (FrameLayout) findViewById(R.id.fragment);

        btDialogfragmentCommon.setOnClickListener(this);
        btDialogfragmentEdit.setOnClickListener(this);
        btDialogfragmentLogin.setOnClickListener(this);
        btDialogfragmentScreenAdaption.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == btDialogfragmentCommon) {
            // 确认对话框
            CommonDialogFragment commonDialogFragment = new CommonDialogFragment();
            commonDialogFragment.show(getFragmentManager(), "普通对话框");
        } else if (v == btDialogfragmentEdit) {
            // 可编辑对话框
            EditDialogFragment editDialogFragment = new EditDialogFragment();
            editDialogFragment.show(getFragmentManager(), "可编辑对话框");
        } else if (v == btDialogfragmentLogin) {
            // 可编辑的登录对话框,旋转后信息不会丢失
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(getFragmentManager(), "loginDialog");
        } else if (v == btDialogfragmentScreenAdaption) {
            // 可屏幕适配的对话框
            showScreenAdaptionDialogFragment();
        }
    }

    private void showScreenAdaptionDialogFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        EditDialogFragment editDialogFragment = new EditDialogFragment();
        boolean mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
        Log.e("TAG", mIsLargeLayout+"");
        if (mIsLargeLayout) {
            editDialogFragment.show(fragmentManager, "");
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.fragment, editDialogFragment).commit();
        }
    }


    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(this, "帐号：" + username + ",  密码 :" + password,
                Toast.LENGTH_SHORT).show();
    }

}
