package com.example.administrator.runningpermissiontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btPermissionOne;
    private Button btPermissionMore;
    private Button btPermissionEasy;

    private Button btPermissionDispatcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }



    private void findViews() {
        btPermissionOne = (Button)findViewById( R.id.bt_permission_one );
        btPermissionMore = (Button)findViewById( R.id.bt_permission_more );
        btPermissionEasy = (Button)findViewById( R.id.bt_permission_Easy );
        btPermissionDispatcher = (Button)findViewById( R.id.bt_permission_Dispatcher );

        btPermissionOne.setOnClickListener( this );
        btPermissionMore.setOnClickListener( this );
        btPermissionEasy.setOnClickListener( this );
        btPermissionDispatcher.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        if ( v == btPermissionOne ) {
          startActivity(new Intent(MainActivity.this,OnePermissionActivity.class));
        } else if ( v == btPermissionMore ) {
            startActivity(new Intent(MainActivity.this,MorePermissionActivity.class));
        } else if ( v == btPermissionEasy ) {
            startActivity(new Intent(MainActivity.this,EasyPermisssionActivity.class));
        }  else if ( v == btPermissionDispatcher ) {
            startActivity(new Intent(MainActivity.this,PermissionDispatcherActivity.class));
        }
    }

}
