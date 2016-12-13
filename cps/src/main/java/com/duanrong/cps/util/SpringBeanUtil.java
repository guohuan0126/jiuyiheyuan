package com.duanrong.cps.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public final class SpringBeanUtil {



	/**
	 * 违背了 Spring 依赖注入思想
	 *
	 * @param beanId
	 * @return
	 */
	public static Object getBeanByName(String beanId) throws Exception {

		//获取web容器上下文
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		return wac.getBean(beanId);

	}

	/**
	 *  违背spring的ioc解耦思想。
	 */
	public static  <T> T getBeanByType(Class clazz) {
		if (clazz == null) {
			return null;
		}
		//获取web容器上下文
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		return (T) wac.getBean(clazz);
	}
}
