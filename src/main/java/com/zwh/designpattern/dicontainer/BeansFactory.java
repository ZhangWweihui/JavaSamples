package com.zwh.designpattern.dicontainer;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BeansFactory {

    private ConcurrentHashMap<String, Object> singletons = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
        for(BeanDefinition beanDefinition : beanDefinitionList) {
            beanDefinitionMap.putIfAbsent(beanDefinition.getId(), beanDefinition);
        }

        for(BeanDefinition beanDefinition : beanDefinitionList) {
            if(!beanDefinition.isLazyInit() && beanDefinition.isSingleton()) {
                createBean(beanDefinition);
            }
        }
    }

    public Object getBean(String id) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(id);
        if(beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("there's no bean definition of " + id);
        }
        return createBean(beanDefinition);
    }

    @VisibleForTesting
    protected Object createBean(BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            if(beanDefinition.isSingleton() && singletons.contains(beanDefinition.getId())) {
                return singletons.get(beanDefinition.getId());
            }
            Class clazz = Class.forName(beanDefinition.getClassName());

            if(CollectionUtils.isEmpty(beanDefinition.getConstructorArgs())) {
                bean = clazz.newInstance();
            } else {
                List<Class> classes = new ArrayList<>();
                List<Object> args = new ArrayList<>();
                for(ConstructorArg constructorArg : beanDefinition.getConstructorArgs()) {
                    if(StringUtils.hasText(constructorArg.getType())) {
                        Class type = ConstructorArgUtils.getClassForPrimitive(constructorArg.getType());
                        if(type==null) {
                            throw new RuntimeException("can't recognize the type " + constructorArg.getType());
                        }
                        classes.add(type);
                        args.add(ConstructorArgUtils.valueOf(type, constructorArg.getValue()));
                    } else if (StringUtils.hasText(constructorArg.getRef())) {
                        BeanDefinition refBeanDefinition = beanDefinitionMap.get(constructorArg.getRef());
                        if(refBeanDefinition==null){
                            throw new NoSuchBeanDefinitionException("bean definition not found : " + constructorArg.getRef());
                        }
                        classes.add(Class.forName(refBeanDefinition.getClassName()));
                        args.add(createBean(refBeanDefinition));
                    }
                }
                bean = clazz.getConstructor(classes.toArray(new Class[classes.size()])).newInstance(args.toArray());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bean!=null && beanDefinition.isSingleton()) {
            singletons.putIfAbsent(beanDefinition.getId(), bean);
        }
        return bean;
    }
}
