package com.zwh.designpattern.dicontainer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BeanDefinition {

    private String id;
    private String className;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public BeanDefinition() { }

    public BeanDefinition(String id, String className, boolean lazyInit, List<ConstructorArg> constructorArgs) {
        this.id = id;
        this.className = className;
        this.lazyInit = lazyInit;
        this.constructorArgs = constructorArgs;
    }

    public boolean isSingleton() {
        return scope.equals(Scope.SINGLETON);
    }
}
