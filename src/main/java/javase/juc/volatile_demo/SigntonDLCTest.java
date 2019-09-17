package javase.juc.volatile_demo;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.volatile_demo
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 11:53
 * @Description: 单例模式是 DLC(双端检索机制) 写法
 *  线程安全问题主要出现在 对象初始化的时候
 *  当一个线程访问 对象的时候 由于对象还没有初始化完成(指令重排)  signton == null 导致会重新生成一个对象
 *
 * 结果 ： 多运行几次或发行 构造方法进入多次 正确性是 99%
 *  要保证 100 %  给 变量新增 volatitle 修饰
 */
class SigntonDLC {
    private static volatile SigntonDLC signton = null;

    private SigntonDLC() {
        System.out.println("-----单例构造方法进来");
    }

    public static SigntonDLC getInstance() {
        if (signton == null) {
            synchronized (SigntonDLC.class) {
                if (signton == null) {
                    signton = new SigntonDLC();
                }
            }
        }
        return signton;
    }
}

public class SigntonDLCTest {
    public static void main(String[] args) {
        for (int i =1; i<=10;i++) {
            new Thread(()->{
                SigntonDLC.getInstance();
            },String.valueOf(i)).start();
        }
    }

}
