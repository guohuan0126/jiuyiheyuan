package com.duanrong.cps.business.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.Log;

import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.platform.dao.PlatformDao;
import com.duanrong.cps.business.platform.service.PushService;
import com.duanrong.cps.util.FastJsonUtil;
import com.duanrong.cps.util.ReadProperties;
import com.duanrong.cps.util.SpringBeanUtil;
import com.duanrong.util.jedis.ICustomer;
import com.duanrong.util.jedis.RollbackException;

@Service
public class RedisCallBackServiceImpl implements ICustomer{

	@Autowired
	private PlatformDao platformDao;
	@Autowired
	private Log log;
	
	/**
	 * 有投资记录时将调用些方法
	 */
	@Override
	public void customer(String key, String message) throws RollbackException {
	
			//在list里取到投资编号 message
			Map<String, Object> map = (Map<String, Object>) FastJsonUtil.jsonToObj(message, Map.class);  
			System.out.println("#######################redis:"+key+":"+message);
			String investId="'"+map.get("investId")+"'";     //sql里是根据investId,in方法查找
			Map<String,Object>params=new HashMap<String,Object>();
			params.put("id", investId);
			
			Exception e=null;
			try {
				List<Invest>investList=platformDao.queryInvest(params);  //如果我们的同一个用户绑定了多个平台那每个平台均需推送
				System.out.println("###############redis:"+investList.toString());
				for(int i=0; i<investList.size(); i++){  //推送投资记录
					String platformType=investList.get(i).getPlatformType();
					String service=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", platformType);
					if(StringUtils.isNotBlank(service)){
						PushService pushService  = (PushService) SpringBeanUtil.getBeanByName(service);
						pushService.pushMethod(params);
					}
				}
				//无论是什么平台的用户投资都应推送标的状态等记录
				String aboutLoanPushService=ReadProperties.getPropetiesValue("constant/thiredPlatform.properties", "thirdpushService");
				String[] array=aboutLoanPushService.split(",");
				for(int i=0; i<array.length; i++){   //可能有多个第三方平台需要推送标的相关的信息
					PushService pushService  = (PushService) SpringBeanUtil.getBeanByName(array[i]);
					System.out.println("#####################:"+pushService);
					pushService.pushLoanAbout(params);
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
				e=e2;
			}
			
			if(e!=null){   //确保每个第三方均推送成功后再回滚
				log.errLog("redis回调异常", e.toString());
				throw new RollbackException();
				
			}
	}

}
