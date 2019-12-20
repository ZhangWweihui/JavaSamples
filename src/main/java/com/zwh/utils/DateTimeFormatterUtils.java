package com.zwh.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author ZhangWeihui
 * @date 2019/6/11 18:29
 */
public class DateTimeFormatterUtils {

    private static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 格式化带时分秒的日期
     * Date --> Instant --> LocalDateTime --> String
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return YMDHMS.format(localDateTime);
    }

    /**
     * 格式化不带时分秒的日期
     * Date --> Instant --> ZonedDateTime --> LocalDate --> String
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return YMD.format(localDate);
    }

    /**
     * 转换带时分秒的日期字符串
     * String--> LocalDateTime --> Instant --> Date
     *
     * @param dateStr
     * @param formatter YMDHMS
     * @return
     */
    public static Date parseDateTime(String dateStr, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 转换不带时分秒的字符串
     * String --> LocalDate --> Instant --> Date
     *
     * @param dateStr
     * @param formatter YMD
     * @return
     */
    public static Date parseDate(String dateStr, DateTimeFormatter formatter){
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static String formatDate2(LocalDateTime date) {
        return YMDHMS.format(date);
    }

    public static LocalDateTime parse2(String dateNow) {
        return LocalDateTime.parse(dateNow, YMDHMS);
    }

    public static void main(String[] args) throws InterruptedException, ParseException {
        //ExecutorService service = Executors.newFixedThreadPool(100);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService service = new ThreadPoolExecutor(5, 50,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

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
