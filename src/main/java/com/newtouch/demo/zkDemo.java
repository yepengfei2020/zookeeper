package com.newtouch.demo;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class zkDemo implements Watcher{

    /*连接地址*/
    private  static  final  String ZK_ADDRESS ="0.0.0.0:2181";
    /*回话时间*/
    private static  final  Integer ZK_SESSION_TIMEOUT=2000;
    /*Zookerpee对象*/
    private ZooKeeper zooKeeper;

    public zkDemo(){
        //初始化连接
        openConnection(ZK_ADDRESS,ZK_SESSION_TIMEOUT);
    }


    /*回调方法 、监听连接、 监听增删改节点事件*/
    @Override
    public void process(WatchedEvent event) {
        //获取当前状态
        Event.KeeperState state = event.getState();
        //获取通知类型
        Event.EventType type = event.getType();
        //获取节点路径
        String path =event.getPath();
        System.out.println("当前状态:"+state);
        System.out.println("通知类型:"+type);
        System.out.println("节点路径:"+path);
        //连接是否成功
        if(Event.KeeperState.SyncConnected == state){
            if(Event.EventType.None == type){
                System.out.println("****************连接成功!!******************");
            }
            if(Event.EventType.NodeCreated == type){
                System.out.println("****************创建成功!!******************");
            }
            if(Event.EventType.NodeDataChanged == type){
                System.out.println("****************修改成功!!******************");
            }
            if(Event.EventType.NodeDeleted == type){
                System.out.println("****************修改成功!!******************");
            }

        }
    }

    public void  openConnection(String zk_address,Integer zk_session){
        try {
            zooKeeper = new ZooKeeper(zk_address,zk_session,this);
            System.out.println("连接成功!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*创建节点*/
    public void createNode(String path,String data){
        /*启动监听*/
        try {
            zooKeeper.exists(path,true);
            zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改节点
     */
    public void setNode(String path,String data){
        try {
            //启动监听
            zooKeeper.exists(path,true);
            //修改节点
            zooKeeper.setData(path,data.getBytes(),-1);
            System.out.println("修改成功！！！");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     */
    public void deleteNode(String path){
        try {
            //启动监听
            zooKeeper.exists(path,true);
            //删除
            zooKeeper.delete(path,-1);
            System.out.println("删除成功！！！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断节点是否存在
     *
     * */
    public void isExist(String path){
        try {
            Stat stat = zooKeeper.exists(path,false);
            System.out.println(stat == null ? "not exists" : "exists");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    /**
     * 关闭zk方法
     * @param
     */
    public void closeConnection(){
        try {
            if(zooKeeper!=null){
                zooKeeper.close();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        zkDemo mckz=new zkDemo();
        //创建节点
//        mckz.createNode("/mckz","MCKZ");
        //修改
//        mckz.setNode("/mckz","wahahaha");
        mckz.isExist("/mckz");
//        //删除
//        mckz.deleteNode("/mckz");
//        //关闭
//        mckz.closeConnection();

    }
}
