package com.hao.greatinrxjava.rxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by liuxuehao on 16/12/22.
 */

public class COO {
    public static void coo() {
        ConnectableObservable<Integer> connectableObservable = Observable.range(1, 1000000).sample(7, TimeUnit.MILLISECONDS).publish();

        connectableObservable.subscribe(integer -> Log.d("COOA", "first:" + integer));

        connectableObservable.subscribe(integer -> Log.d("COOA", "second:" + integer));

        connectableObservable.connect(connection -> Log.d("COOA", "subscription:" + connection.isDisposed()));

        connectableObservable.subscribe(integer -> Log.d("COOA", "third:" + integer));

        RxJavaPlugins.setErrorHandler(e -> Log.d("COOA", "coo: " + e));
    }
}
