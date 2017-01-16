package com.duanrong.drpay.jsonservice.handler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口输入参数注解,
 * 输入参数需要此注解绑定, 此注解会自动转换输入参数为绑定类型
 * @author xiao
 * @datetime 2016年11月30日 下午6:15:32
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParameter {

	boolean required() default true;
}
