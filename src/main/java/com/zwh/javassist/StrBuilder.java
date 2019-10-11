package com.zwh.javassist;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhangWeihui
 * @date 2019/9/10 16:52
 */
@Slf4j
public class StrBuilder {

    public String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }

}
