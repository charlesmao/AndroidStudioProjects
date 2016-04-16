package com.shiyanlou.asyntaskdemo;

/**
 * Created by AQ-DN-01-076B on 2016/4/6.
 */
public class DelayOperator {

    public void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
