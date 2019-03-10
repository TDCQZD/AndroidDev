package com.example.administrator.rxjavademo.event;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/9/18 15:20
 * 有背压处理的 RxBus
 */

public class RxBus2 {
    private final FlowableProcessor<Object> mBus;

    private RxBus2() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus2 get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBus2 BUS = new RxBus2();
    }
}
