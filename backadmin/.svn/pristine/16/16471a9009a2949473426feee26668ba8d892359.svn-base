package com.duanrong.newadmin.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class IdUtil {
	private static int increament = 0;

	
	
	public static String getId(){
		
		increament++;
		String n = String.format("%07d", increament);	
		if (increament >= 99999999) {
			increament = 0;
		}		
		
		return n;
	}
	
	public static final String numberChar = "0123456789";
		public static String generateString(int length) // 参数为返回随机数的长度
		{
			StringBuffer sb = new StringBuffer();
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
			}
			return sb.toString();
		}
	
	public static String generateId(ToType toType) {
		//服务器名称，从配置文件里面读取。web1。长度4位
		
	
		String serviceName = LoadConstantProterties2.getValueByDefaultPro(null);
		
		//操作类型。长度4位。定义一个枚举类型。
		//yyyyMMddHHmmssSSS
		//4位随机数字
		String random = RandomStringUtils.random(4, false, true);
		
		//4位累加数据，从0开始，最大9999.
		String gid = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		increament++;
		if (increament >= 999999) {
			increament = 0;
		}
		gid += String.format("%06d", increament);
		
		String id = serviceName+toType+random+gid;
		return id;
	}
	

	
	/**
	 * @author yinxunzhi
	 * @return
	 */
	public static String randomUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 根据传来的000001 值 自增长
	 * @param num
	 * @return
	 */
	public static String getSortNum(String num){
		
		Calendar a=Calendar.getInstance();
		String str="";
		String strnum = num;
	        String numStr = String.valueOf(Integer.parseInt(strnum)+1);
	        strnum = strnum.substring(0,strnum.length()-numStr.length())+numStr;
	       str="天天赚D"+a.get(Calendar.YEAR)+strnum;
	       return str;
	}
	/**
	 * 根据传来的000001 值 自增长
	 * @param num
	 * @return
	 */
	public static String getVihicheSortNum(String num){
		
		Calendar a=Calendar.getInstance();
		String str="";
		String strnum = num;
	        String numStr = String.valueOf(Integer.parseInt(strnum)+1);
	        strnum = strnum.substring(0,strnum.length()-numStr.length())+numStr;
	       str="车押宝C"+a.get(Calendar.YEAR)+strnum;
	       return str;
	}
	public static String getVihicheSortNum(Integer num){		
		Calendar a=Calendar.getInstance();
		String str="";
	    str="车押宝C"+a.get(Calendar.YEAR)+String.valueOf("0"+num);
	    return str;
	}
	public static void main(String[] args) {
/*		String loanName="车押宝C201600009";
		String name=(String) loanName.substring(8);
		System.out.println(getVihicheSortNum(name));
		System.out.println(getVihicheSortNum("00000"));*/
	/*	Calendar a=Calendar.getInstance();
		String str="";
		
		for (int i = 1; i <= 2; i++) {
			String strnum = "00010";
			String numStr = String.valueOf(Integer.parseInt(strnum)+i);
	        strnum = strnum.substring(0,strnum.length()-numStr.length())+numStr;
	       str="车押宝C"+a.get(Calendar.YEAR)+strnum;	
	       System.out.println(str);
		}*/
		System.out.println(getVihicheSortNum(9120));
       
	}
	
}
