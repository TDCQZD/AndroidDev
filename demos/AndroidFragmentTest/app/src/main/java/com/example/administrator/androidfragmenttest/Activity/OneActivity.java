package com.example.administrator.androidfragmenttest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidfragmenttest.Fragment.OneFragment;
import com.example.administrator.androidfragmenttest.R;

public class OneActivity extends AppCompatActivity {
    private TextView tv;
    private String resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        tv = (TextView) findViewById(R.id.tv_show);
    }

    public void oneintentActivity(View view) {//onClick要使用public\
        String data = "传递数据给下一个Acvitity";
        Intent intent = new Intent(OneActivity.this, TwoActivity.class);
        //直接传递数据
        intent.putExtra("extra_data", data);
        //使用Bundle传递数据
        Bundle bundle = new Bundle();
        bundle.putString("extra_data", data);
        intent.putExtras(bundle);
//        startActivity(intent);
        startActivityForResult(intent, 1);//带返回值
//        finish();
    }

    public void oneintentFragment(View view) {

        OneFragment oneFragment = new OneFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment, oneFragment);
        transaction.commit();

    }

    /**
     * 回调处理，接受数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == TwoActivity.RESULT_CODE) {
                    resultData = data.getStringExtra("resultdata");
                    tv.setText(resultData);
                    Log.i("TAG", "返回的数据--->" + resultData);
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
