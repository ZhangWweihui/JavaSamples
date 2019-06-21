package com.zwh.spi.impl;

import com.zwh.spi.Robot;

/**
 * @author ZhangWeihui
 * @date 2019/6/21 10:15
 */
public class OptimusPrime implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}
