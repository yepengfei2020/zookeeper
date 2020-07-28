package com.newtouch.study;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class zkExists {

    private  static ZooKeeper zooKeeper;

    private  static  ZooKeeperConnection conn;

    /*判断节点是否存在的方法*/
    public static Stat   znodeIsExists(String path) throws KeeperException, InterruptedException {

        Stat stat =zooKeeper.exists(path,true);

        return stat;
    }

    public static void main(String[] args) {
        String path = "/MyFirstZnode"; // Assign path to znode

        /*获取连接*/
        try {
            conn = new ZooKeeperConnection();
            zooKeeper = conn.connect("localhost");

           Stat stat = znodeIsExists(path);

           if(stat != null){
               System.out.println("当前节点存在");
           }else{
               System.out.println("当前节点不存在");
           }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
