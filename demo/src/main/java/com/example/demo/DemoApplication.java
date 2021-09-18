package com.example.demo;

import com.example.demo.service.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.lang.reflect.Field;

//@SpringBootApplication
//public class DemoApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
//
//}

public class DemoApplication {

    //UserService.class---->构造方法---->对象---->依赖注入(给加了@autoWired属性赋值)---->初始化前(a())     初始化    初始化后(AOP)     Bean
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


//        //编程式
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition(); //用编程方式定义了一个Bean
//        beanDefinition.setBeanClass(UserService.class);
//        beanDefinition.setScope("prototype");
//        //等等属性
//        context.registerBeanDefinition("userService",beanDefinition);

//        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(context); //参数为context，表示将读取到的beanDefinition放入该容器中
//        //将User.class解析(注册)为BeanDefinition
//        annotatedBeanDefinitionReader.register(User.class);
//        System.out.println(context.getBean("user"));




        // Bean
        UserService userService = (UserService) context.getBean("userService");


        userService.test();

    }
}
