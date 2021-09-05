package com.spring;


//Bean定义   保存Bean的类型，作用域，是否懒加载等等属性
public class BeanDefinition {

    private Class type;
    private ScopeEnum scope;
    private boolean isLazy;
    //等等...


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public ScopeEnum getScope() {
        return scope;
    }

    public void setScope(ScopeEnum scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
}
