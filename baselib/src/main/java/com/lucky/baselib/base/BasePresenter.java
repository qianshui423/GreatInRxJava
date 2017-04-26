package com.lucky.baselib.base;

import com.lucky.baselib.base.cache.TaskCached;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by liuxuehao on 16/12/26.
 */

public abstract class BasePresenter {
    protected Reference<IBaseView> mViewRef;

    public void attachView(IBaseView view) {
        mViewRef = new WeakReference<>(view);

        prepare();
        bindApiLifecycle();
    }

    public void detachView() {
        unbindApiLifecycle();
        if (null != mViewRef) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected IBaseView getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return null != mViewRef && null != mViewRef.get();
    }

    protected void prepare() {

    }

    protected void bindApiLifecycle() {

    }

    private void unbindApiLifecycle() {
        TaskCached.getInstance().unbindLifecycle();
    }

    final protected <T> void requestData() {
        if (!isViewAttached()) {
            return;
        }
    }

    final protected void addApiKeys(List<String> apiKeys) {
        TaskCached.getInstance().bindLifecycle(apiKeys);
    }
}
