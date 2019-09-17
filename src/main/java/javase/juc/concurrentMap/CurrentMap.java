package javase.juc.concurrentMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @BelongsProject: CodeCamp
 * @BelongsPackage: javase.juc.concurrentMap
 * @Author: Jackson_J
 * @CreateTime: 2019-09-17 14:47
 * @Description:
 */
public class CurrentMap {
    public static void main(String[] args) {
        // 写时复制 调用add 方法的时候
        // 使用了ReentrantLock 数组添加了Volatile 关键词
        //CopyOnWriteArrayList
        // 内部使用了 synchronized
        //ConcurrentHashMap
    }
}
