package com.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)  //运行时生效
@Target(ElementType.TYPE) //该注解写在类上面
public @interface Component {

    String value() default "";
}
