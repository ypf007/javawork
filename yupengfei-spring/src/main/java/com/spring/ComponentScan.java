package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 扫描路径注解
 */
@Retention(RetentionPolicy.RUNTIME)  //运行时生效
@Target(ElementType.TYPE) //该注解写在类上面
public @interface ComponentScan {

    String value() default "";
}
