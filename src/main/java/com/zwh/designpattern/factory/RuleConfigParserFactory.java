package com.zwh.designpattern.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.Optional;

/**
 * 简单工厂（Simple Factory）或者叫静态工厂方法（Static Factory Method）
 */
public class RuleConfigParserFactory {

    public static IRuleConfigParser createParser(String configFormat) {
        IRuleConfigParser parser = null;
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("appContext.xml");
        Collection<IRuleConfigParser> ruleConfigParserList = applicationContext.getBeansOfType(IRuleConfigParser.class).values();
        Optional<IRuleConfigParser> ruleConfigParserOptional = ruleConfigParserList.stream().filter(r->r.getSuffix().equalsIgnoreCase(configFormat)).findFirst();
        return ruleConfigParserOptional.orElse(null);
    }

}
