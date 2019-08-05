package com.zwh.leetcode;

import java.math.BigInteger;

/**
 * @author ZhangWeihui
 * @date 2019/8/2 18:44
 */
public class CountHammingDistanceAction {

    public int hammingDistance(int x, int y) {
        int z = x ^ y;
        int count = 0;
        while (z!=0) {
            if(z%2==1){
                count++;
            }
            z = z/2;
        }
        return count;
    }
}
