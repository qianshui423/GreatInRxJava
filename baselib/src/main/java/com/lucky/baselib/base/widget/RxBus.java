package com.lucky.baselib.base.widget;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by liuxuehao on 17/4/6.
 */

public class RxBus {
    private HashMap<Object, List<Subject>> mMap = new HashMap<>();
    private static RxBus INSTANCE;

    private RxBus() {
        
    }

    public static RxBus get() {
        if (INSTANCE == null) {
            synchronized (RxBus.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RxBus();
                }
            }
        }

        return INSTANCE;
    }

    public Observable register(@NonNull Object tag, @NonNull Class clazz) {
        List<Subject> subjects = mMap.get(tag);
        if (subjects == null) {
            subjects = new ArrayList<>();
            mMap.put(tag, subjects);
        }

        Subject subject = PublishSubject.create();
        subjects.add(subject);

        return subject;
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjects = mMap.get(tag);
        if (subjects != null) {
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                mMap.remove(tag);
            }
        }
    }

    public void post(@NonNull Object o) {
        post(o.getClass().getSimpleName(), o);
    }

    public void post(@NonNull Object tag, @NonNull Object o) {
        List<Subject> subjects = mMap.get(tag);
        if (subjects != null && !subjects.isEmpty()) {
            for (Subject s : subjects) {
                s.onNext(o);
            }
        }
    }
}
