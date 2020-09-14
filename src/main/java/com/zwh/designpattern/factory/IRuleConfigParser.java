package com.zwh.designpattern.factory;

public interface IRuleConfigParser {

    String getSuffix();

    RuleConfig parse(String context);
}
