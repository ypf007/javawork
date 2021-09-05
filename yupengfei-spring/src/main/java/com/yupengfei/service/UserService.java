package com.yupengfei.service;


import com.spring.*;

@Component("userService")   //value参数，给Bean取一个名字
@Scope("singleton") //定义作用域
public class UserService implements InitializingBean,UserInterface, BeanNameAware {

    @Autowired
    private OrderService orderService;

    @YupengfeiValue("xxx")  //将“xxx”赋值给test
    private String test;

    private String beanName; //当获取到该Bean时就能知道BeanName，使用aware回调

    @Override
    public void setBeanName(String name) {
        this.beanName=name;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("初始化");
    }

    public void test(){
        System.out.println(orderService);
        System.out.println(test);
        System.out.println(beanName);
    }




}
