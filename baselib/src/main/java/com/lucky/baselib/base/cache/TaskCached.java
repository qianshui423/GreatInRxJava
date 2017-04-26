package com.lucky.baselib.base.cache;

import android.text.TextUtils;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by liuxuehao on 16/12/30.
 */

public class TaskCached {
    private static TaskCached INSTANCE;

    private final int INIT_SIZE = 5;
    /**
     * 缓存数据驱动源
     */
    private HashMap<String, Flowable> mEngineCached;
    /**
     * 缓存任务源
     */
    private HashMap<String, DisposableSubscriber> mTaskCached;
    /**
     * 绑定任务key序列
     */
    private List<String> mBindKeyToLifecycle;

    private String mCurrentKey = "";

    private TaskCached() {
        mEngineCached = new HashMap<>(INIT_SIZE);
        mTaskCached = new HashMap<>(INIT_SIZE);
        mBindKeyToLifecycle = new ArrayList<>(INIT_SIZE);
    }

    public static TaskCached getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskCached.class) {
                INSTANCE = new TaskCached();
            }
        }
        return INSTANCE;
    }

    public TaskCached addEngine(String key, Flowable flowable) {
        if (mBindKeyToLifecycle.isEmpty()) throw new RuntimeException("还没有绑定任何Api到生命周期");
        if (!mBindKeyToLifecycle.contains(key)) throw new RuntimeException("暂不支持应用级别的任务管理");
        mCurrentKey = key;
        if (null != mEngineCached.get(key)) {
            mEngineCached.remove(key);
        }

        mEngineCached.put(key, flowable);

        return INSTANCE;
    }

    public TaskCached addTask(DisposableSubscriber disposableSubscriber) {
        if (TextUtils.isEmpty(mCurrentKey)) throw new RuntimeException("没有指定有效的任务Key");

        Subscriber subscriberOld = mTaskCached.get(mCurrentKey);
        if (null != subscriberOld) {
            mTaskCached.remove(mCurrentKey);
        }

        mTaskCached.put(mCurrentKey, disposableSubscriber);

        return INSTANCE;
    }

    public void executeTask() {
        if (TextUtils.isEmpty(mCurrentKey)) throw new RuntimeException("没有指定有效的任务Key");

        Flowable flowable = mEngineCached.get(mCurrentKey);
        DisposableSubscriber disposableSubscriber = mTaskCached.get(mCurrentKey);

        if (null == disposableSubscriber || null == flowable)
            throw new RuntimeException("执行任务前要首先指定Engine和Task");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);

        //将当前key置空
        mCurrentKey = "";
    }

    public void clearTaskCached() {
        for (Map.Entry<String, DisposableSubscriber> entry : mTaskCached.entrySet()) {
            DisposableSubscriber disposableSubscriber = entry.getValue();
            disposableSubscriber.dispose();
        }
        mTaskCached.clear();
    }

    public boolean isRunningExistTask() {
        if (mTaskCached.isEmpty()) return false;
        else return true;
    }

    public void bindLifecycle(List<String> apiKeys) {
        if (null == apiKeys || apiKeys.isEmpty()) return;
        //绑定前处理旧绑定
        if (!mBindKeyToLifecycle.isEmpty()) {
            for (String key : mBindKeyToLifecycle) {
                stopTask(key);
            }
            mBindKeyToLifecycle.clear();
        }

        mBindKeyToLifecycle.addAll(apiKeys);
    }

    public void unbindLifecycle() {
        for (String key : mBindKeyToLifecycle) {
            stopTask(key);
        }
    }

    private void stopTask(String key) {
        DisposableSubscriber disposableSubscriber = mTaskCached.get(key);
        if (null == disposableSubscriber) return;

        mTaskCached.remove(key);
    }
}