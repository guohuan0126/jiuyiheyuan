package com.duanrong.udesk.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.newadmin.utility.HttpUtil;
import com.duanrong.udesk.service.UdeskService;

@Service
public class UdeskServiceImpl implements UdeskService{


	/**
	 *  获取udesk用户自定义字段
	 * @return
	 */
	@Override
	public JSONObject udeskUserfields() {
		String url = "/api/v2/user_fields.json";
		JSONObject param = new JSONObject();		
		JSONObject result = HttpUtil.sendGet(param,url);
		//System.out.println(result);
		return result;
	}

	@Override
	public JSONObject udeskCustomerImport(JSONObject customerParams,JSONObject userpParam){		
		String url = "/api/v2/customer_import.json";
		userpParam.put("customer_field",customerParams );
		JSONObject param = new JSONObject();	
		param.put("user", userpParam);
		JSONObject result = HttpUtil.sendPost(param,url);
		/*System.out.println(result);*/
		return result;
	}
	


}
