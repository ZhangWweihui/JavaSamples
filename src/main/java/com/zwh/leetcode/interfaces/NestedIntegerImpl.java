package com.zwh.leetcode.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2019/8/2 13:41
 */
public class NestedIntegerImpl implements NestedInteger {

    private boolean isInteger;
    private Integer integer;
    private List<NestedInteger> nestedIntegers;

    public NestedIntegerImpl(int num){
        this.integer = num;
        this.isInteger = true;
    }

    public NestedIntegerImpl(int[] nums){
        this.nestedIntegers = new ArrayList<>();
        Arrays.stream(nums).forEach(n -> nestedIntegers.add(new NestedIntegerImpl(n)));
        this.isInteger = false;
    }

    @Override
    public boolean isInteger() {
        return this.isInteger;
    }

    @Override
    public Integer getInteger() {
        return isInteger ? integer : null;
    }

    @Override
    public void setInteger(int value) {
        this.integer = value;
        this.isInteger = true;
    }

    @Override
    public void add(NestedInteger ni) {
        if(this.nestedIntegers==null){
            this.nestedIntegers = new ArrayList<>();
        }
        this.nestedIntegers.add(ni);
        this.isInteger = false;
    }

    @Override
    public List<NestedInteger> getList() {
        return this.nestedIntegers;
    }
}
