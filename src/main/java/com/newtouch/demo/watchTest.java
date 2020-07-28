package com.newtouch.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class watchTest implements Watcher {
    /**
     * watch test
     *
     */

        private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

        public static Stat stat = new Stat();
        ZooKeeper zooKeeper;

        public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
            String p = "/mckz1";
            ZooKeeper zooKeeper = new ZooKeeper("0.0.0.0:2181", 5000, new watchTest());
         /*   调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行*/
            connectedSemaphore.await();
            //exists register watch
            zooKeeper.exists(p, true);
            System.out.println("判断目录节点是否存在，存在启动监听");
            String path = zooKeeper.create(p, "456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            //get register watch
            zooKeeper.getData(path, true, stat);
            zooKeeper.setData(path, "hhhh".getBytes(), -1);
            zooKeeper.exists(path, true);
            //exists register watch
            zooKeeper.delete(path, -1);

        }
        @Override
        public void process(WatchedEvent event) {
            if (Event.KeeperState.SyncConnected == event.getState()) {
                if (Event.EventType.None == event.getType() && null == event.getPath()) {
                    connectedSemaphore.countDown();
                    System.out.println("Zookeeper session established");
                } else if (Event.EventType.NodeCreated == event.getType()) {
                    System.out.println("success create znode");

                } else if (Event.EventType.NodeDataChanged == event.getType()) {
                    System.out.println("success change znode: " + event.getPath());

                } else if (Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("success delete znode");

                } else if (Event.EventType.NodeChildrenChanged == event.getType()) {
                    System.out.println("NodeChildrenChanged");

                }

            }
        }

}
