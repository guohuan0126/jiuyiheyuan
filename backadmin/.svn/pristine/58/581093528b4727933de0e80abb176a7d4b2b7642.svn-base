package base.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 能在quartz的job中，注入Spring的对象
 * 
 * @author Lin Zhiming
 * @version Feb 27, 2015 11:21:43 AM
 */
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory
		implements ApplicationContextAware {

	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
	}

	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle)
			throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}
}
