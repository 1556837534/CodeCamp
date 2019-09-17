package javase.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.cas
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 12:26
 * @Description: CAS ABA 问题
 * ABA 问题可以理解为  “狸猫换太子”
 *
 * CAS 算法实现的一个重要前提是 需要将内存中某个时刻的数据与当前时刻的数据进行比较与交换 那么这个时间差内数据发生了变化？
 *
 *    比如说 一个线程One 从内存地址 V 中获取了数据A ，这个时候线程Two 也出内存中获取出A 并且线程 Two 进行了一波操作 将数据A 改成 B，最后 线程Two 又将地址 V 中的数据变成了A
 *    这个时候线程 One 进行CAS 操作 发现内存地址 V 中的数据是 A 然后线程One 就操作成功了
 *    虽然线程One 操作成功 但是并不代表这个过程没有问题
 *
 * 解决方法 原子引用 与新增 一种机制  就是添加一个版本号
 *
 *
 */
class User {
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class CASABA {
    public static void main(String[] args) {
        User user = new User("jackson_j",26);
        User user1 = new User("Jackson_J",27);
        // 原子引用
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(user);

        System.out.println("===========================ABA 问题的产生================");
        new Thread(()->{
            atomicReference.compareAndSet(user,user1);
            atomicReference.compareAndSet(user1,user);
        },"t1").start();

        new Thread(()->{
            // 暂停3s 确保线程 t1 完成了一次 ABA 操作
           try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
           // 这里 使用CAS 进行更新  更新成功 虽然中间数据出现了ABA 问题 但是还是更新成功了
            System.out.println(Thread.currentThread().getName()+"\t"+atomicReference.compareAndSet(user,new User("zs",55))+"----------val \t" + atomicReference.get());
        },"t2").start();

        System.out.println("===========================ABA 问题的解决================");

        // 初始化 原子引用 并新增一个版本号 1
        AtomicStampedReference<User> _atomicReference = new AtomicStampedReference<>(user,1);
        new Thread(()->{
            int stamp = _atomicReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"=====第一次版本号\t" + stamp);
            //暂停 t3 线程 1s
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            // 如果 期望值与版本号 是 user 与 1 的话 就进行更新
            _atomicReference.compareAndSet(user,user1,_atomicReference.getStamp(),_atomicReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"=====第二次版本号\t" + _atomicReference.getStamp());
            _atomicReference.compareAndSet(user1,user,_atomicReference.getStamp(),_atomicReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"=====第二次版本号\t" + _atomicReference.getStamp());
        },"t3").start();

        new Thread(()->{
            int stamp = _atomicReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"=====第一次版本号\t" + stamp);
            //暂停 t3 线程 3s
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
             // 这里 使用CAS 进行更新
            boolean result = _atomicReference.compareAndSet(user,new User("zs",55),stamp,_atomicReference.getStamp()+1);

            System.out.println(Thread.currentThread().getName()+"\t 是否修改成功==="+result+" \t 当前最新版本 =="+_atomicReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t 当前最新值 =="+_atomicReference.getReference());
        },"t4").start();


    }
}
