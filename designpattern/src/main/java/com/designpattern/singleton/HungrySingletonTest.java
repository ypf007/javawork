package com.designpattern.singleton;

/**
 * @Author ypf
 * @Date 2021/9/11 17:55
 * @Version 1.0
 */

/**
 * 饿汉模式：类加载的初始化阶段就完成了实例的初始化。本质上就是借助于JVM类加载机制，保证实例的唯一性。
 * 类加载过程：
 *      1.加载二进制数据到内存中，生成对应的Class数据结构
 *      2.连接：a.验证  b.准备（给类的静态成员变量赋默认值）  c.解析
 *      3.初始化：给类的静态变量赋初值
 *
 *  只有在真正使用对应的类时，才会触发初始化 如（当前类是启动类即main函数所在的类，
 *      直接进行new操作，访问静态属性，访问静态方法，用反射访问类，初始化一个类的子类等）
 */
public class HungrySingletonTest {
    public static void main(String[] args) {
        HungrySingleton instance1 = HungrySingleton.getInstance();
        HungrySingleton instance2 = HungrySingleton.getInstance();
        System.out.println(instance1==instance2);

    }
}

class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() { //定义私有的无参构造方法，防止在外部进行直接new,这样的话单例就没法保证了

    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}