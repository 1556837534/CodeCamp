package javase.juc.volatile_demo;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.volatile_demo
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 11:13
 * @Description: 验证内存的可见性
 *
 * 打印结果
 * 线程名称	 come in
 * 线程名称	 update number60
 *
 * 数据更新到 60 后 主线程还是在执行 不会停止
 *   这是因为 num 在线程工作区间将数据改成了60 但是没有刷新到主内存中  导致主内存中的值还是0 ，所以主线程一直在等待
 *
 * 修改方案：  num 使用 volatile 关键词修饰 即可
 */

class MyData {
    //int num = 0;
    volatile int num = 0;
    public void addTo60() {
        this.num  = 60;
    }
}

public class DemoOne {
    public static void main(String[] args) {
        // 资源类
        MyData myData = new MyData();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            //暂停一会 跳到主线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t update number--" + myData.num);
        },"线程名称").start();

        // main 线程 一直等待 直到 myData.num 不等于0
        while (myData.num == 0) {

        }
        System.out.println(Thread.currentThread().getName()+"\t");
    }
}
