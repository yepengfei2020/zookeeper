package com.newtouch.study;

import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.PrivateKeyResolver;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/*
* 帮助类
* */


public class ZooKeeperConnection {

    private ZooKeeper zk;

    final CountDownLatch countDownLatch = new CountDownLatch(1);

    public ZooKeeper connect(String host) throws IOException, InterruptedException {

        zk = new ZooKeeper(host, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();

                }
            }
        });

        countDownLatch.await();
        return zk;
    }

    public  void close() throws InterruptedException {
      zk.close();
    }

    }
