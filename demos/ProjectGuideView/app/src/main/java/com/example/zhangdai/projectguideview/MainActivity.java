package com.example.zhangdai.projectguideview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhangdai.projectguideview.activity.RotateAnimationPage;
import com.example.zhangdai.projectguideview.activity.SinglePage;

/**
 * App引导界面整合
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btSinglePage;
    private Button btrotataAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btrotataAnimation= (Button) findViewById(R.id.bt_rotate_animation);
        btSinglePage= (Button) findViewById(R.id.bt_single_page);

        btrotataAnimation.setOnClickListener(this);
        btSinglePage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Class intentclass=null;
        switch (view.getId()) {
            case  R.id.bt_rotate_animation:
        intentclass=RotateAnimationPage.class;
                break;
            case  R.id.bt_single_page:
                intentclass=SinglePage.class;
                break;
        }
        startActivity(new Intent(this,intentclass));
    }
}
