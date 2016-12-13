package com.dr.test;

//import org.junit.Test;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duanrong.newadmin.utility.HttpUtil;
import com.duanrong.newadmin.utility.MD5Encry;


public class TestUrl{
	
	

	public static void main(String[] args) {
//	String url = "/api/v2/user_fields.json";
//		JSONObject param = new JSONObject();		
//		JSONObject json = HttpUtil.sendGet(param,url);
//		System.out.println(json);	
//		JSONArray dataArray =json.getJSONArray("user_fields");
//		for (int i = 0; i < dataArray.size(); i++) {
//			JSONObject bean=(JSONObject) JSONObject.toJSON(dataArray.get(i));
//			System.out.println(bean);
//		   if(bean.getString("field_label").equals("手机号")){
//			   System.out.println(bean.getString("field_name"));
//		   }
//		}
		
		String url = "/api/v2/customer_import.json";
		JSONObject userpParam = new JSONObject();		
		JSONObject customerParams = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		customerParams.put("TextField_5422", df.format(new Date()));
		userpParam.put("email", "ceshi2@163.com");
		userpParam.put("nick_name", "api创建的5");
		userpParam.put("cellphone", "15071347862");
		userpParam.put("qq", "112527851");
		userpParam.put("org_name", "久亿恒远");
		/*userpParam.put("owner_group_name", "规则测试组");*/
		userpParam.put("agent_email", "fengcuihua@duanrong.com");
		userpParam.put("customer_field",customerParams );
		JSONObject param = new JSONObject();	
		param.put("user", userpParam);
		JSONObject json = HttpUtil.sendPost(param,url);
		System.out.println(customerParams);
		System.out.println(userpParam);
		System.out.println(param);
		System.out.println(json);
		
	}

}
