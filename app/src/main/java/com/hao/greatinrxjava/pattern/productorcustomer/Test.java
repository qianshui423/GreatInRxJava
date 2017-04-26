package com.hao.greatinrxjava.pattern.productorcustomer;

/**
 * Created by liuxuehao on 16/12/26.
 */

public class Test {
    private void testDemo() {
        Info info = new Info();
        new Thread(new Productor(info)).start();
        new Thread(new Customer(info)).start();
    }
}
