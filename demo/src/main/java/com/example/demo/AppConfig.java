package com.example.demo;

import com.example.demo.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.example.demo")
@EnableAspectJAutoProxy
public class AppConfig {
    public UserService userService(){
        return new UserService();
    }
}
