package com.newtouch.study;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/*获取节点数据*/
public class zkGetNodeData {

    private  static ZooKeeper zooKeeper;

    private  static  ZooKeeperConnection conn;

    /*判断节点是否存在的方法*/
    public static Stat znodeIsExists(String path) throws KeeperException, InterruptedException {

        Stat stat =zooKeeper.exists(path,true);

        return stat;
    }

    public static String   getNodeData(String path, Stat stat) throws KeeperException, InterruptedException {
       byte[] s = zooKeeper.getData(path,true,stat);

        String string = new String(s);

       return  string;

    }


    public static void main(String[] args) {
        String path = "/MyFirstZnode"; // Assign path to znode

        /*获取连接*/
        try {
            conn = new ZooKeeperConnection();
            zooKeeper = conn.connect("localhost");
            Stat stat = znodeIsExists(path);
            if(stat != null){
               String str = getNodeData(path,stat);
               System.out.println("当前节点存在,节点为:"+str);
            }else{
                System.out.println("当前节点不存在");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
