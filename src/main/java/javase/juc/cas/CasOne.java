package javase.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.cas
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 12:09
 * @Description: CAS compare and set 比较与交换
 * 如果线程的期望值与主内存中的值一样，就进行更新，否则不更新，需要重新获取内存中的值
 * 原子类之所以底层使用CAS 也可以保证线程安全，而不用加synchronized 是因为 cas 底层使用的是（Unsafe类 +自旋）
 *
 * Unsafe类？
 *     是CAS的核心类 由于Java方法无法直接访问底层系统，需要通过本地(Native) 方法来访问 Unsafe 相当与一个后门 基于该类 可以操作特点内存的数据、
 *     Unsafe类 存在于 sun.misc 包中 其内部方法操作可以像c的指针一样操作内存数据
 *     Unsafe类 中所有方法都是 native 的
 * CAS 缺点
 *     循环时间开销大  (如果失败 CAS 会一直尝试 在自旋 导致CPU 消耗大)
 *     只能保证一个共享变量的原子操作 (多个共享变量使用锁来保证原子操作)
 *     引出了ABA 问题？
 *
 */
public class CasOne {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        // 这里不会进行更新
        /**
          底层源码  使用的是
          valueOffset 内存中的偏移地址 Unsafe类是通过内存偏移地址来获取内存中的数据
          unsafe.compareAndSwapInt(this, valueOffset, expect, update);

             public final int getAndSetInt(Object var1, long var2, int var4) {
             int var5;
             do {
             // 获取当前对象 var1 在内存地址（var2）中 的值
             var5 = this.getIntVolatile(var1, var2);
             // while  不断区 内存中的值与上一次取出的值进行比较 相同就更新 不然就一直自旋
             } while(!this.compareAndSwapInt(var1, var2, var5, var4));

             return var5;
             }
         */
        System.out.println(atomicInteger.compareAndSet(2,4)+" current data \t "+atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5,4)+" current data \t "+atomicInteger.get());
    }
}
