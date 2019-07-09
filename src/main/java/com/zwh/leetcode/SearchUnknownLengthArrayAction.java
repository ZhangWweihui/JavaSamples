package com.zwh.leetcode;

/**
 * 702. 搜索长度未知的有序数组
 * 给定一个升序整数数组，写一个函数搜索 nums 中数字 target。如果 target 存在，返回它的下标，否则返回 -1。注意，这个数组的大小是未知的。你只可以通过 ArrayReader 接口访问这个数组，ArrayReader.get(k) 返回数组中第 k 个元素（下标从 0 开始）。
 *
 * 你可以认为数组中所有的整数都小于 10000。如果你访问数组越界，ArrayReader.get 会返回 2147483647。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-in-a-sorted-array-of-unknown-size
 *
 * @author ZhangWeihui
 * @date 2019/7/9 10:07
 */
public class SearchUnknownLengthArrayAction {

    public static int search(ArrayReader reader, int target) {
        int i = 0;
        int n = reader.get(i);
        while(n!=target && n!=Integer.MAX_VALUE){
            n = reader.get(++i);
        }
        return n==target ? i : -1;
    }

    public static class ArrayReader {

        private int[] nums;

        public ArrayReader(int[] nums){
            this.nums = nums;
        }

        public int get(int i){
            if(i<0 || i>nums.length-1){
                return Integer.MAX_VALUE;
            }
            return nums[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2};
        ArrayReader arrayReader = new ArrayReader(nums);
        System.out.println(search(arrayReader, 2));
    }
}
