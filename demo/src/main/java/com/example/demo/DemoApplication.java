package com.example.demo;

import com.example.demo.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;

//@SpringBootApplication
//public class DemoApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
//
//}

public class DemoApplication{

    //UserService.class---->构造方法---->对象---->依赖注入(给加了@autoWired属性赋值)---->初始化前(a())     初始化    初始化后(AOP)     Bean
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Bean
        UserService userService=(UserService)context.getBean("userService");


        userService.test();

    }
}
