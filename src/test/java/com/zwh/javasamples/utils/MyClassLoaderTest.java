package com.zwh.javasamples.utils;

import com.zwh.utils.MyClassLoader;
import org.junit.Test;

/**
 * @author ZhangWeihui
 * @date 2019/9/24 17:00
 */
public class MyClassLoaderTest {

    @Test
    public void test1() throws Exception {
        MyClassLoader mcl = new MyClassLoader();
        Class<?> c = Class.forName("com.xrq.classloader.Person", false, mcl);
        Object obj = c.getConstructor(String.class).newInstance("Tom");
        System.out.println(obj);
        System.out.println(obj.getClass().getClassLoader());
    }
}
