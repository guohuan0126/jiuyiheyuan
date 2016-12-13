package base.schedule.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * 启动初始化调度
 * 
 * @author Lin Zhiming
 * @version Mar 25, 2015 5:25:31 AM
 */
@Component
public class InitJobs implements ApplicationListener<ContextRefreshedEvent> {


	// 开启哪些调度，能手动控制
	//@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root application context 没有parent，他就是老大
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。 初始化所有的调度。

		}
	}

	

}
