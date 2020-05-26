package com.zwh.leetcode;

import java.util.concurrent.CountDownLatch;

/**
 * 多线程按序打印
 *
 * https://leetcode-cn.com/problems/print-in-order/
 *
 * @author ZhangWeihui
 * @date 2019/8/1 16:45
 */
public class MultiThreadSequentialPrintAction2 {

    private final CountDownLatch latch1 = new CountDownLatch(1);
    private final CountDownLatch latch2 = new CountDownLatch(1);

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        latch1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latch1.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        latch2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch2.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

    public static class PrintRunnable implements Runnable {

        private int number;

        public PrintRunnable(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            switch (number) {
                case 1 : System.out.println("one");
                    break;
                case 2 : System.out.println("two");
                    break;
                case 3 : System.out.println("three");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0; i<3; i++) {
            try {
                MultiThreadSequentialPrintAction.PrintRunnable r1 = new MultiThreadSequentialPrintAction.PrintRunnable(1);
                MultiThreadSequentialPrintAction.PrintRunnable r2 = new MultiThreadSequentialPrintAction.PrintRunnable(2);
                MultiThreadSequentialPrintAction.PrintRunnable r3 = new MultiThreadSequentialPrintAction.PrintRunnable(3);
                MultiThreadSequentialPrintAction2 action = new MultiThreadSequentialPrintAction2();
                action.first(r1);
                action.second(r2);
                action.third(r3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
