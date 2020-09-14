package com.zwh.javasamples.spring;

import com.zwh.model.User;
import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class UserTest {

    @Test
    public void test1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:appContext.xml");
        User user = applicationContext.getBean(User.class);
        User.UserFactory2 userFactory = (User.UserFactory2)applicationContext.getBean("&user");
        System.out.println("object type : " + userFactory.getObjectType());
        System.out.println("is singleton : " + userFactory.isSingleton());
        System.out.println(user);

        String[] aliases = applicationContext.getAliases("user");
        System.out.println("aliases : " + StringUtils.arrayToCommaDelimitedString(aliases));

        User user1 = applicationContext.getBean("user3", User.class);
        System.out.println("user1 : " + user1);
    }
}
