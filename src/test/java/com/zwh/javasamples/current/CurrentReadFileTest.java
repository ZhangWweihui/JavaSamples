package com.zwh.javasamples.current;

import com.zwh.utils.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessControlContext;
import java.security.ProtectionDomain;
import java.util.concurrent.CountDownLatch;

/**
 * @author ZhangWeihui
 * @date 2019/8/1 16:58
 */
public class CurrentReadFileTest {

//    @Test
//    public void testReadFile() throws IOException {
//        String filePath = "F:\\stdout.txt";
//        ByteArrayOutputStream byteArrayOutputStream = FileUtils.readFile(filePath);
//        String str = new String(byteArrayOutputStream.toByteArray());
//        System.out.println(str);
//    }

    private String filePath;
    private final CountDownLatch countDownLatch = new CountDownLatch(3);
    private long totalTime;

    public synchronized void addTotalTime (long time) {
        this.totalTime += time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public static class ReadFileRunnable implements Runnable {

        private CurrentReadFileTest test;

        private ReadFileRunnable(CurrentReadFileTest test) {
            this.test = test;
        }

        @Override
        public void run() {
            try {
                long start = System.currentTimeMillis();
                FileUtils.readFile(test.getFilePath());
                long costTime = System.currentTimeMillis() - start;
                System.out.println(Thread.currentThread().getName() + "用时：" + costTime);
                test.addTotalTime(costTime);
                test.getCountDownLatch().countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String[] paths = new String[]{"F:\\供应商系统功能梳理.xlsx","F:\\供应商新增工单.zip","F:\\进销存1.5期2019-1-8.zip"};
        CurrentReadFileTest test = new CurrentReadFileTest();
        for(int i=0;i<3;i++){
            test.setFilePath(paths[i]);
            new Thread(new ReadFileRunnable(test), "Thread-"+i).start();
        }
        try {
            test.countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("multi thread 总用时：" + test.getTotalTime());

        long totalTime = 0;
        try {
            for(String s : paths) {
                long start = System.currentTimeMillis();
                FileUtils.readFile(s);
                long costTime = System.currentTimeMillis() - start;
                System.out.println(Thread.currentThread().getName() + "用时：" + costTime);
                totalTime += costTime;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("single thread 总用时：" + totalTime);
    }
}
