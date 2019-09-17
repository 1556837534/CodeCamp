package javase.base;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.base
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 09:41
 * @Description: 常见的懒汉单例模式
 */
public class SampleSingleDemo {
    private SampleSingleDemo singtton = null;

    private SampleSingleDemo() {

    }

    public SampleSingleDemo getInstance() {
        if (singtton == null) {
            synchronized (SampleSingleDemo.class) {
               if (singtton == null) {
                   try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                   singtton =  new SampleSingleDemo();
               }
            }
        }
        return singtton;
    }
}
