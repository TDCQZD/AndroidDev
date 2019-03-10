package com.example.administrator.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.rxjavademo.Utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description:RxJava的操作符使用
 * @author: Administrator
 * @Date: 2017/9/15 9:56
 */

public class OperatorsActivity extends AppCompatActivity {
    //    public void (View v){}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
    }

    /*
    map操作符使用
    作用：对发射时间发送的每一个事件应用一个函数，是的每一个事件都按照指定的函数去变化

    注：
     */
    public void Map(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer + "、学习是第一步";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("TAG", "accept-->" + s);
            }
        });
    }


    /*
    flatMap操作符使用
    作用：把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable。
    注：flatMap 并不能保证事件的顺序
     */
    public void flatMap(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("学习");
                e.onNext("码代码");
                e.onNext("视频学习");
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + s);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("TAG", "accept-->" + s);
            }
        });
    }

    /*
   concat操作符使用
    作用：把两个发射器合并为一个
    注：
     */
    public void concat(View view) {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "concat : " + integer + "\n");
                    }
                });


    }

    /*
   concatMap操作符使用
    作用：进行数据类型装换
    注：concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序
     */
    public void concatMap(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("学习");
                e.onNext("码代码");
                e.onNext("视频学习");
            }
        }).concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + s);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("TAG", "accept-->" + s);
            }
        });
    }

    /*
    zip操作符使用
   作用：zip 专用于合并事件
   注：
    */
    public void Zip(View view) {
        Observable<String> getStringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("、学习");
                e.onNext("、码代码");
                e.onNext("、视频学习");
            }
        });
        Observable<Integer> getIntegerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        });
        Observable.zip(getIntegerObservable, getStringObservable, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("TAG", "accept-->" + s);
            }
        });
    }
    /*
         merge 操作符使用
        作用：merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
        */
    public void merge(View v) {
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "accept: merge :" + integer + "\n");
                    }
                });


    }
    /*
    distinct操作符使用
   作用：简单的去重
   注：
    */
    public void distinct(View v) {
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "distinct : " + integer + "\n");
                    }
                });
    }

    /*
    Filter操作符使用
   作用：过滤
   注：
    */
    public void Filter(View v) {
        Observable.just(1, -100, 20, 65, -5, 7, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer >= 0;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

                Log.d("TAG", "filter : " + integer + "\n");
            }
        });


    }

    /*
    buffer 操作符使用
   作用：buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable 。
    */
    public void buffer(View v) {
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d("TAG", "buffer size :" + integers.size());
                        for (Integer integer : integers) {
                            Log.d("TAG", "buffer value--->" + integer);
                        }
                    }
                });
    }

    /*
    timer操作符使用
    作用：定时执行
     */
    public void timer(View v) {

        Observable.just(1, 2, 3, 4, 5)
                .timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                        Log.d("TAG", "timer :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                    }
                });

    }

    /*
    interval 操作符使用
    作用：用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     */
    public void interval(View v) {
        Observable.interval(3, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                        Log.d("TAG", "interval :" + aLong + " at " + TimeUtil.getNowStrTime() + "\n");
                    }
                });


    }

    /*
     doOnNext操作符使用
     作用：是让订阅者在接收到数据之前，处理一些另外的操作
      */
    public void doOnNext(View v) {
        Observable.just(1, 2, 3, 4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "doOnNext 保存 " + integer + "成功" + "\n");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

                Log.d("TAG", "doOnNext :" + integer + "\n");
            }
        });


    }

    /*
   skip操作符使用
   作用：接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
    */
    public void skip(View v) {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "skip : " + integer + "\n");
                    }
                });


    }

    /*
     take操作符使用
     作用：接受一个 long 型参数 count ，代表至多接收 count 个数据
      */
    public void take(View v) {
        Flowable.fromArray(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "accept: take : " + integer + "\n");
                    }
                });


    }

    /*
     just操作符使用
     作用：简单的发射器依次调用 onNext() 方法。
     */
    public void just(View v) {
        Observable.just("吃饭", "休息", "运动")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("TAG", "onNext:" + s);
                    }
                });


    }

    /*
      Create操作符使用
     作用：主要用于产生一个 Obserable 被观察者对象
     */
    public void Create(View v) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("学习");
                e.onNext("码代码");
                e.onNext("视频学习");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("TAG", "accept-->" + s);
            }
        });


    }

    /*
      Single 操作符使用
     作用：Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
     */
    public void Single(View v) {
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {

                        Log.d("TAG", "single : onSuccess : " + integer + "\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.d("TAG", "single : onError : " + e.getMessage() + "\n");
                    }
                });


    }

    /*
      debounce 操作符使用
     作用：过快发送频率
     */
    public void debounce(View v) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "debounce :" + integer + "\n");
                    }
                });

    }

    /*
      defer 操作符使用
     作用：简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
     */
    public void defer(View v) {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        });


        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {

                Log.d("TAG", "defer : " + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.d("TAG", "defer : onError : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {

                Log.d("TAG", "defer : onComplete\n");
            }
        });


    }

    /*
      last 操作符使用
     作用：last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    public void last(View v) {
        Observable.just(1, 2, 3,4,5,6)
                .last(9)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                        Log.d("TAG", "last : " + integer + "\n");
                    }
                });


    }



    /*
      reduce 操作符使用
     作用：reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值
     */
    public void reduce(View v) {
        Observable.just(1, 5, 2)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

                Log.d("TAG", "accept: reduce : " + integer + "\n");
            }
        });


    }

    /*
      scan 操作符使用
     作用：scan 操作符作用和上面的 reduce 一致，
     唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。
     */
    public void scan(View v) {
        Observable.just(1, 5,2)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {

                Log.d("TAG", "accept: scan " + integer + "\n");
            }
        });


    }

    /*
      window 操作符使用
     作用：按照实际划分窗口，将数据发送给不同的 Observable
     */
    public void window(View v) {
        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
                .take(15) // 最多接收15个
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(@NonNull Observable<Long> longObservable) throws Exception {

                        Log.d("TAG", "Sub Divide begin...\n");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(@NonNull Long aLong) throws Exception {

                                        Log.d("TAG", "Next:" + aLong + "\n");
                                    }
                                });
                    }
                });


    }

}
