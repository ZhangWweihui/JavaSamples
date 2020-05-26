package com.zwh.leetcode;

import com.zwh.leetcode.interfaces.NestedInteger;
import com.zwh.leetcode.interfaces.NestedIntegerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 嵌套列表求和，最外层权重为 1，每深入一层权重加 1
 *
 * https://leetcode-cn.com/problems/nested-list-weight-sum/
 *
 * @author ZhangWeihui
 * @date 2019/8/2 17:30
 */
public class DepthSumAction {

    public int depthSum(List<NestedInteger> nestedList) {
        return sum(nestedList, 1);
    }

    private int sum(List<NestedInteger> nestedList, int weight) {
        int n = 0;
        if(nestedList.isEmpty()){
            return 0;
        }
        for(NestedInteger ni : nestedList){
            if(ni.isInteger()){
                n += ni.getInteger() * weight;
            } else {
                n += sum(ni.getList(), weight + 1);
            }
        }
        return n;
    }

    public static void main(String[] args) {
        List<NestedInteger> nis = new ArrayList<>();
        NestedInteger n1 = new NestedIntegerImpl(1);
        nis.add(n1);
        NestedInteger n4 = new NestedIntegerImpl(new int[]{7});
        NestedInteger n5 = new NestedIntegerImpl(new int[]{8});
        NestedInteger n6 = new NestedIntegerImpl(new int[]{9});
        NestedInteger n3 = new NestedIntegerImpl(new int[]{6});
        n3.add(n4);
        n3.add(n5);
        n5.add(n6);
        NestedInteger n2 = new NestedIntegerImpl(new int[]{4});
        n2.add(n3);
        nis.add(n2);

//        NestedInteger n1 = new NestedIntegerImpl(new int[]{1,1});
//        NestedInteger n2 = new NestedIntegerImpl(2);
//        NestedInteger n3 = new NestedIntegerImpl(new int[]{1,1});
//        nis.add(n1);
//        nis.add(n2);
//        nis.add(n3);

//        NestedInteger n1 = new NestedIntegerImpl(1);
//        nis.add(n1);
//        NestedInteger n3 = new NestedIntegerImpl(new int[]{6});
//        NestedInteger n2 = new NestedIntegerImpl(new int[]{4});
//        n2.add(n3);
//        nis.add(n2);

        DepthSumAction action = new DepthSumAction();

        System.out.println("求和：" + action.depthSum(nis));
    }
}
