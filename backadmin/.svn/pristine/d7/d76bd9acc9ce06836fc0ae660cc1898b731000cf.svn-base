package com.duanrong.newadmin.utility;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.tools.apt.Main;

public class InterestAccrual {
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
	 * 等额本息的计算
	 * @param money
	 * @param rate
	 * @param month
	 * @param loanMoney
	 * @param compositeInteresRate
	 * @return
	 */
	public static JSONArray getDeBenxi(Double money,Double rate,Integer month,double loanMoney,double compositeInteresRate){
		double money01 = loanMoney/month+loanMoney*compositeInteresRate;//每月固定应还金额
		//money01 = Double.parseDouble(String.format("%.2f", money01));
		return jisuan(money,rate,month,new JSONArray(),money01);
	}

	public static JSONArray jisuan(Double money,Double rate,Integer month,JSONArray array,double money01){
		
		Double money02 = money*(rate/12.0); //当前月利息
		//money02 = Double.parseDouble(String.format("%.2f", money02));
		//Double money03 =Double.parseDouble(String.format("%.2f", money01-money02)); //当前月还本金
		//Double money04 = Double.parseDouble(String.format("%.2f", money-money03));; //剩余待还本金
	
		Double money03 = money01-money02; //当前月还本金
		Double money04 = money-money03; //剩余待还本金
		if(month==1){
			money03 =money;
			money04=money-money03;
			money02=money01-money03;
		}
		money04 = money04 < 0 ? 0:money04;	
		JSONObject obj = new JSONObject();		
		obj.put("benjin", String.format("%.2f", money03));
			obj.put("lixi", String.format("%.2f", money02));
			obj.put("money", String.format("%.2f", money01));				
			obj.put("shengyu", String.format("%.2f", money04));
			obj.put("benxihe", String.format("%.2f", money03+money02));	
		
		array.add(obj);
		month--;
		if(month > 0){
			return jisuan(money04,rate,month,array,money01); //本息和
		}else{
			return array;
		}
	}	
	//调用先息后本的计算公式
	public static JSONArray getTimelyDeBenxi(Double money,Double rate,Integer month){
		//每月固定应还金额
		//money01 = Double.parseDouble(String.format("%.2f", money01));
		return jisuanTimely(money,rate,month,new JSONArray());
	}
	/**
	 * 先息后本的计算公式
	 * @param money
	 * @param rate
	 * @param month
	 * @param array
	 * @return
	 */
   public static JSONArray jisuanTimely(Double money,Double rate,Integer month,JSONArray array){
		
		Double money02 = money*rate; //当前月利息
		Double money03 = 0.0; //当前月还本金
		if(month==1){
			money03 =money;		
		}
		Double money04 = money-money03; //剩余待还本金
		money04 = money04 < 0 ? 0:money04;	
		JSONObject obj = new JSONObject();		
			obj.put("benjin", String.format("%.2f", money03));
			obj.put("lixi", String.format("%.2f", money02));
			obj.put("money", String.format("%.2f", money03+money02));				
			obj.put("shengyu", String.format("%.2f", money04));
			obj.put("benxihe", String.format("%.2f", money03+money02));
		
		array.add(obj);
		month--;
		if(month > 0){
			return jisuanTimely(money,rate,month,array); //本息和
		}else{
			return array;
		}
	}
   /**
    * 韭农贷先息没有本的计算公式
    * @param loanMoney
    * @param compositeInteresRate
    * @param month
    * @param array
    * @return
    */
   public static JSONArray jisuanLeek(Double money,Double loanMoney,Double compositeInteresRate,Double rate,Integer month,JSONArray array){
 		Double money02 = loanMoney*compositeInteresRate; //当前月利息（包含手续费）
 		Double lixi = money*(rate/12);//每月真实的利息
 		Double money03 = 0.0; //当前月还本金
 		Double money04 =money; //剩余待还本金
 		JSONObject obj = new JSONObject();
 		Double servicefee = money02-lixi< 0 ? 0: money02-lixi; //服务费=月利息-每月真实利息
		obj.put("benjin", String.format("%.2f", money03));
		obj.put("lixi", String.format("%.2f", money02));
		obj.put("serviceMoney", String.format("%.2f", servicefee));
		obj.put("money", String.format("%.2f", money03+money02));				
		obj.put("shengyu", String.format("%.2f", money04));
		obj.put("benxihe", String.format("%.2f", money03+money02));
 		   
 		array.add(obj);
 		month--;	
 		if(month > 0){
			return jisuanLeek(money,loanMoney,compositeInteresRate,rate,month,array); //本息和
		}else{
			return array;
		} 
 	}
   /**
    * 调用先息的计算公式
    * @param money
    * @param loanMoney
    * @param compositeInteresRate
    * @param month
    * @return
    */
	public static JSONArray getjisuanLeek(Double money,Double loanMoney,Double compositeInteresRate,Double rate,Integer month){
		return jisuanLeek(money,loanMoney,compositeInteresRate,rate,month,new JSONArray());
	}
   /**
    * 先息后本大于20万拆标计算公式
    * @param totalMoney
    * @param chushu
    * @param i 一共几期
    * @param j 当期期是第几期
    * @return
    */
  public static double getaverageMoney(Double totalMoney,Double chushu,Integer i,Integer j){
		double averageMoney=Math.floor(totalMoney/i);	 
		if(j==i){
		 averageMoney=totalMoney-averageMoney*(i-1);			
		}
		  return averageMoney;
  }
  /**
   * 三农等额本息提前还款退还服务费计算
   * @param money
   * @param servicetotalMoney
   * @param loanTerm
   * @param month
   * @return
   */
 public static double getServiceMoney(Double money,Double servicetotalMoney,Integer loanTerm,Integer month){
	  double serviceMoney = 0.0;
	  if(month==3){
		serviceMoney=servicetotalMoney/loanTerm*(loanTerm-month)-money*0.1;
	  }
	  if(month>=4 && month<=loanTerm){
		 serviceMoney=servicetotalMoney/loanTerm*(loanTerm-month)-money*0.05;  
	  }
	  if(serviceMoney<=0)serviceMoney=0.0;
	  return round(serviceMoney, 2);
 }
//调用先息后本的计算公式
	public static JSONArray gethuimu(Double money,Double loanMoney,Double compositeInteresRate,Double rate,Integer month){
		double money01 = loanMoney/month+loanMoney*compositeInteresRate;//等本等息每月固定应还金额
		return huimu(money,rate,month,new JSONArray(),money01);
	}
/**
 * 惠牧贷和韭农贷等额本息计算
 * @param money
 * @param rate
 * @param month
 * @param array
 * @param money01
 * @return
 */
 public static JSONArray huimu(Double money,Double rate,Integer month,JSONArray array,double money01){		
		double moneyRate = rate/12.0;//月利率
		double money02 = money*moneyRate; //等额本息当前月利息	
		double monthMoney =money*moneyRate*Math.pow(1+moneyRate,month)/(Math.pow(1+moneyRate,month)-1);//等额本息月还金额
		double money03 = monthMoney-money02; //当前月还本金=等额本息月还金额-等额本息月利息
		double servicefee = money01-monthMoney; //服务费=等本等息月金额-等额本息月金额
		Double money04 = money-money03; //剩余待还本金
		money04 = money04 < 0 ? 0:money04;	
		JSONObject obj = new JSONObject();	
			obj.put("benjin", String.format("%.2f", money03));
			obj.put("lixi", String.format("%.2f", money02));
			obj.put("serviceMoney", String.format("%.2f", servicefee));
			obj.put("money", String.format("%.2f", money01));		
			obj.put("shengyu", String.format("%.2f", money04));
			obj.put("benxihe", String.format("%.2f", money01));	
		array.add(obj);
		month--;
		if(month > 0){
			return huimu(money04,rate,month,array,money01); 
		}else{
			return array;
		}
	}	
	public static void main(String[] args) {		
		JSONArray array = gethuimu(20800.0, 20000.0, 0.022, 0.12, 3);
		for(int i=0;i<array.size();i++){
			System.out.println("第"+(i+1)+"期");
			System.out.println(array.get(i));
		}
		JSONArray array1 = gethuimu(21600.0, 20000.0, 0.02, 0.12, 6);
		for(int i=0;i<array1.size();i++){
			System.out.println("第"+(i+1)+"期");
			System.out.println(array1.get(i));
		}
		JSONArray array2 = gethuimu(32400.0, 30000.0, 0.02, 0.12, 6);
		for(int i=0;i<array2.size();i++){
			System.out.println("第"+(i+1)+"期");
			System.out.println(array2.get(i));
		}
		/*JSONArray array = getTimelyDeBenxi(10000.0,0.0288,6);
		for(int i=0;i<array.size();i++){
			System.out.println(array.get(i));
		}*/
/*		System.out.println(Math.floor(3.7));//舍掉小数取整
		System.out.println(Math.rint(3.3));//四舍五入取整
		System.out.println(Math.ceil(3.3));//进位取整
		double totalMoney=702731.0;
		double chushu =200000.0;
		double n= Math.ceil(totalMoney/chushu);
		int i=(int)n;
		System.out.println(i);
		//大于20万的拆标，具体要拆几个，就是要平均分，让总金额除以20万，得到进位整数n
		//其次循环n,并得到平均数，平均数为1的整数，在最后一期平衡账户=总金额-前面几期的金额和
		for (int j = 0; j < i; j++) {
			double averageMoney=Math.floor(totalMoney/i);
			//double averageMoney=totalMoney/i;
			if(j==i-1){
				averageMoney=totalMoney-averageMoney*(i-1);
			}
			System.out.println(averageMoney);
		}*/
/*		double totalMoney=702731.0;
		double chushu =200000.0;
		double n= Math.ceil(totalMoney/chushu);
		int i=(int)n;
		for (int j = 1; j <= i; j++) {
			System.out.println(getaverageMoney(totalMoney, chushu, i, j));			
		}*/
	/*	JSONArray array = getjisuanLeek(54000.0,50000.0,0.02, 1);
		for(int i=0;i<array.size();i++){
			System.out.println(array.get(i));
		}*/
	}
	
	
}
