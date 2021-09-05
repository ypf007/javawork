package com.yupengfei;

import com.spring.YupengfeiApplicationContext;
import com.yupengfei.service.UserInterface;
import com.yupengfei.service.UserService;

public class Test {
    public static void main(String[] args) {

        //先扫描---->创建一些单例Bean(都在构造方法里实现) BeanDefinition  BeanProcessor
        YupengfeiApplicationContext applicationContext = new YupengfeiApplicationContext(AppConfig.class);

        //UserService userService = (UserService) applicationContext.getBean("userService");
        UserInterface userService = (UserInterface) applicationContext.getBean("userService");

        userService.test();

//        System.out.println(applicationContext.getBean("userService"));
//        System.out.println(applicationContext.getBean("userService"));
//        System.out.println(applicationContext.getBean("orderService"));
    }
}
