package com.example.administrator.daggerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DaggerUserComponent.create().inject(this);
//        apiService.show();
        DaggerUserComponent.builder().userMoudle(new UserMoudle()).build().inject(this);
    }
}
