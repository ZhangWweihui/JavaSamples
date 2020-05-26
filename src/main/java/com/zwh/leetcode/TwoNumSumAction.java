package com.zwh.leetcode;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，
 * 并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 *
 * @author ZhangWeihui
 * @date 2019/7/5 17:09
 */
public class TwoNumSumAction {

    /**
     * 解法一
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal ten = BigDecimal.TEN;
        int index = 0;
        do {
            sum = sum.add(BigDecimal.valueOf(l1.val).multiply(ten.pow(index++)));
            l1 = l1.next;
        }while(l1!=null);

        index = 0;
        do {
            sum = sum.add(BigDecimal.valueOf(l2.val).multiply(ten.pow(index++)));
            l2 = l2.next;
        }while(l2!=null);

        System.out.println(sum.toPlainString());
        char[] chars = sum.setScale(0).toPlainString().toCharArray();
        ListNode firstNode = null;
        ListNode preNode = null;
        for(int i=chars.length-1;i>=0;i--){
            ListNode ln = new ListNode(chars[i]-48);
            if(i==chars.length-1){
                firstNode = ln;
                preNode = ln;
            } else {
                preNode.next = ln;
                preNode = ln;
            }
        }
        return firstNode;
    }

    /**
     * 解法二，比解法一效率高
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        StringBuffer buf = new StringBuffer();
        do {
            buf.append(l1.val);
            l1 = l1.next;
        }while(l1!=null);
        buf.reverse();
        System.out.println(buf);
        BigDecimal n1 = new BigDecimal(buf.toString());

        buf.delete(0,buf.length());
        do {
            buf.append(l2.val);
            l2 = l2.next;
        }while(l2!=null);
        buf.reverse();
        System.out.println(buf);
        BigDecimal n2 = new BigDecimal(buf.toString());

        char[] chars = n1.add(n2).setScale(0).toPlainString().toCharArray();
        ListNode firstNode = null;
        ListNode preNode = null;
        for(int i=chars.length-1;i>=0;i--){
            ListNode ln = new ListNode(chars[i]-48);
            if(i==chars.length-1){
                firstNode = ln;
                preNode = ln;
            } else {
                preNode.next = ln;
                preNode = ln;
            }
        }
        return firstNode;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode build(String numStr){
        String[] chars = StringUtils.commaDelimitedListToStringArray(numStr);
        ListNode firstNode = null;
        ListNode preNode = null;
        for(int i=chars.length-1;i>=0;i--){
            ListNode ln = new ListNode(new Integer(chars[i]));
            if(i==chars.length-1){
                firstNode = ln;
                preNode = ln;
            } else {
                preNode.next = ln;
                preNode = ln;
            }
        }
        return firstNode;
    }

    public static void main(String[] args) {
        ListNode n1 = build("2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,9");
        ListNode n2 = build("5,6,4,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,2,4,3,9,9,9,9");
        ListNode sumNode = addTwoNumbers2(n1,n2);
        do{
            System.out.println(sumNode.val);
            sumNode = sumNode.next;
        } while(sumNode!=null);
    }
}
