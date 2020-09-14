package com.zwh.designpattern.dicontainer;

public class ApplicationContextTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans1.xml");
        RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
        System.out.println(rateLimiter);
    }
}
