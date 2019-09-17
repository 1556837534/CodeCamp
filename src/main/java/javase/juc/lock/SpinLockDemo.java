package javase.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.lock
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 15:17
 * @Description: 实现一个自旋锁
 *  自旋锁：
 *    尝试获取锁的线程不会立即阻塞 而是采用循环的方式去获取锁，这样的好处是减少线程上下文切换的消耗
 *    好处： 循环比较直到获取锁为止 没有 wait 阻塞
 *
 *    通过CAS 操作完成自旋锁 A 线程进来先调用mylock 方法 持有锁5s B随后进来发现当前有线程持有锁，不是null 所以只能通过A 自旋等待 直到A 释放锁
 *
 *    打印
 *    AA	 come in
 *    BB	 come in
 *    AA	 come out
 *    BB	 come out
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void mylock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in");
        while (!atomicReference.compareAndSet(null,thread)) {

        }
    }

    public void unLock () {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t come out");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()-> {
            spinLockDemo.mylock();
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.unLock();
        },"AA").start();
        //保证上面的线程先启动
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(()-> {
            spinLockDemo.mylock();
            spinLockDemo.unLock();
        },"BB").start();
    }
}
