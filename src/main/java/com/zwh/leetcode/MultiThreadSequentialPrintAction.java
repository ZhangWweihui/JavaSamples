package com.zwh.leetcode;

import java.util.concurrent.TimeUnit;

/**
 * 多线程按序打印
 *
 * https://leetcode-cn.com/problems/print-in-order/
 *
 * @author ZhangWeihui
 * @date 2019/8/1 10:50
 */
public class MultiThreadSequentialPrintAction {

    private boolean isFirstEnd = false;
    private boolean isSecondEnd = false;
    private Object lock = new Object();

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            isFirstEnd = true;
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            if (!isFirstEnd) {
                lock.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            isSecondEnd = true;
            lock.notifyAll();
            Thread.sleep(1);
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
            if (!isSecondEnd) {
                lock.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
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
                PrintRunnable r1 = new PrintRunnable(1);
                PrintRunnable r2 = new PrintRunnable(2);
                PrintRunnable r3 = new PrintRunnable(3);
                MultiThreadSequentialPrintAction action = new MultiThreadSequentialPrintAction();
                action.first(r1);
                action.second(r2);
                action.third(r3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
