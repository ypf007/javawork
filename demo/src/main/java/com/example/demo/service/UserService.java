package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserService {

    @Autowired
    private OrderService orderService;


    public void test() {
        System.out.println("test");
    }
}
