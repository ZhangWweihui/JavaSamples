package com.zwh.designpattern.dicontainer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanConfigParser implements BeanConfigParser {

    @Override
    public List<BeanDefinition> parse(String configContent) {
        return null;
    }

    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);
            List<Node> nodeList = document.getRootElement().selectNodes("//bean");
            if(CollectionUtils.isEmpty(nodeList)) {
                throw new RuntimeException("未找到bean节点");
            }
            List<BeanDefinition> beanDefinitions = new ArrayList<>();
            for(Node node : nodeList) {
                String id = node.valueOf("@id");
                String className = node.valueOf("@class");
                String lazyInitStr = node.valueOf("@lazy-init");
                List<ConstructorArg> constructorArgs = new ArrayList<>();
                List<Node> constructorArgNodes = node.selectNodes("constructor-arg");
                if(!CollectionUtils.isEmpty(constructorArgNodes)){
                    for(Node consArgNode : constructorArgNodes) {
                        String ref = consArgNode.valueOf("@ref");
                        String type = consArgNode.valueOf("@type");
                        String value = consArgNode.valueOf("@value");
                        constructorArgs.add(new ConstructorArg(type, value, ref));
                    }
                }
                boolean lazyInit = StringUtils.hasText(lazyInitStr) ? Boolean.valueOf(lazyInitStr) : false;
                beanDefinitions.add(new BeanDefinition(id,className,lazyInit,constructorArgs));
            }
            return beanDefinitions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
