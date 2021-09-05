package com.yupengfei.service;

import com.spring.BeanPostProcessor;
import com.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Component
public class YupengfeiBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            Object proxyInstance = Proxy.newProxyInstance(YupengfeiBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //切面
                    System.out.println("切面逻辑");

                    return method.invoke(bean, args); //执行原始的普通对象的方法
                }
            });

            return proxyInstance;  //返回了代理对象
        }
        return bean;
    }
}
