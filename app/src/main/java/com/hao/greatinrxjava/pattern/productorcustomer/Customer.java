package com.hao.greatinrxjava.pattern.productorcustomer;

/**
 * Created by liuxuehao on 16/12/23.
 */

public class Customer implements Runnable {
    private Info info;

    public Customer(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        for (int x = 0; x < 100; x++) {
            this.info.get();
        }
    }
}
