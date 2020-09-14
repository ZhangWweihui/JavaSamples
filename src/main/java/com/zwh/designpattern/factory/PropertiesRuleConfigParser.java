package com.zwh.designpattern.factory;

public class PropertiesRuleConfigParser implements IRuleConfigParser {

    @Override
    public String getSuffix() {
        return "properties";
    }

    @Override
    public RuleConfig parse(String context) {
        return null;
    }
}
