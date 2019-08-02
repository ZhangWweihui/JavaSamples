package com.zwh.leetcode.interfaces;

import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2019/8/2 11:11
 */
// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
public interface NestedInteger {

     // Constructor initializes an empty nested list.
     // public NestedInteger();

     // Constructor initializes a single integer.
     // public NestedInteger(int value);

      // @return true if this NestedInteger holds a single integer, rather than a nested list.
      boolean isInteger();

      // @return the single integer that this NestedInteger holds, if it holds a single integer
      // Return null if this NestedInteger holds a nested list
      Integer getInteger();

      // Set this NestedInteger to hold a single integer.
      void setInteger(int value);

      // Set this NestedInteger to hold a nested list and adds a nested integer to it.
      void add(NestedInteger ni);

      // @return the nested list that this NestedInteger holds, if it holds a nested list
      // Return null if this NestedInteger holds a single integer
      List<NestedInteger> getList();
}
