package com.lucky.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuxuehao on 16/12/26.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements IBaseView {
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (null != mPresenter) mPresenter.attachView(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mPresenter) mPresenter.detachView();
    }

    protected T createPresenter() {
        return null;
    }

    @Override
    public <T> void display(T data, int which) {

    }

    @Override
    public void error(String error, int which) {

    }
}
