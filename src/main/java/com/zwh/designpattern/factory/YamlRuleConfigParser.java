package com.zwh.designpattern.factory;

public class YamlRuleConfigParser implements IRuleConfigParser {

    @Override
    public String getSuffix() {
        return "yml";
    }

    @Override
    public RuleConfig parse(String context) {
        return null;
    }
}
