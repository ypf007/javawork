package com.designpattern.singleton;

/**
 * @Author ypf
 * @Date 2021/9/11 17:40
 * @Version 1.0
 */

/**
 * 懒汉模式：延迟加载，只有在真正使用的时候，才开始实例化。
 *   需要考虑几个问题：
 *      1）线程安全问题
 *      2）double check 加锁优化
 *      3）编译器（JIT），CPU有可能对指令进行重排序，导致使用到尚未初始化的实例，
 *          可以通过添加volatile关键字进行修饰，对于volatile修饰的字段，可以防止指令重排序。
 */
public class LazySingletonTest {
    public static void main(String[] args) {
        new Thread(()->{
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName()+":"+instance);
        },"t1").start();

        new Thread(()->{
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName()+":"+instance);
        },"t2").start();
    }
}

class LazySingleton {

    private volatile static LazySingleton instance;

    private LazySingleton(){}  //定义私有的无参构造方法，防止在外部进行直接new,这样的话单例就没法保证了

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                    /**
                     * 字节码层
                     * JIT,CPU
                     * 1.分配空间
                     * 2.初始化
                     * 3.引用赋值
                     */
                }
            }
        }
        return instance;
    }
}
