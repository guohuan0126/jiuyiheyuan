package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public final class SpringBeanUtil {

	static Log log = LogFactory.getLog(SpringBeanUtil.class);

	/**
	 * 违背了 Spring 依赖注入思想
	 *
	 * @param beanId
	 * @return
	 */
	public static Object getBeanByName(String beanId) {
		if (beanId == null || beanId.equals("")) {
			log.error("beanId is empty");
			return null;
		}
		//获取web容器上下文
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		return wac.getBean(beanId);

	}

	/**
	 *  违背spring的ioc解耦思想。
	 */
	public static  <T> T getBeanByType(Class<T> clazz) {
		if (clazz == null) {
			log.error("clazz is empty");
			return null;
		}
		//获取web容器上下文
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		return (T) wac.getBean(clazz);
	}
}
