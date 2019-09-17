package javase.base.loadOrder;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.base.loadOrder
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 09:49
 * @Description: 父类
 */
public class Father {
    //这里i=test()执行的是子类重写的test()方法
    private int i = test();

    private static int j = method();

    static {
        System.out.print("(1)");
    }

    public Father() {
        System.out.print("(2)");
    }

    {
        System.out.print("(3)");
    }


    public int test() {
        System.out.print("(4)");
        return 1;
    }

    public static int method() {
        System.out.print("(5)");
        return 1;
    }
}
