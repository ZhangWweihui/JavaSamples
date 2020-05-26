package com.zwh.spi.impl;

import com.zwh.spi.Robot;

/**
 * @author ZhangWeihui
 * @date 2019/6/21 10:17
 */
public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
