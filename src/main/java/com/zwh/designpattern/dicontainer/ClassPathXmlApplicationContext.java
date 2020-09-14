package com.zwh.designpattern.dicontainer;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private BeanConfigParser beanConfigParser;
    private BeansFactory beansFactory;

    public ClassPathXmlApplicationContext(String configFilePath) {
        beanConfigParser = new XmlBeanConfigParser();
        beansFactory = new BeansFactory();
        loadBeanDefinitions(configFilePath);
    }

    private void loadBeanDefinitions(String configFilePath) {
        if(!StringUtils.hasText(configFilePath)) {
            throw  new IllegalArgumentException("the config file path must not be empty");
        }
        try(InputStream inputStream = this.getClass().getResourceAsStream("/" + configFilePath)) {
            if(inputStream == null) {
                throw new RuntimeException("Can not find config file: " + configFilePath);
            }
            List<BeanDefinition> beanDefinitionList = beanConfigParser.parse(inputStream);
            if(CollectionUtils.isEmpty(beanDefinitionList)){
                throw new RuntimeException("parse config file failed.");
            }
            beansFactory.addBeanDefinitions(beanDefinitionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        return beansFactory.getBean(id);
    }
}
