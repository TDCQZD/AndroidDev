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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
/**
 *@Description：RxJava基本使用
 *@author ：Administrator
 *@date ：2017/9/14 11:36
 *
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvshow = (TextView) findViewById(R.id.tvShow);
    }

    public void test(View view) {
        /*
        普通用法
         */
        Observable<String> observable = getObservable();
        Observer<String> observer = getObserver();
      /*
      3、订阅事件
       */
        observable.subscribe(observer);

        /*
        简介写法
         */
//        observable.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d("TAG", "onNext:" + s);
//                tvshow.setText("");
//                tvshow.append(s);
//                tvshow.append("\n");
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.d("TAG", "onError:" + throwable);
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                Log.d("TAG", "onComplete");
//            }
//        });
    }

    /*
    1、创建Observable-被观察者
     */
    public Observable<String> getObservable() {

        /*
        Observable创建方法一
         */
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {//反射器
                e.onNext("吃饭");
                e.onNext("休息");
                e.onNext("K歌");
                e.onNext("跳舞");
                /*
                onComplete和onError只能存其一
                 */
                e.onComplete();
//                e.onError(new TimeoutException());
            }
        });
         /*
        Observable创建方法二
         */
//         Observable observable=Observable.just("吃饭","休息","运动");

        /*
         Observable创建方法三
         */
//        Observable observable=Observable.fromArray("吃饭","休息","运动");
        /*
        Observable创建方法四
         */
//        Observable observable = Observable.fromCallable(new Callable() {
//            @Override
//            public Object call() throws Exception {
//                return "吃饭";
//            }
//        });

//        return observable;
    }

    /*
    2、创建Observer-观察者
     */
    public Observer<String> getObserver() {

        return new Observer<String>() {
            Disposable disposable = null;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                boolean result = d.isDisposed();//判断是否取消订阅关系
                Log.d("TAG", "onSubscribe:" + result);
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("TAG", "onNext:" + s);
                if (s.equals("休息")) {
                    disposable.dispose();//取消订阅关系
                    Log.d("TAG", "onNext:" + disposable.isDisposed());
                }


            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TAG", "onError:" + e);
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete");
            }
        };
    }


}
