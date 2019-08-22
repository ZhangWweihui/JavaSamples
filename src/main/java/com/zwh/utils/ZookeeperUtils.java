package com.zwh.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangWeihui
 * @date 2019/8/22 16:21
 */
public class ZookeeperUtils {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String connectString = "47.104.103.95:2181,47.104.103.95:2182,47.104.103.95:2183";
        ZooKeeper zk = new ZooKeeper(connectString, 300000, new DemoWatcher());//连接zk server
        if(!ZooKeeper.States.CONNECTED.equals(zk.getState())){
            while (true) {
                if(ZooKeeper.States.CONNECTED.equals(zk.getState())) {
                    break;
                } else {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            }
        }
        System.out.println("zk stat : " + zk);
        String node = "/zookeeper/computer/cpu";
        Stat stat = zk.exists(node, true);//检测node是否存在
        if (stat == null) {
            //创建节点
            String createResult = zk.create(node, "英特尔".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create result : " + createResult);
        }
        printNode(zk, node, stat);
        try {
            stat = zk.setData(node, "AMD".getBytes(), stat.getVersion());
        } catch (Exception e) {
            stat = zk.setData(node, "AMD".getBytes(), 1);
            e.printStackTrace();
        }
        printNode(zk, node, stat);
        zk.delete(node, stat.getVersion());
        stat = zk.exists(node, false);
        System.out.println("stat after delete : " + stat);
        zk.close();
    }

    public static void printNode(ZooKeeper zk, String node, Stat stat) throws InterruptedException, KeeperException{
        //获取节点的值
        byte[] b = zk.getData(node, true, stat);
        System.out.println("node data : " + new String(b));
    }

    static class DemoWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("----------->");
            System.out.println("path:" + event.getPath());
            System.out.println("type:" + event.getType());
            System.out.println("stat:" + event.getState());
            System.out.println("<-----------");
        }
    }

}
