package com.example.administrator.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description:RxJava的线程调度
 * @author: Administrator
 * @Date: 2017/9/14 19:38
 */

public class SchedulerActivity extends AppCompatActivity {
    private TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvshow = (TextView) findViewById(R.id.tvShow);
        Log.d("TAG", "onCreate-->" + Thread.currentThread().getName());
    }

    public void test(View view) {
        Log.d("TAG", "test-->" + Thread.currentThread().getName());
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {//创建被观察者
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.d("TAG", "Observable--> " + Thread.currentThread().getName());
                e.onNext("吃饭");
//                e.onNext("休息");
//                e.onNext("K歌");
//                e.onNext("跳舞");
//                e.onError(new TimeoutException());
            }
        });
        Observer<String> observer = new Observer<String>() {//创建观察者
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("TAG", "onSubscribe--> " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("TAG", "onNext--> " + Thread.currentThread().getName());
                Log.d("TAG", "onNext-->" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TAG", "onError-->" + e);
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete");
            }
        };
//        observable.subscribe(observer);//订阅事件
        /*
        线程调度：

         */
        observable.subscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
