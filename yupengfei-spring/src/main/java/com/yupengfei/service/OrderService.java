package com.yupengfei.service;


import com.spring.Component;

@Component
public class OrderService {

    @YupengfeiValue("ccc")  //将“xxx”赋值给test
    private String test;

    public void test(){
        System.out.println(test);
    }

}
