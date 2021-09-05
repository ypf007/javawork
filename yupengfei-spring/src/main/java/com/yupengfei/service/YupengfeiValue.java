package com.yupengfei.service;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  //运行时生效
@Target(ElementType.FIELD) //该注解写在字段上面
public @interface YupengfeiValue {

    String value() default "";
}
