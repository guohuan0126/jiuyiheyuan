package com.duanrong.util;

import java.math.BigDecimal;

/**
 * 
 * 利息计算工具类
 * 
 * @author xiao
 * 
 * @date 2016/3/7
 *
 * @version 1.0
 */
public final class InterestUtil {

	
	/**
	 * 天标利息
	 * 
	 * @param money 金额
	 * @param rate 利率
	 * @param days 天数
	 * @return
	 */
	public static double getInterestByPeriodDay(double money, double rate,
			int days){		
		return round(money * rate / 365 * days, 2);
	}


	/**
	 * 月标利息
	 *
	 * @param money 金额
	 * @param rate 利率
	 * @param moths 月数
	 * @return
	 */
	public static double getInterestByPeriodMoth(double money, double rate,
													 int moths, String repayType){
		if(null == repayType || "".equals(repayType.trim())){
			throw new IllegalArgumentException("还款方式repayType不能为空");
		}
		if (LoanConstants.RepayType.RFCL.equals(repayType)){
			return getRFCLInterestByPeriodMoth(money,rate,moths);
		}else if(LoanConstants.RepayType.DQHBFX.equals(repayType)){
			return getDQHBFXInterestByPeriodMoth(money,rate,moths);
		}else if(LoanConstants.RepayType.CPM.equals(repayType)){
			double principal = principal(money, rate, moths);//每月本息和  
			return getCPMInterestSum(money, rate, moths, 0,principal);
		}else{
			throw new IllegalArgumentException("未知的还款方式repayType");
		}

	}

	/**
	 * 月标利息(按月计息)
	 * 
	 * @param money 金额
	 * @param rate 利率
	 * @param moths 月数
	 * @return
	 */
	public static double getRFCLInterestByPeriodMoth(double money, double rate,
			int moths){		
		return moths * round(money * rate / 12, 2);
	}

	/**
	 * 月标利息(一次性还本付息)
	 *
	 * @param money 金额
	 * @param rate 利率
	 * @param moths 月数
	 * @return
	 */
	public static double getDQHBFXInterestByPeriodMoth(double money, double rate,
													 int moths){
		return round(moths * money * rate / 12, 2);
	}

	/**
	 * 应得利息
	 * @param money 金额
	 * @param rate 利率
	 * @param periods 周期，天标则为天数，月标为月数
	 * @param periodType 周期类型
	 * @return
	 */
	public static double getInterestByPeriod(double money, double rate,
			int periods, String periodType, String repayType){
		if(null == periodType || "".equals(periodType.trim())){
			throw new IllegalArgumentException("周期类型periodType不能为空");
		}
		if(periodType.equals(LoanConstants.LoanPeriodType.DAY)){
			return getInterestByPeriodDay(money, rate, periods);
		}else if(periodType.equals(LoanConstants.LoanPeriodType.MOTH)){
			return getInterestByPeriodMoth(money, rate, periods, repayType);
		}else{
			throw new IllegalArgumentException("未知的周期类型periodType");
		}
	}
	/**
	 * 精确四舍五入
	 * @param num  数字
	 * @param scale 小数位
	 * @return
	 */
	private static double round(double num, int scale) {	
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
    
    /** 等额本息，计算利息和
     * @param money 贷款总额 
     * @param rate  年化利率 
     * @param month 月份
     * @return 利息息和
     */  
    public static double getCPMInterestSum(double money,double rate,int month){  
        double principal = principal(money, rate, month);//每月本息和  
        double interestSum = 0.0;//利息和
        return getCPMInterestSum(money, rate, month,interestSum,principal);
    } 
    
    public static double getCPMInterestSum(double money,double rate,int month,double interestSum,double principal){  
        double interest = money*resMonthRate(rate);//当月利息
        money = money-principal+interest;//剩余本金
        month--;
        interestSum += round(interest, 2);
        if(month > 0){
        	return getCPMInterestSum(round(money,2), rate, month,interestSum,principal);
        }
        return round(interestSum, 2);
    } 
    
    /** 等额本息，计算第N期应还利息
     * @param money 贷款总额 
     * @param rate  年化利率 
     * @param month 月份
     * @param period 第几期
     * @return 利息息和
     */  
    public static double interest(double money,double rate,int month,int period){  
    	double principal = principal(money, rate, month);//每月本息和  
        return interest(money, rate, month,period,principal);
    } 
    public static double interest(double money,double rate,int month,int period,double principal){  
    	
    	period = month - period;
        double interest = round(money*resMonthRate(rate),2);//当月利息
        money = money-principal+interest;//剩余本金
        month--;
        if(month > period){
        	return interest(round(money,2), rate, month,month-period,principal);
        }
        return round(interest, 2);
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
    
    
    public static double surplusCorpus(double money,double rate,int month,int period){  
    	double principal = principal(money, rate, month);//每月本息和  
    	double corpusSum = 0.0;
        return surplusCorpus(money,money, rate, month,period,principal,corpusSum);
    } 
    
    public static double surplusCorpus(double totleMoney,double money,double rate,int month,int period,double principal,double corpusSum){  
    	period = month - period;
        double interest = money*resMonthRate(rate);//当月利息
        month--;
        double corpus = principal-interest;//当月本金
        corpusSum += round(corpus,2);
        if(month > period){
            money = money-corpus;//剩余本金 
        	return surplusCorpus(totleMoney,round(money,2), rate, month,month-period,principal,corpusSum);
        }
        return round(totleMoney-corpusSum,2);
    }
}