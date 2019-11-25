package com.zwh.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ZhangWeihui
 * @date 2019/10/24 16:10
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("RealSubject#request " + System.currentTimeMillis());
    }

    @Override
    public String response() {
        System.out.println("RealSubject#response " + System.currentTimeMillis());
        return "ack";
    }

    public static void main(String[] args) {
        final RealSubject realSubject = new RealSubject();
        Class clazz = RealSubject.class;

//        Subject subject = (Subject) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
//                (Object proxy, Method method, Object[] params) -> {
//                    System.out.println("Befor calling : " + method);
//                    method.invoke(realSubject, params);
//                    System.out.println("After calling : " + method);
//                    return null;
//                });
        Subject subject = (Subject) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                new RealSubjectInvocationHandler(realSubject));

        subject.response();
        subject.request();
    }
}
