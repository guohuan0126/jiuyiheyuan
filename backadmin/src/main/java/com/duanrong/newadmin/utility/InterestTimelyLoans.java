package com.duanrong.newadmin.utility;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.tools.apt.Main;

public class InterestTimelyLoans {
	
	
	public static JSONArray getDeBenxi(Double money,Double rate,Integer month){
		//每月固定应还金额
		//money01 = Double.parseDouble(String.format("%.2f", money01));
		return jisuan(money,rate,month,new JSONArray());
	}

	public static JSONArray jisuan(Double money,Double rate,Integer month,JSONArray array){
		
		Double money02 = money*rate; //当前月利息
		//money02 = Double.parseDouble(String.format("%.2f", money02));
		//Double money03 =Double.parseDouble(String.format("%.2f", money01-money02)); //当前月还本金
		//Double money04 = Double.parseDouble(String.format("%.2f", money-money03));; //剩余待还本金
	
		Double money03 = 0.0; //当前月还本金
		if(month==1){
			money03 =money;		
		}
		Double money04 = money-money03; //剩余待还本金
		money04 = money04 < 0 ? 0:money04;	
		JSONObject obj = new JSONObject();		
			obj.put("本金", String.format("%.2f", money03));
			obj.put("利息", String.format("%.2f", money02));
			obj.put("每月应还金额", String.format("%.2f", money03+money02));				
			obj.put("剩余应还本金", String.format("%.2f", money04));
			//obj.put("benxihe", String.format("%.2f", money03+money02));
		
		array.add(obj);
		month--;
		if(month > 0){
			return jisuan(money,rate,month,array); //本息和
		}else{
			return array;
		}
	}	
		
	public static void main(String[] args) {		
		JSONArray array = getDeBenxi(10000.0,0.0288,6);
		for(int i=0;i<array.size();i++){
			System.out.println(array.get(i));
		}
	}
	
	
}
