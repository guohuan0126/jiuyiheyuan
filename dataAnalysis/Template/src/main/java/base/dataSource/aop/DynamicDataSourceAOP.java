package base.dataSource.aop;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.Ordered;

import base.dataSource.DBContextHolder;

/**
 * 动态数据源切换aop
 * @author xiao
 * @date 2015年7月23日下午4:17:13
 */
public final class DynamicDataSourceAOP implements Ordered{


	/**
	 * 方法参数，定义了方法，数据源的映射
	 */
	private Map<String, String> methods;

	
	/**
	 * aop的执行顺序
	 */
	private int order;
		
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}

	public Map<String, String> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, String> methods) {
		this.methods = methods;
	}
	
	public void dynamicDataSource(JoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName().toUpperCase();
		for (String method : methods.keySet()) {
			String m = method.toUpperCase();
			if (method.contains("*")
					&& (methodName.startsWith(m.substring(0, m.indexOf("*") - 1))
							|| methodName.equalsIgnoreCase(m.substring(0,
									m.indexOf("*") - 1)) || !method
							.contains("*") && methodName.equalsIgnoreCase(m))) {
				DBContextHolder.setDbType(methods.get(method));
			}
		}					
	}

	
}
