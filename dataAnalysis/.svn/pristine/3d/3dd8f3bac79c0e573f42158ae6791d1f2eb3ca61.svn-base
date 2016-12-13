package com.duanrong.dataAnalysis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetTimeUtil {

	
	
	public static String getBeginTime(int days){
		Calendar   c   =   Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,days);//关键是这个7天前....
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd ");
		String beginTime=sdf.format(c.getTime());
		return beginTime;
				
		
	}
	public static String getEndTime(){
		   Date date = new Date();  
           SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");  
           String nowDate = sf.format(date);  
           //System.out.println(nowDate);  
           //通过日历获取下一天日期  
           Calendar cal = Calendar.getInstance();  
           try {
			cal.setTime(sf.parse(nowDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
           cal.add(Calendar.DAY_OF_YEAR, +1);  
           String nextDate_1 = sf.format(cal.getTime());  
          // System.out.println(nextDate_1);  
		return nextDate_1;
	}
	public static String getLastTime(){
		   Date date = new Date();  
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");  
        String nowDate = sf.format(date);  
        //System.out.println(nowDate);  
        //通过日历获取下一天日期  
        Calendar cal = Calendar.getInstance();  
        try {
			cal.setTime(sf.parse(nowDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        cal.add(Calendar.DAY_OF_YEAR, -1);  
        String nextDate_1 = sf.format(cal.getTime());  
       // System.out.println(nextDate_1);  
		return nextDate_1;
	}
	public static void main(String[] args) {
		Calendar   c   =   Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-30);//关键是这个7天前....
		SimpleDateFormat   sdf   =   new   SimpleDateFormat( "yyyy-MM-dd ");
		String beginTime=sdf.format(c.getTime());
		//System.out.println(beginTime);
		System.out.println(getLastTime());
	}
}
