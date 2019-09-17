package javase.juc.OtherLock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.OtherLock
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 16:16
 * @Description: 类似于抢车位
 * 类似一种锁，可以代替 synchronized，lock（前提设置1），可以针对 多线程抢占多个资源
 *
 */
public class SemaPhoreDemo {
    public static void main(String[] args) {
        // 初始化 3个资源
        Semaphore semaphore = new Semaphore(3);
        for (int i =1; i<=10;i++) { // 10个线程争抢3个资源
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 抢到资源 ");
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+"\t 停车3s 后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
