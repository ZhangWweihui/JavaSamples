package com.zwh.leetcode;

import com.zwh.utils.AESUtils;
import com.zwh.utils.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/**
 * TinyURL 的加密与解密
 *
 * https://leetcode-cn.com/problems/encode-and-decode-tinyurl/
 *
 * @author ZhangWeihui
 * @date 2019/9/3 11:10
 */
public class TinyURLCodec {

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "https://leetcode.com/problems/design-tinyurl";
        System.out.println(cSrc);

        // 加密
        String enString = AESUtils.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AESUtils.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);

        System.out.println("SHA-256z摘要字符串是：" + DigestUtils.encode(cSrc, MessageDigestAlgorithms.SHA_256));

        System.out.println("MD5摘要字符串是：" + DigestUtils.encode(cSrc, MessageDigestAlgorithms.MD5));
    }
}
