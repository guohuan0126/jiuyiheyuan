package com.duanrong.newadmin.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdGenerator {
	
	private static long increament = 0;
	
	
	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	
	public static String generateLoanId() {
		
		// 3位累加数据，从0开始，最大9999.
		String gid = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		increament++;
		gid += String.format("%03d", increament);	
		if (increament >= 999) {
			increament = 0;
		}
		return gid;		
		/*String gid = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		List<Loan> contractList = loanDao.generateId(gid + "%");
		Integer itemp = 0;
		if (contractList.size() == 1) {
			Loan loan = contractList.get(0);
			String temp = loan.getId();
			temp = temp.substring(temp.length() - 6);
			itemp = Integer.valueOf(temp);
		}
		itemp++;
		gid += String.format("%06d", itemp);*/
		//return gid;
	}
}
