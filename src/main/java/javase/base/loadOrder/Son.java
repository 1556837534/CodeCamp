package javase.base.loadOrder;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.base.loadOrder
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 09:50
 * @Description: 子类
 * 父类静态方法与静态代码块 （按编写顺序）> 子类静态方法与静态代码块 （按编写顺序）>
 * 父类非静态方法与非静态代码块 （按编写顺序）》 子类非静态方法与非静态代码块 （按编写顺序）》 父类构造器 >子类构造器
 */
public class Son extends Father{
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }

    public Son () {
        //super();//写或不写都在，在子类构造器中一定会调用父类的构造器
        System.out.print("(7)");
    }

    {
        System.out.print("(8)");
    }

    @Override
    public int test(){
        System.out.print("(9)");
        return 1;
    }

    public static int method(){
        System.out.print("(10)");
        return 1;
    }

    /**
     * @Author Jackson_J
    1. 主方法所在的类需要加载与初始化 这里不影响结果 如果main方法里面什么都不写 会进行类的加载 ，先加载父类的静态变量 -> 静态代码块  打印 5,1
     然后加载子类的静态变量  静态代码块   打印 10，6

    父类的实例化方法：
     * （1）super()（最前）
     * （2）i = test();   9
     * （3）父类的非静态代码块  3
     * （4）父类的无参构造（最后） 2
     *
     *  * 子类的实例化方法<init>：
     *  * （1）super()（最前）      （9）（3）（2）
     *  * （2）i = test();    （9）
     *  * （3）子类的非静态代码块    （8）
     *  * （4）子类的无参构造（最后） （7）

     */
    public static void main(String[] args) {
        // 这步创建对象的时候 打印  2176
        Son s1 = new Son();

        System.out.println();

        Son s2 = new Son();
    }
}
