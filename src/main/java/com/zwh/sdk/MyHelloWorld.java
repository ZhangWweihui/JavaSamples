package com.zwh.sdk;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author ZhangWeihui
 * @date 2019/7/12 16:49
 */
public class MyHelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("---------------------------------------------------");
        System.out.println(Arrays.toString(args));
        System.out.println("---------------------------------------------------");
        printEnv();
        System.out.println("---------------------------------------------------");
        printProperties();
    }

    private static void printEnv() {
        Map<String, String> map = System.getenv();
        for(Iterator<String> itr = map.keySet().iterator(); itr.hasNext();){
            String key = itr.next();
            System.out.println(key + "=" + map.get(key));
        }
    }

    private static void printProperties() {
        Properties props = System.getProperties();
        props.list(System.out);
    }
}
