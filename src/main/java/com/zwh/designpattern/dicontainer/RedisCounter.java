package com.zwh.designpattern.dicontainer;

import lombok.ToString;

@ToString
public class RedisCounter {

    private String ipAddress;

    private Integer port;

    public RedisCounter(String ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

}
