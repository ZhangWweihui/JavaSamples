package com.zwh.designpattern.factory;

public class InvalidRuleConfigException extends RuntimeException {

    public InvalidRuleConfigException(String message){
        super(message);
    }
}
