package com.lucky.baselib.base.interfaces;

/**
 * Created by liuxuehao on 16/12/29.
 */

public interface IFlow<T> {
    void flowData(T data);

    void flowCode(int code);
}
