package com.zwh.leetcode;

import java.math.BigInteger;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * 示例 1:
 * 输入: 123
 * 输出: 321
 *
 * 示例 2:
 * 输入: -123
 * 输出: -321
 *
 * 示例 3:
 * 输入: 120
 * 输出: 21
 *
 * 注意:
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-integer
 *
 * @author ZhangWeihui
 * @date 2019/7/8 15:39
 */
public class IntegerReverseAction {

    public static int reverse(int x) {
        if(x>=0 && x<10){
            return x;
        }
        if(x<=Integer.MIN_VALUE || x>=Integer.MAX_VALUE){
            return 0;
        }
        String str = String.valueOf(Math.abs(x));
        char[] chars = new char[str.length()];
        int len = str.length();
        for(int i=0;i<len;i++){
            chars[i] = str.charAt(len-i-1);
        }
        String newStr = new String(chars);
        BigInteger b = new BigInteger(newStr);
        if(b.subtract(BigInteger.valueOf(Integer.MAX_VALUE)).compareTo(BigInteger.ZERO)>0){
            return 0;
        }else {
            return x >= 0 ? b.intValue() : Math.negateExact(b.intValue());
        }
    }

    public static void main(String[] args) {
        System.out.println(IntegerReverseAction.reverse(-2147483647));
    }
}
