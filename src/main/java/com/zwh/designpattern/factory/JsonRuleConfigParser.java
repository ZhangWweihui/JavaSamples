package com.zwh.designpattern.factory;

public class JsonRuleConfigParser implements IRuleConfigParser {

    @Override
    public String getSuffix() {
        return "json";
    }

    @Override
    public RuleConfig parse(String context) {
        return null;
    }
}
