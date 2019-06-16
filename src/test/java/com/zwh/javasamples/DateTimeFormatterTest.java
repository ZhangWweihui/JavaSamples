package com.zwh.javasamples;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangWeihui
 * @date 2019/6/11 18:29
 */
public class DateTimeFormatterTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 从 Date --> Instant --> LocalDateTime --> String
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return formatter.format(localDateTime);
    }

    public static String formatDate2(LocalDateTime date) {
        return formatter.format(date);
    }

    public static LocalDateTime parse2(String dateNow) {
        return LocalDateTime.parse(dateNow, formatter);
    }

    /**
     * 从 String--> LocalDateTime --> Instant --> Date
     *
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static void main(String[] args) throws InterruptedException, ParseException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        // 20个线程
        for (int i = 0; i < 20; i++) {
            service.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(parse2(formatDate2(LocalDateTime.now())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // 等待上述的线程执行完
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
    }
}
