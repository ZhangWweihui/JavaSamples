package com.zwh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangWeihui
 * @date 2019/8/23 15:37
 */
@Slf4j
public class CuratorLock {

    public static void main(String[] args) throws Exception {
        //创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String connectString = "47.104.103.95:2181,47.104.103.95:2182,47.104.103.95:2183";
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();

        Runnable runnable = () -> {
            try {
                InterProcessMutex mutex = new InterProcessMutex(client, "/zookeeper/lock");
                mutex.acquire();
                log.info("Enter mutex, sleep 3 seconds ....");
                TimeUnit.SECONDS.sleep(3);
                mutex.release();
                log.info("mutex released....");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        //关闭客户端
        client.close();
    }
}
