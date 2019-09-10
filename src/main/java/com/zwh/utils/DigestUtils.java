package com.zwh.utils;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ZhangWeihui
 * @date 2019/9/3 11:15
 */
public class DigestUtils {

    /**
         * 利用java原生的类实现SHA256加密
         * @param str 加密后的报文
         * @param digestAlgorithms 摘要算法
         * @return
         */
    public static String encode(String str, String digestAlgorithms){
        MessageDigest messageDigest;
        String encodestr = null;
        try {
            messageDigest = MessageDigest.getInstance(digestAlgorithms);
            messageDigest.update(str.getBytes(Charset.forName("UTF-8")));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
