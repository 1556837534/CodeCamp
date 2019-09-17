package javase.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.lock
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 15:06
 * @Description:
 *
 */
public class lockDemo1 {
    //ReentrantLock 默认是非公平锁 通过构造函数 默认false  传 true就是公平锁
    //Synchronized  非公平锁
    // 以上两种都是可重入锁(代码内部与外部使用相同的锁 程序进入内部程序后自动获取锁)
    // 验证可重入锁 ReentrantLock
    Lock lock = new ReentrantLock();

    public void sendSms() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"\t 发送短信");
        } catch (Exception ex) {

        } finally {
            lock.unlock();
        }
    }

    public void sendEmail () {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"\t 发送邮件");
            sendSms();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        lockDemo1 demo1 = new lockDemo1();
        for (int i =1; i<=2;i++) {
            new Thread(()->{
                demo1.sendEmail();
            },String.valueOf(i)).start();
        }
    }
 }
