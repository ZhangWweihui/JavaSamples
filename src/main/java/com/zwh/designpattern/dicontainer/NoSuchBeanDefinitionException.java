package com.zwh.designpattern.dicontainer;

public class NoSuchBeanDefinitionException extends RuntimeException {

    public NoSuchBeanDefinitionException (String message) {
        super(message);
    }
}
