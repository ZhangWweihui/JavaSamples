package com.zwh.sdk;

import java.io.Serializable;

/**
 * @Description
 * @Author 张炜辉
 * @Date 2019/6/16
 */
public class PersonImpl implements Person,Serializable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
