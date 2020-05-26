package com.zwh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangWeihui
 * @date 2019/8/23 11:27
 */
@Slf4j
public class ZooKeeperLock {

    private ZooKeeper zk;
    private String parentNode;
    private String lockNode;
    private int seq;
    private String seqNode;
    private boolean running = true;

    public ZooKeeperLock(String parentNode, String lockNode) throws IOException,InterruptedException{
        this.zk = ZookeeperUtils.connect();
        this.parentNode = parentNode;
        this.lockNode = lockNode;
    }

    public void lock() throws KeeperException, InterruptedException {
        String createResult = zk.create(parentNode + lockNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("create result : {}", createResult);
        if(StringUtils.hasText(createResult)) {
            seq = Integer.valueOf(createResult.replace(parentNode + lockNode, ""));
            seqNode = createResult.replace(parentNode + "/", "");
        }
        processLock();
        while (running) {
            TimeUnit.SECONDS.sleep(5);
            log.info("thread [{}] is running", Thread.currentThread().getName());
        }
    }

    private void processLock() throws KeeperException, InterruptedException{
        List<String> children = zk.getChildren(parentNode, false);
        String prefix = lockNode.substring(1);
        children.sort((s1,s2) -> {
            s1 = s1.replace(prefix, "");
            s2 = s2.replace(prefix, "");
            return Integer.valueOf(s1).compareTo(Integer.valueOf(s2));
        });
        OptionalInt min = children.stream().mapToInt(c -> {
            c = c.replace(prefix, "");
            return Integer.valueOf(c);
        }).sorted().min();
        if(min.getAsInt() == seq) {
            log.info("开始处理业务3秒钟。。。");
            TimeUnit.MILLISECONDS.sleep(3000);
            log.info("处理业务完毕。。。");
            zk.close();
            running = false;
        } else {
            String preNode = children.get(children.indexOf(seqNode) - 1);
            Stat stat = zk.exists(parentNode + "/" + preNode, new LockWatcher());
            log.info("previous node is : [{}], stat : [{}]", preNode, stat);
        }
    }

    private class LockWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("Catch a event : " + event);
            if(Event.EventType.NodeDeleted == event.getType()) {
                try {
                    processLock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Runnable runnable = () -> {
                try {
                    new ZooKeeperLock("/zookeeper/lock", "/lock-").lock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }};
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}
