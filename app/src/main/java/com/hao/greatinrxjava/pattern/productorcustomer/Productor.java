package com.hao.greatinrxjava.pattern.productorcustomer;

/**
 * Created by liuxuehao on 16/12/23.
 */

public class Productor implements Runnable {
    private Info info;

    public Productor(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        for (int x = 0; x < 100; x++) {
            if (x % 2 == 0) {
                this.info.set("第1组数据", "1.数据说明");
            } else {
                this.info.set("第2组数据", "2.数据说明");
            }
        }
    }
}
