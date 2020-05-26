package com.zwh.javasamples;

import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author 张炜辉
 * @Date 2020/5/26
 */
public class TypeTest {

    /**
     * 测试获取类的泛型类型的方法
     * 1、只能获得类定义时设定的泛型类型
     * 2、如果要改变类的泛型类型，比如 E，可以覆盖这个类型
     * 3、内部匿名类的实现会在编译时生成一个新的类，并继承原来的类型，创建的对象是新类的对象
     */
    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        Type t = list.getClass().getGenericSuperclass();
        Type t1 = ((ParameterizedType)t).getActualTypeArguments()[0];
        System.out.printf("t: %s, t1: %s \n", t, t1);
        TypeVariable[] tvs = list.getClass().getTypeParameters();
        System.out.printf("tvs: %s \n", tvs[0].getTypeName());
        String tname = list.getClass().getTypeName();
        System.out.printf("tname: %s \n", tname);

        TypeReference<Map<String,String>> tf = new TypeReference<Map<String,String>>(){};
        System.out.printf("tf: %s \n", tf.getType().getTypeName());

        List<Integer> list1 = new ArrayList<Integer>() {
            @Override
            public boolean add(Integer i) {
                return true;
            }
        };
        System.out.printf("list1 type parameters: %s \n", Arrays.toString(list1.getClass().getTypeParameters()));
        t = list1.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType)t).getActualTypeArguments();
        System.out.printf("list1 superclass type parameters: %s \n", Arrays.toString(types));
    }
}
