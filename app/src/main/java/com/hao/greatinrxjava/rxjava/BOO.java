package com.hao.greatinrxjava.rxjava;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by liuxuehao on 16/12/22.
 * <p>
 * RxJava2.0 BlockingObservable has been integrated with the main reactive type
 */

public class BOO {
    private void testBOO() {
        BOO.firstObserver().subscribe();
    }

    /**
     * Observable的first用法
     *
     * @return
     */
    public static Observable<Integer> firstObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).filter(integer -> integer > 1);
    }

    /**
     * BlockingObservable的from用法
     *
     * @return
     */
    private static Single<List<Integer>> getBlockingObserver() {
        return Flowable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).toList();
    }

    /**
     * BlockingObservable的first用法
     *
     * @return
     */
    public static Maybe<List<Integer>> firstBlockingObserver() {
        return getBlockingObserver().filter(integer -> {
            for (int i : integer) {
                if (i > 5) {
                    return true;
                }
            }
            return false;
        });
    }
}