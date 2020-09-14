package com.zwh.designpattern.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SearchWord implements Serializable {

    private static final long serialVersionUID = -6339908336484607782L;
    private String keyword;
    private Long lastUpdateTime;
    private Long count;

    public SearchWord(){}

    public SearchWord(String keyword, Long count, Long lastUpdateTime) {
        this.keyword = keyword;
        this.count = count;
        this.lastUpdateTime = lastUpdateTime;
    }
}
