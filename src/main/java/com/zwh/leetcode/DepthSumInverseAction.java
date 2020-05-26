package com.zwh.leetcode;

import com.zwh.leetcode.interfaces.NestedInteger;
import com.zwh.leetcode.interfaces.NestedIntegerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 加权嵌套序列求和，最外层权重最大
 *
 * https://leetcode-cn.com/problems/nested-list-weight-sum-ii/
 *
 * @author ZhangWeihui
 * @date 2019/8/2 11:20
 */
public class DepthSumInverseAction {

    private int maxLevel = 0; //嵌套集合的最大层数
    private int sum = 0; //加权求和值

    public int depthSumInverse(List<NestedInteger> nestedList) {
        countLevels(nestedList,0);
        sum(nestedList, maxLevel);
        return sum;
    }

    /**
     * 计算嵌套集合的层数，递归运算
     * @param nestedList 嵌套集合
     * @param level  上层集合所在的层数，最外层集合时level值为 0
     */
    public void countLevels(List<NestedInteger> nestedList, int level) {
        if(!nestedList.isEmpty()){
            level++;
        }
        boolean isNested = false;
        for(NestedInteger ni : nestedList){
            if(!ni.isInteger()) {
                isNested = true;
                countLevels(ni.getList(), level);
            }
        }
        if (!isNested) {
            if(level > maxLevel){
                maxLevel = level;
            }
        }
    }

    /**
     * 求和，递归运算
     * @param nestedList 嵌套集合
     * @param weightedValue 最外层集合的权重值
     */
    public void sum(List<NestedInteger> nestedList, int weightedValue) {
        for(NestedInteger ni : nestedList){
            if(ni.isInteger()){
                sum += weightedValue * ni.getInteger();
            } else {
                sum(ni.getList(), weightedValue - 1);
            }
        }
    }

//    private void countLevels(NestedInteger nestedInteger, AtomicInteger level){
//        boolean isNested = false;
//        for(NestedInteger ni : nestedInteger.getList()){
//            if(!ni.isInteger()){
//                isNested = true;
//                break;
//            }
//        }
//        if(isNested){
//            level.incrementAndGet();
//            for(NestedInteger ni : nestedInteger.getList()){
//                if(!ni.isInteger()) {
//                    countLevels(ni, level);
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        List<NestedInteger> nis = new ArrayList<>();
//        NestedInteger n1 = new NestedIntegerImpl(1);
//        nis.add(n1);
//        NestedInteger n4 = new NestedIntegerImpl(new int[]{7});
//        NestedInteger n5 = new NestedIntegerImpl(new int[]{8});
//        NestedInteger n6 = new NestedIntegerImpl(new int[]{9});
//        NestedInteger n3 = new NestedIntegerImpl(new int[]{6});
//        n3.add(n4);
//        n3.add(n5);
//        n5.add(n6);
//        NestedInteger n2 = new NestedIntegerImpl(new int[]{4});
//        n2.add(n3);
//        nis.add(n2);

//        NestedInteger n1 = new NestedIntegerImpl(new int[]{1,1});
//        NestedInteger n2 = new NestedIntegerImpl(2);
//        NestedInteger n3 = new NestedIntegerImpl(new int[]{1,1});
//        nis.add(n1);
//        nis.add(n2);
//        nis.add(n3);

        NestedInteger n1 = new NestedIntegerImpl(1);
        nis.add(n1);
        NestedInteger n3 = new NestedIntegerImpl(new int[]{6});
        NestedInteger n2 = new NestedIntegerImpl(new int[]{4});
        n2.add(n3);
        nis.add(n2);

        DepthSumInverseAction action = new DepthSumInverseAction();
        action.countLevels(nis,0);
        System.out.println("层数：" + action.maxLevel);

        System.out.println("求和：" + action.depthSumInverse(nis));
    }
}
