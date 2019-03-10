package com.example.administrator.rxjavademo.event;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/9/18 15:24
 * 基于 RxRelay 有异常处理的 Rxbus
 */

public class RxBus3 {
    private final Relay<Object> mBus;

    private RxBus3() {
        // toSerialized method made bus thread safe
        mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus3 get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.doOnNext((Consumer<? super Object>) obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus3 BUS = new RxBus3();
    }
}
