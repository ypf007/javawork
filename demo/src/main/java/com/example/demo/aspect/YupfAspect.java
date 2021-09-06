package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class YupfAspect {

    @Before("execution(public void com.example.demo.service.UserService.test())")
    public void yupfBefore(JoinPoint joinPoint){
        System.out.println(joinPoint);
        System.out.println("yupfBefore");
    }
}
