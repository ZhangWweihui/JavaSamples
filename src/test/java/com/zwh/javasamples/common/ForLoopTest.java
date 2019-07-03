package com.zwh.javasamples.common;

import org.junit.Test;

/**
 * @Description
 * @Author 张炜辉
 * @Date 2019/6/22
 */
public class ForLoopTest {

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++){
            System.out.println("i : "+i);
        }

        for (int m = 0; m < 10; ++m){
            System.out.println("m : "+m);
        }
    }
}
