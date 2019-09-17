package javase.juc.volatile_demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.volatile_demo
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 11:25
 * @Description: 验证 volatile 不保证原子性
 * 什么是原子性
 *    不可分割，完整性，也即 某个线程正在执行某个业务时 中间不可以被加塞或者被分割 是一个整体操作 要么同时成功 要么同时是失败
 *    原子性 表示多个线程操作相同数据的时，单个线程不会被中断或者阻塞，只有该线程结束了，其他线程才开始
 *
 * 打印结果
 * 1	 come in
 * 1	 come in
 * 1	 come in
 * ----result:19999
 *
 * 多运行几次后 结果并不是 20*1000
 * 原因：
 *    线程运行太快，出现了写覆盖 (一个线程写数据到主内存被挂起，其它现在读取操作后写回主内存，被挂起的线程被激活，可以写数据，会出现数据被覆盖)
 *    实际就是各个线程中工作区间的数据不一致 就是其他线程操作共享数据的时候被打断或者挂起 导致的  这个就是原子性
 * 解决方法：
 *     num 类型由 int 改成 AtomicInteger
 *
 */
class MyData2 {
    volatile int num = 0;

    // num 使用的volatile 关键词修饰  但是 volatile 是不保证原子性的
    public void numPlus() {
       this.num++;
    }

    AtomicInteger integer = new AtomicInteger();
    public void addAtomic() {
        integer.getAndIncrement();
    }
}

public class DemoTwo {
    public static void main(String[] args) {
        MyData2 data2 = new MyData2();

        // 启动 20个线程 每个线程加 1000次
        for (int i =1; i<=20;i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    System.out.println(Thread.currentThread().getName()+"\t come in ");
                    //data2.numPlus();
                    data2.addAtomic();
                }
            },String.valueOf(i)).start();
        }

        // 主线程 当上面的任务执行完后 再执行下面的代码
        //这里设置 2 而不是1 是因为idea 工具后台会默认启动一个线程 需要计算进去
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        // 输出结果
        //System.out.println("----result:"+data2.num);
        System.out.println("----result:"+data2.integer);
    }
}
