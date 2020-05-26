package com.zwh.leetcode;

import java.util.concurrent.Semaphore;

/**
 * @author ZhangWeihui
 * @date 2019/8/1 19:14
 */
public class PrintFooBarAction {

    private int n;
    private final Semaphore semaphoreFoo = new Semaphore(0);
    private final Semaphore semaphoreBar = new Semaphore(0);

    public PrintFooBarAction(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            //semaphore.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            semaphoreBar.release();
            semaphoreFoo.acquire();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            semaphoreBar.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            semaphoreFoo.release();
        }
    }

    public static void main(String[] args){
        PrintFooBarAction action = new PrintFooBarAction(3);
        new Thread(()-> {
            try {
                action.foo(() -> System.out.println("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()-> {
            try {
                action.bar(()-> System.out.println("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
