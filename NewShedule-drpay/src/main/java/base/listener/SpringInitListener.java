package base.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.jedis.ICustomer;

public class SpringInitListener implements ApplicationListener<ContextRefreshedEvent>  {
	
	@Resource
	ICustomer investCustomer;
	
	@Resource
	ICustomer doubleElevenInvestActive;
	
	@Resource
	ICustomer isFirstInvest;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {	
		DRJedisMQ.customer("activity_invest", investCustomer);
		DRJedisMQ.customer("isFirst_invest", isFirstInvest);
	}
	
	

}
