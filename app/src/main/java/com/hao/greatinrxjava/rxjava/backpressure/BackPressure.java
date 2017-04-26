package com.hao.greatinrxjava.rxjava.backpressure;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuxuehao on 17/4/14.
 */

public class BackPressure {
    private static Consumer<Integer> sConsumer = new Consumer<Integer>() {
        @Override
        public void accept(@NonNull Integer integer) throws Exception {
            Log.w("TAG", "Thread[" + Thread.currentThread().getName() + " ," + Thread.currentThread().getId() + "] : " + integer);
        }
    };

    /**
     * Observable Range
     */
    public static void testObservableRange() {
        Observable<Integer> observable = Observable.range(0, 10000);
        observable.doOnNext(sConsumer)
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.w("TAG", "onsubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.w("TAG", "onNext--->Thread[" + Thread.currentThread().getName() + " ," + Thread.currentThread().getId() + "] : " + integer);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }

    /**
     * Observable Interval
     */
    public static void testObservaleInterval() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.MILLISECONDS);
        observable.observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.w("TAG", "onsubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.w("TAG", "onNext--->" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }

    /**
     * Flowable Range
     */
    public static void testFlowableRange() {
        Flowable.range(0, 10000)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    Subscription sub;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.w("TAG", "onsubscribe start");
                        sub = s;
                        sub.request(1);
                        Log.w("TAG", "onsubscribe end");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.w("TAG", "onNext--->" + integer);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }

    /**
     * Flowable Interval
     */
    public static void testFlowableInterval() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    Subscription sub;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.w("TAG", "onsubscribe start");
                        sub = s;
                        sub.request(1);
                        Log.w("TAG", "onsubscribe end");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.w("TAG", "onNext--->" + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }

    public static void testFlowableIntervalBuffer() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .observeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    Subscription sub;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.w("TAG", "onsubscribe start");
                        sub = s;
                        sub.request(1);
                        Log.w("TAG", "onsubscribe end");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.w("TAG", "onNext--->" + aLong);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }

    public static void testFlowableIntervalDrop() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    Subscription sub;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.w("TAG", "onsubscribe start");
                        sub = s;
                        sub.request(1);
                        Log.w("TAG", "onsubscribe end");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.w("TAG", "onNext--->" + aLong);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }

    public static void testFlowableIntervalLatest() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    Subscription sub;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.w("TAG", "onsubscribe start");
                        sub = s;
                        sub.request(1);
                        Log.w("TAG", "onsubscribe end");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.w("TAG", "onNext--->" + aLong);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.w("TAG", "onComplete");
                    }
                });
    }
}
