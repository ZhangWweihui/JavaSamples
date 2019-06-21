package com.zwh.javasamples.spi;

import com.zwh.spi.Robot;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * @author ZhangWeihui
 * @date 2019/6/21 10:20
 */
public class JavaSPITest {

    @Test
    public void test1(){
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
