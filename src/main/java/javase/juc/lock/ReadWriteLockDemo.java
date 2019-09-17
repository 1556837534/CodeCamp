package javase.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.lock
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 15:36
 * @Description: 读写锁是 共享锁   使用读写锁之前
 *
 *  多个线程同时读一个资源流没有任何问题 所以为了满足并发量，读取共享资源应该可以同时进行
 *  但是 如果有线程想去写共享资源，那就不能有其他线程来进行写 与读
 *  总结：
 *      读 + 读 可以共存
 *      读 + 写 不能共存
 *      写 + 写 不能共存
 *
 *
 *      2	 正在写入：2
 * 5	 正在写入：5
 * 4	 正在写入：4
 * 1	 正在写入：1
 * 3	 正在写入：3
 * 1	 正在读取
 * 2	 正在读取
 * 4	 正在读取
 * 3	 正在读取
 * 5	 正在读取
 * 4	 写入完成
 * 4	 读取完成：null
 * 2	 写入完成
 * 3	 写入完成
 * 1	 读取完成：1
 * 5	 写入完成
 * 2	 读取完成：null
 * 5	 读取完成：null
 * 1	 写入完成
 * 3	 读取完成：null
 */
class MyResource {
    private volatile Map<String,Object> map = new HashMap<>();

    public void put(String key,Object val) {
        System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key );
        //暂停会
        try { TimeUnit.MICROSECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        map.put(key,val);
        System.out.println(Thread.currentThread().getName()+"\t 写入完成");
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName()+"\t 正在读取" );
        try { TimeUnit.MICROSECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName()+"\t 读取完成："+result);
    }


}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();
        //写
        for (int i =1; i<=5;i++) {
            final int tmpi = i;
            new Thread(()->{
                myResource.put(String.valueOf(tmpi),tmpi);
            },String.valueOf(i)).start();
        }

        //读
        for (int i =1; i<=5;i++) {
            final int tmpi = i;
            new Thread(()->{
                myResource.get(String.valueOf(tmpi));
            },String.valueOf(i)).start();
        }

    }
}
