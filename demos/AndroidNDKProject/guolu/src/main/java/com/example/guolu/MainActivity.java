package com.example.guolu;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    {
        System.loadLibrary("guolu");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PressureView pressureView = new PressureView(this);

        setContentView(pressureView);
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    SystemClock.sleep(500);
                    int pressure = Math.abs(getPressure());//0~250
                    pressureView.setPressure(pressure);

                    if(pressure > 220){//如果压力大于220就要爆炸
                        break;
                    }
                }




            }
        }.start();
    }

    /**
     * native代码
     * 调用C代码中的对应的方法
     *
     * @return
     */
    public native int getPressure();
}
