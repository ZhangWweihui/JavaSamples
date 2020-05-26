package com.zwh.leetcode;

/**
 * 1064. 不动点
 *
 * 给定已经按升序排列、由不同整数组成的数组 A，返回满足 A[i] == i 的最小索引 i。如果不存在这样的 i，返回 -1。
 * https://leetcode-cn.com/problems/fixed-point/
 *
 * @author ZhangWeihui
 * @date 2019/7/8 20:16
 */
public class FindFixedPointAction {

    public static int fixedPoint(int[] A) {
        int low = 0, high = A.length-1, mid;
        while(low <= high){
            mid = low + (high - low) / 2;
            if(A[mid] >= mid){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        if(low>A.length-1 || A[low]!=low){
            return -1;
        }else {
            return low;
        }
    }

    public static void main(String[] args) {
        int[] A = new int[]{-2,-1,0,3,4,5,6,7,8,9};
        System.out.println(fixedPoint(A));
    }
}
