package com.zwh.designpattern.factory;

public class XmlRuleConfigParser implements IRuleConfigParser {

    @Override
    public String getSuffix() {
        return "xml";
    }

    @Override
    public RuleConfig parse(String context) {
        return null;
    }
}
