package com.duanrong.newadmin.utility;

import java.math.BigDecimal;

public class ForkloansCompute {
	/**
	 * 等本金农贷原先拆标金额
	 * @param money
	 * @param month
	 * @param period
	 * @return
	 */
	 public static double remainmoney(double money,int month,int period){ 
		 double interest = Math.floor(money/(month*100))*100;
		 double remainMoney =0.0;
		 if(period==month){
			remainMoney = money-interest*(month-1); 
		 }else{
			remainMoney= interest;
		 }	        
			return remainMoney;
	       
	 }
		/**
		 * 精确四舍五入
		 * @param num  数字
		 * @param scale 小数位
		 * @return
		 */
		public static double round(double num, int scale) {	
			BigDecimal bd = new BigDecimal(Double.toString(num));
			return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		 /** 
	     *  
	     * 转换为月利率 
	     * @param rate 
	     * @return 
	     */  
	    public static double resMonthRate(double rate){  
	        return rate/12;  
	    }
		/** 等额本息，每月本息和
	     * @param money 贷款总额 
	     * @param rate  年化利率 
	     * @param month 月份
	     * @return 本息和
	     */  
	    public static double principal(double money,double rate,int month){  
	        double monRate=resMonthRate(rate);//月利率
	        double principal = money*monRate*Math.pow((1+monRate),month)/(Math.pow((1+monRate),month)-1);//每月本息和  
	        return round(principal, 2);
	          
	    } 
	 /** 等额本息，计算第N期应还本金
	     * @param money 贷款总额 
	     * @param rate  年化利率 
	     * @param month 月份
	     * @param period 第几期
	     * @return 第N期应还本金
	     */  
	    public static double corpus(double money,double rate,int month,int period){  
	    	double principal = principal(money, rate, month);//每月本息和  
	    	double corpusSum = 0.0;
	        return corpus(money,money, rate, month,period,principal,corpusSum);
	    } 
	    
	    public static double corpus(double totleMoney,double money,double rate,int month,int period,double principal,double corpusSum){  
	    	period = month - period;
	        double interest = money*resMonthRate(rate);//当月利息
	        month--;
	        if(month == 0){
	       	 	return round(totleMoney-corpusSum,2);
	        }
	        double corpus = principal-interest;//当月本金
	        corpusSum += round(corpus,2);
	        if(month > period){
	            money = money-corpus;//剩余本金 
	        	return corpus(totleMoney,round(money,2), rate, month,month-period,principal,corpusSum);
	        }
	        return round(corpus, 2);
	    }
}