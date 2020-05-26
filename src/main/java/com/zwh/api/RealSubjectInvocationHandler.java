package com.zwh.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ZhangWeihui
 * @date 2019/10/24 16:36
 */
public class RealSubjectInvocationHandler implements InvocationHandler {

    private RealSubject realSubject;

    public RealSubjectInvocationHandler(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("request".equals(method.getName())) {
            request(method, args);
        } else if ("response".equals(method.getName())) {
            response(method, args);
        }
        return null;
    }

    private void request(Method method, Object[] params) throws Throwable {
        System.out.println("Befor request : " + System.currentTimeMillis());
        Object object = method.invoke(realSubject, params);
        System.out.println("After request, the result is : " + object);
    }

    private void response(Method method, Object[] params) throws Throwable {
        System.out.println("Befor response : " + System.currentTimeMillis());
        String result = (String) method.invoke(realSubject, params);
        System.out.println("After response, the result is : " + result );
    }
}
