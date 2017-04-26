package com.hao.greatinrxjava.pattern.productorcustomer;

/**
 * Created by liuxuehao on 16/12/23.
 */

public class Info {
    private String title;
    private String content;

    private boolean flag = true;

    public synchronized void set(String title, String content) {
        if (this.flag == false) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.content = content;
        this.flag = false;
        super.notify();
    }

    public synchronized void get() {
        if (this.flag == true) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + "-" + this.content);
        this.flag = true;
        super.notify();
    }
}
