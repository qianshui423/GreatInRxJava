package com.hao.greatinrxjava.rxjava.changelog;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuxuehao on 17/4/14.
 */

public class ChangeLog {
    /**
     * 抛出NullPointerException
     */
    public static void testNulls() {

        Observable.just(null);

        Single.just(null);

        Observable.fromCallable(() -> null)
                .subscribe(System.out::println, Throwable::printStackTrace);

        Observable.just(1).map(v -> null)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    public static void emitAnyValue() {
        Observable<Object> source = Observable.create((ObservableEmitter<Object> emitter) -> {
            System.out.println("Side-effect 1");
            emitter.onNext(Irrelevant.INSTANCE);

            System.out.println("Side-effect 2");
            emitter.onNext(Irrelevant.INSTANCE);

            System.out.println("Side-effect 3");
            emitter.onNext(Irrelevant.INSTANCE);
        });


        source.subscribe(e -> Log.w("TAG", "onNext--->" + e), Throwable::printStackTrace);
    }

    /**
     * 数据源单值，可被多次订阅
     * 1.x是可以将Observable转换成Single，2.x不能
     */
    public static void testSingle() {
        Single<Integer> single = Single.just(25);
        single.subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                Log.w("TAG", "onSuccess " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    /**
     * 对下层数据只有完成和错误通知，不发射任何值
     * 1.x是可以将Observable转换成Completable，2.x不能
     */
    public static void testCompletable() {
        CompletableOnSubscribe completableOnSubscribe = new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {

            }
        };
        Completable completable = Completable.create(completableOnSubscribe);

        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    /**
     * union of Single and Completable
     */
    public static void testMaybe() {
        Maybe<Boolean> maybe = Maybe.just(true);
        maybe.subscribe(new MaybeObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void testNew() {
        Flowable.create((FlowableEmitter<Integer> emitter) -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("OnSubscribe start");
                s.request(Long.MAX_VALUE);
                System.out.println("OnSubscribe end");
            }

            @Override
            public void onNext(Integer v) {
                System.out.println(v);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        });
    }

    public static void testFlowableSubscriber() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }, BackpressureStrategy.DROP);

        flowable.subscribe(new FlowableSubscriber<Integer>() {
            Subscription sub;

            @Override
            public void onSubscribe(@NonNull Subscription s) {
                Log.w("TAG", "onsubscribe start");
                /*Object obj = null;
                obj.toString();*/
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

            }

            @Override
            public void onComplete() {
                Log.w("TAG", "onComplete");
            }
        });
    }

    public static void testHotObservable() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.MILLISECONDS).publish().autoConnect();
        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.w("TAG", "First onNext--->" + aLong);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.w("TAG", "Second onNext--->" + aLong);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
