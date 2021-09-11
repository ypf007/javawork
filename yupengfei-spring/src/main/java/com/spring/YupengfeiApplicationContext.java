package com.spring;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.yupengfei.service.YupengfeiBeanPostProcessor;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YupengfeiApplicationContext {

    private Class configClass; //配置类
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> singletonObjects = new HashMap<>();  //单例池
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();


    public YupengfeiApplicationContext(Class configClass) { //有参构造方法
        this.configClass = configClass;

        //扫描
        scan(configClass);

        //创建单例Bean
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();

            if (beanDefinition.getScope().equals(ScopeEnum.singleton)) {

                Object bean = creatBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }


    public Object creatBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();

        Object instance = null;
        try {

            instance = clazz.getConstructor().newInstance(); //默认用无参构造方法去创建对象

            //进行依赖注入
            for (Field field : clazz.getDeclaredFields()) {  //getDeclaredFields是返回所有字段，getFields只返回公有字段
                if (field.isAnnotationPresent(Autowired.class)) {

                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }

            //执行一系列Aware接口
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            //BeanPostProcessor 初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            //初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            //BeanPostProcessor 初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }
            //new YupengfeiBeanPostProcessor().postProcessAfterInitialization(instance,beanName);


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    //getBean方法
    public Object getBean(String beanName) {
        //避免重新解析类，使用BeanDefinition
        if (!beanDefinitionMap.containsKey(beanName)) {
            //map里面没有beanName，表示程序里就没有定义过这个bean，beanName出错抛出异常
            throw new NullPointerException();
        }

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.getScope().equals(ScopeEnum.singleton)) {
            Object singletonBean = singletonObjects.get(beanName);
            if (singletonBean == null) { //防止一个Bean提前创建，导致注入的Bean属性拿不到
                singletonBean = creatBean(beanName, beanDefinition);
                singletonObjects.put(beanName, singletonBean);
            }
            return singletonBean;
        } else {
            //原型Bean
            Object prototypeBean = creatBean(beanName, beanDefinition);
            return prototypeBean;
        }

    }

    private void scan(Class configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) { //判断配置类上是否有ComponentScan注解
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class); //拿到ComponentScan注解
            String path = componentScanAnnotation.value(); //获取扫描路径(源代码工程下的路径),但是需要找target目录下编译好的路径
            path = path.replace(".", "/"); //转换为URL (此路径是相对路径)

            //System.out.println(path);
            ClassLoader classLoader = YupengfeiApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path); //获取到绝对路径
            File file = new File(resource.getFile());  //获取该绝对路径下的文件
            if (file.isDirectory()) {  //判断文件是否是个目录
                for (File f : file.listFiles()) {
                    String absolutePath = f.getAbsolutePath();


                    absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace("\\", ".");
                    //System.out.println(absolutePath);

                    //需要判断该.class文件里，是否包含Component注解，因此需要使用加载器先加载类才能判断
                    try {
                        Class<?> clazz = classLoader.loadClass(absolutePath);  //loadClass里的参数，是"com.yupengfei.userService"形式

                        if (clazz.isAnnotationPresent(Component.class)) {

                            //判断该类是否实现了BeanPostProcessor
                            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                BeanPostProcessor instance = (BeanPostProcessor) clazz.getConstructor().newInstance();
                                beanPostProcessorList.add(instance);
                            }

                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if ("".equals(beanName)) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }
                            //说明该类是Bean,创建Bean定义
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(clazz);

                            //还需判断该Bean是否是单例或者多例等,即解析Scope
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class); //获取该注解
                                String scopeValue = scopeAnnotation.value(); //有可能是单例的，也有可能是多例的
                                if (ScopeEnum.singleton.name().equals(scopeValue)) {  //说明是单例
                                    beanDefinition.setScope(ScopeEnum.singleton);
                                } else {  //说明是多例
                                    beanDefinition.setScope(ScopeEnum.prototype);
                                }

                            } else {
                                //无Scope注解默认是单例的
                                beanDefinition.setScope(ScopeEnum.singleton);
                            }

                            beanDefinitionMap.put(beanName, beanDefinition);

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
    }

}
