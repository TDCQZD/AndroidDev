package com.example.administrator.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.rxjavademo.bean.Event;
import com.example.administrator.rxjavademo.event.RxBus;
import com.example.administrator.rxjavademo.event.RxBus2;

import io.reactivex.functions.Consumer;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/9/18 15:35
 */

public class RxBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        RxBus.get().toObservable(Event.class).subscribe(new Consumer<Event>() {
            @Override
            public void accept(Event event) throws Exception {
                String id = event.getId();
                String name = event.getName();
                Log.i("TAG", "RxBus获取的信息：" + id + "\n" + name);
            }
        });
        RxBus2.get().toFlowable(Event.class).subscribe(new Consumer<Event>() {
            @Override
            public void accept(Event event) throws Exception {
                String id = event.getId();
                String name = event.getName();
                Log.i("TAG", "RxBus获取的信息：" + id + "\n" + name);
            }
        });

    }

    public void sentEvent(View view) {
        Event event = new Event("001", "张三");
        Event event2 = new Event("002", "李四");
        RxBus2.get().post(event2);
        RxBus.get().post(event);
    }


}
