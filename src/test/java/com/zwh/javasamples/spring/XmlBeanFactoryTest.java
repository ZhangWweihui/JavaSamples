package com.zwh.javasamples.spring;

import com.alibaba.fastjson.JSON;
import com.zwh.model.User;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.util.Map;

public class XmlBeanFactoryTest {

    @Test
    public void test1() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("appContext.xml"));
        System.out.println("bean definition names : " + StringUtils.arrayToCommaDelimitedString(beanFactory.getBeanDefinitionNames()));
        System.out.println("bean definition count : " + beanFactory.getBeanDefinitionCount());

        Map<String, User> userBeanMap = beanFactory.getBeansOfType(User.class);
        System.out.println("user bean map : " + JSON.toJSONString(userBeanMap));
    }
}
