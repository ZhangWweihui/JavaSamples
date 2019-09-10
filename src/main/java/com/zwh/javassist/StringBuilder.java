package com.zwh.javassist;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhangWeihui
 * @date 2019/9/10 16:52
 */
@Slf4j
public class StringBuilder {

    private String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }

    public static void main(String[] argv) {
        StringBuilder inst = new StringBuilder();
        for (int i = 0; i < argv.length; i++) {
            String result = inst.buildString(Integer.parseInt(argv[i]));
            log.info("Constructed string [{}] of length [{}]", result, result.length());
        }
    }
}
