package javase.juc.OtherLock;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc
 * @Author: Jackson_J
 * @CreateTime: 2019-08-17 09:30
 * @Description: 与 CountDownLatch 相反 这个是累加
 *
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
        });

        for (int i =1; i<=7;i++) {
            final int tempid = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 收集到 第"+tempid+"号龙珠");
                try {
                    // 累加 从 1 开始 加到 7
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }


}
