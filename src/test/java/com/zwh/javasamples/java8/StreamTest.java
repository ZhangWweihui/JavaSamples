package com.zwh.javasamples.java8;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class StreamTest {

    /**
     * 读取一个文件统计其中长单词的数量
     * 长度超过12的算长单词
     */
    //@Test
    public void test1() throws Exception {
        URI uri = StreamTest.class.getClassLoader().getResource("article.txt").toURI();
        log.info(uri.toString());
        String content = new String(Files.readAllBytes(Paths.get(uri)),
                StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(content.split("[\\P{L}]+"));
        log.info(words.toString());
        long count = words.stream().filter(w -> w.length()>10).count();
        log.info("count : {}", count);
    }

    @Test
    public void test2() throws Exception {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        //nums = nums.stream().filter(n -> n<5).collect(Collectors.toList());
        nums = ListUtils.select(nums, n -> n>5);
        log.info("nums : {}", nums);
    }
}
