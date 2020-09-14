package com.zwh.designpattern.dicontainer;

import lombok.ToString;

@ToString
public class RateLimiter {

    private RedisCounter redisCounter;

    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }

    public void test1() {
        System.out.println("Hello World!");
    }

}
