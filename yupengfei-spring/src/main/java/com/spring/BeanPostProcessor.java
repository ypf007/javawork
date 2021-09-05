package com.spring;

public interface BeanPostProcessor {  //针对所有的Bean

    default Object postProcessBeforeInitialization(Object bean,String beanName){
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean,String beanName){
        return bean;
    }
}
