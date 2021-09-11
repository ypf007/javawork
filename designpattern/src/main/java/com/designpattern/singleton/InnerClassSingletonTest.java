package com.designpattern.singleton;

import java.util.PriorityQueue;

/**
 * @Author ypf
 * @Date 2021/9/11 18:10
 * @Version 1.0
 */

/**
 * 静态内部类
 *  本质上是利用类的加载机制来保证线程安全
 *  只有在实际使用的时候，才会触发类的初始化，所以也是懒加载的一种形式。
 */
public class InnerClassSingletonTest {
    public static void main(String[] args) {
        InnerClassSingleton instance1 = InnerClassSingleton.getInstance();
        InnerClassSingleton instance2 = InnerClassSingleton.getInstance();
        System.out.println(instance1==instance2);
    }
}

class InnerClassSingleton {
    private InnerClassSingleton() {
    }

    private static class InnerClassHolder {
        private static InnerClassSingleton instance=new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance(){
        return InnerClassHolder.instance;
    }
}

