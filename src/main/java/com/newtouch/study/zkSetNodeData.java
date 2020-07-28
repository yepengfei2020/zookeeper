package com.newtouch.study;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class zkSetNodeData {

    private  static ZooKeeper zooKeeper;

    private  static  ZooKeeperConnection conn;

    public static Stat update(String path, byte[] b) throws KeeperException, InterruptedException {
       Stat stat = zooKeeper.setData(path,b,zooKeeper.exists(path,true).getVersion());
       return stat;
    }

    // Method to check existence of znode and its status, if znode is available.
    public static void delete(String path) throws KeeperException,InterruptedException {
        zooKeeper.delete(path,zooKeeper.exists(path,true).getVersion());
    }

    public static void main(String[] args) {
        // znode path
        String path = "/MyFirstZnode"; // Assign path to znode
        byte[] newData = "success body!!! fine".getBytes();

        try {
            conn =  new ZooKeeperConnection();
            zooKeeper = conn.connect("localhost");
            Stat stat = update(path,newData);
            if(stat != null){
                System.out.println("修改成功！！"+stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
