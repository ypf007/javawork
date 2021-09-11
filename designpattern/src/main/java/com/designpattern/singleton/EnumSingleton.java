package com.designpattern.singleton;

/**
 * @Author ypf
 * @Date 2021/9/11 18:32
 * @Version 1.0
 */
public enum EnumSingleton {
    INSTANCE;

    public void print(){
        System.out.println(this.hashCode());
    }
}

class EnumTest{
    public static void main(String[] args) {
        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        EnumSingleton instance2 = EnumSingleton.INSTANCE;
        System.out.println(instance1==instance2);
    }
}
