package com.zwh.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangWeihui
 * @date 2019/8/22 16:21
 */
@Slf4j
public class ZookeeperUtils {

    public static void main(String[] args) {
        ZooKeeper zk = null;//连接zk server
        try {
            zk = connect();
            String node = "/zookeeper/computer/cpu";
            Stat stat = zk.exists(node, true);//检测node是否存在
            if (stat == null) {
                //创建节点
                String createResult = zk.create(node, "英特尔".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                System.out.println("create result : " + createResult);
                stat = zk.exists(node, true);
            }
            printNode(zk, node, stat);
            stat = zk.setData(node, "AMD".getBytes(), stat.getVersion());
            printNode(zk, node, stat);
            zk.delete(node, stat.getVersion());
            stat = zk.exists(node, false);
            System.out.println("stat after delete : " + stat);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static ZooKeeper connect() throws IOException, InterruptedException{
        String connectString = "47.104.103.95:2181,47.104.103.95:2182,47.104.103.95:2183";
        ZooKeeper zk = new ZooKeeper(connectString, 300000, new DemoWatcher());
        if(!ZooKeeper.States.CONNECTED.equals(zk.getState())){
            while (true) {
                if(ZooKeeper.States.CONNECTED.equals(zk.getState())) {
                    break;
                } else {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            }
        }
        log.info("zk connected,  stat : {}", zk);
        return zk;
    }

    public static void printNode(ZooKeeper zk, String node, Stat stat) throws InterruptedException, KeeperException{
        //获取节点的值
        byte[] b = zk.getData(node, true, stat);
        System.out.println("node data : " + new String(b));
    }

    static class DemoWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            log.info("catch a event : [{}]", event);
        }
    }

}
