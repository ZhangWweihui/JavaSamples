package com.zwh.designpattern.factory;

import org.apache.commons.codec.language.bm.Rule;

public class RuleConfigSource {

    public RuleConfig load(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParser parser = RuleConfigParserFactory.createParser(ruleConfigFileExtension);
        if (parser == null) {
            throw new InvalidRuleConfigException(
                    "Rule config file format is not supported: " + ruleConfigFilePath);
        }
        System.out.println(parser.getClass());
        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return filePath.toLowerCase().substring(filePath.lastIndexOf(".")+1);
    }

    public static void main(String[] args) {
        RuleConfigSource ruleConfigSource = new RuleConfigSource();
        ruleConfigSource.load("123.yml");
    }
}
