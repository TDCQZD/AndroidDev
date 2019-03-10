package com.example.administrator.countdownviewtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.iwgang.countdownview.CountdownView;

/**
 * CountdownView是Android中第三方的倒计时秒杀控件，在商城购物相关软件秒杀和倒计时中经常用到；
 * 它可以在ListView和RecyclerView中显示秒杀倒计时，滑动时候并且时间不会错乱，且性能良好。
 * CountdownView还可以自定义显示各种风格。
 *
 * @author ZD
 *         created at 2017/6/29 15:33
 *         description：
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CountdownView cvCountdownViewTest1;
    private CountdownView cvCountdownViewTest2;
    private CountdownView cvCountdownViewTest211;
    private CountdownView cvCountdownViewTest21;
    private CountdownView cvCountdownViewTest22;
    private CountdownView cvCountdownViewTest3;
    private CountdownView cvCountdownViewTest4;
    private CountdownView cvCountdownViewTest5;
    private CountdownView cvCountdownViewTest6;
    private Button btnToDynamicShowActivity;
    private Button btnToListViewActivity;
    private Button btnToRecyclerViewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initData();
    }


    /**
     * 初始化视图
     */
    private void findViews() {
        cvCountdownViewTest1 = (CountdownView) findViewById(R.id.cv_countdownViewTest1);
        cvCountdownViewTest2 = (CountdownView) findViewById(R.id.cv_countdownViewTest2);
        cvCountdownViewTest211 = (CountdownView) findViewById(R.id.cv_countdownViewTest211);
        cvCountdownViewTest21 = (CountdownView) findViewById(R.id.cv_countdownViewTest21);
        cvCountdownViewTest22 = (CountdownView) findViewById(R.id.cv_countdownViewTest22);
        cvCountdownViewTest3 = (CountdownView) findViewById(R.id.cv_countdownViewTest3);
        cvCountdownViewTest4 = (CountdownView) findViewById(R.id.cv_countdownViewTest4);
        cvCountdownViewTest5 = (CountdownView) findViewById(R.id.cv_countdownViewTest5);
        cvCountdownViewTest6 = (CountdownView) findViewById(R.id.cv_countdownViewTest6);

        btnToDynamicShowActivity = (Button) findViewById(R.id.btn_toDynamicShowActivity);
        btnToListViewActivity = (Button) findViewById(R.id.btn_toListViewActivity);
        btnToRecyclerViewActivity = (Button) findViewById(R.id.btn_toRecyclerViewActivity);

        btnToDynamicShowActivity.setOnClickListener(this);
        btnToListViewActivity.setOnClickListener(this);
        btnToRecyclerViewActivity.setOnClickListener(this);
    }

    /**
     * 数据加载
     */
    private void initData() {
        CountdownView mCvCountdownViewTest1 = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownViewTest1.setTag("test1");
        //倒计5小时
        long time1 = (long)5 * 60 * 60 * 1000;
        mCvCountdownViewTest1.start(time1);

        CountdownView mCvCountdownViewTest2 = (CountdownView)findViewById(R.id.cv_countdownViewTest2);
        mCvCountdownViewTest2.setTag("test2");
        //倒计时30分
        long time2 = (long)30 * 60 * 1000;
        mCvCountdownViewTest2.start(time2);

        CountdownView cvCountdownViewTest211 = (CountdownView)findViewById(R.id.cv_countdownViewTest211);
        cvCountdownViewTest211.setTag("test21");
        //倒计时30分
        long time211 = (long)30 * 60 * 1000;
        cvCountdownViewTest211.start(time211);


        CountdownView mCvCountdownViewTest21 = (CountdownView)findViewById(R.id.cv_countdownViewTest21);
        mCvCountdownViewTest21.setTag("test21");
        //倒计时一天，24小时
        long time21 = (long)24 * 60 * 60 * 1000;
        mCvCountdownViewTest21.start(time21);

        CountdownView mCvCountdownViewTest22 = (CountdownView)findViewById(R.id.cv_countdownViewTest22);
        mCvCountdownViewTest22.setTag("test22");
        //倒计时30分钟
        long time22 = (long)30 * 60 * 1000;
        mCvCountdownViewTest22.start(time22);

        CountdownView mCvCountdownViewTest3 = (CountdownView)findViewById(R.id.cv_countdownViewTest3);
        //倒计时9小时
        long time3 = (long)9 * 60 * 60 * 1000;
        mCvCountdownViewTest3.start(time3);

        CountdownView mCvCountdownViewTest4 = (CountdownView)findViewById(R.id.cv_countdownViewTest4);
        //倒计时150天
        long time4 = (long)150 * 24 * 60 * 60 * 1000;
        mCvCountdownViewTest4.start(time4);

        final CountdownView mCvCountdownViewTest5 = (CountdownView)findViewById(R.id.cv_countdownViewTest5);
        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                long time = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                        time += 1000;
                        publishProgress(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            protected void onProgressUpdate(Long... values) {
                super.onProgressUpdate(values);
                mCvCountdownViewTest5.updateShow(values[0]);
            }
        }.execute();

        CountdownView mCvCountdownViewTest6 = (CountdownView)findViewById(R.id.cv_countdownViewTest6);
        //倒计时两个小时
        long time6 = (long)2 * 60 * 60 * 1000;
        mCvCountdownViewTest6.start(time6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toDynamicShowActivity:
                startActivity(new Intent(MainActivity.this, DynamicShowActivity.class));
                break;
            case R.id.btn_toListViewActivity:
                startActivity(new Intent(MainActivity.this, CountdownViewListViewActivity.class));
                break;
            case R.id.btn_toRecyclerViewActivity:
                startActivity(new Intent(MainActivity.this, CountdownViewRecyclerViewActivity.class));
                break;
        }


    }

}
