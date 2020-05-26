package com.zwh.leetcode;

/**
 * 1108. IP 地址无效化
 *
 * 给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
 * 所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
 *
 * 示例 1：
 * 输入：address = "1.1.1.1"
 * 输出："1[.]1[.]1[.]1"
 *
 * 示例 2：
 * 输入：address = "255.100.50.0"
 * 输出："255[.]100[.]50[.]0"
 *  
 * 提示：给出的 address 是一个有效的 IPv4 地址
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/defanging-an-ip-address
 *
 * @author ZhangWeihui
 * @date 2019/7/8 19:10
 */
public class InvalidateIPAddressAction {

    public static String defangIPaddr(String address) {
        char[] c1 = address.toCharArray();
        char[] c2 = new char[c1.length+6];
        int dotCount = 0;
        for(int i=0;i<c1.length;i++){
            if(c1[i]=='.'){
                c2[dotCount*2+i] = '[';
                c2[dotCount*2+i+1] = '.';
                c2[dotCount*2+i+2] = ']';
                dotCount++;
            }else{
                c2[dotCount*2+i] = c1[i];
            }
        }
        return new String(c2);
    }

    public static void main(String[] args) {
        String ip = "23.45.23.57";
        System.out.println(defangIPaddr(ip));
    }
}
