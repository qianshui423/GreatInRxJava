package com.hao.greatinrxjava.rxjava;

import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by liuxuehao on 16/12/23.
 */

public class MergeTest {
    private static final String sContent = "工作台内容";
    private static final String sBadge = "工作台角标";

    public static Observable<String> getObservable(int timeDelay, String str) {
        return Observable.just(str).delay(timeDelay, TimeUnit.MILLISECONDS);
    }

    public static void mergeTest() {
        //工作台内容接口模拟
        Observable<String> observableContent = getObservable(1000, sContent);

        //工作台角标接口模拟
        Observable<String> observableBadge = getObservable(500, sBadge);

        observableContent.concatWith(observableBadge)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.d("MERGE", "onNext: " + value);
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
