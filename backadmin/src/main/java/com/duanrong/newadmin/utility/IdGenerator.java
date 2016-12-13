package com.duanrong.newadmin.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IdGenerator {

	private static long increament = 0;

	public static String randomUUID() {
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
	}

	public static String generateOrderNo() {
		// 3位累加数据，从0开始，最大9999.
		String gid = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		int i = new Random().nextInt(89999999)+10000000;
		return gid+i;
	}

	
}
