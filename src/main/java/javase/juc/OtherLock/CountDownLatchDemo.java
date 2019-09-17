package javase.juc.OtherLock;

import java.util.concurrent.CountDownLatch;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.OtherLock
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 15:59
 * @Description:
 * 倒计时 ---> 类似火箭发射器的倒计时
 *  CountDownLatch.await() // 判断计数器为0才会往下走
 */
public class CountDownLatchDemo {
    // 使用之前 我想人数到 10 了才往下执行  会出现 学生还没全部离开教室  班长就离开了
    public void before () {
        for (int i =1; i<=10;i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
            },String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName()+"\t 班长关门离开教室");
    }

    // 使用 CountDownLatch 之后  倒计时
    public void after() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i =1; i<=10;i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        // 任务还没执行完 调用 await方法的线程将会被阻塞  知道 CountDownLatch 值为0 才会进行唤醒
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t 班长关门离开教室");
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo downLatchDemo = new CountDownLatchDemo();
        //downLatchDemo.before();
        downLatchDemo.after();
    }
}
