package com.zwh.leetcode;

import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * 剔除元音字母
 *
 * 给你一个字符串 S，请你删去其中的所有元音字母（ 'a'，'e'，'i'，'o'，'u'），并返回这个新字符串。
 *
 * https://leetcode-cn.com/problems/remove-vowels-from-a-string/
 *
 * @author ZhangWeihui
 * @date 2019/8/2 10:33
 */
public class RemoveVowelsAction {

    private final char[] vowels = new char[]{'a','e','i','o','u'};

    public String removeVowels(String S) {
        char[] chars = S.toCharArray();
        char[] selected = new char[chars.length];
        int count = 0;
        for(int i=0;i<chars.length;i++){
            if(Arrays.binarySearch(vowels, chars[i])>=0){
                count++;
                continue;
            } else {
                selected[i-count] = chars[i];
            }
        }
        return new String(selected).trim();
    }

    public static void main(String[] args) {
        RemoveVowelsAction action = new RemoveVowelsAction();
        String s = action.removeVowels("aeiou");
        Assert.isTrue(s.equals(""), "错误的答案");
    }
}
