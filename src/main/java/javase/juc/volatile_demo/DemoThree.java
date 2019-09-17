package javase.juc.volatile_demo;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.volatile_demo
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 11:43
 * @Description: volatile 禁止指令重排
 * 原理是
 * 对volatile 变量进行写操作的时候 会在写操作后加入一条store屏蔽指令 将工作内存中的数据重新刷新回主内存中
 * 对volatile 变量进行读操作的时候 会在读操作前加入一条load屏蔽指令 从主内存中读取共享变量
 * 屏蔽指令--> 内存屏障 或者称为 内存栅栏
 */
public class DemoThree {
    public int a = 0;
    boolean flag =false;

    public void method1() {
        // 语句1
        a =1;
        // 语句2
        flag =true;
    }

    /**
     * 多线程环境，线程交替执行 由于编译器指令重排的存在
     * 两个线程中使用的变量能否保证一致性的不可以确定的
     */
    public void method2() {
        if (flag) {
            //语句3
            a += 5;
            System.out.println("----returnVal---"+a);
        }
    }
}
