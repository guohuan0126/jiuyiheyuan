package com.duanrong.yeepaysign;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.cfca.util.pki.PKIException;
import com.duanrong.newadmin.utility.LoadConstantProterties;

/**
 * 生成密钥 校验密钥
 * 
 * @author 林志明
 */
public class CFCASignUtil {
	/**
	 * 生成密文 加密 P2P --> 易宝
	 */
	public static String sign(String source) {
		if (source == null) {
			return "";
		}
		String path = null;
		String password = null;
		String sign = null;
		List<String> list = new ArrayList<String>();
		list.add("duanrongcfcaConfig.properties");
		HashMap<String, Properties> loadConstantsPro = LoadConstantProterties
				.loadConstantsPro(list);
		if (loadConstantsPro != null && loadConstantsPro.size() > 0) {
			Properties properties = loadConstantsPro
					.get("duanrongcfcaConfig.properties");
			path = properties.getProperty("path");
			password = properties.getProperty("password");		
		}
		String CFCASignUtilPaht = getCFCASignUtilPaht("soarequestYeepayData");
		writeMessage(source, CFCASignUtilPaht);
		try {
			sign = SignUtil.sign(source, path, password);
		} catch (Exception e) {
			PrintStream printStream;
			try {
				PKIException t = (PKIException)e.getCause();
				if (t.getHistory() != null) {
					System.out.println(t.getClass());
					printStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(CFCASignUtilPaht, true)));
					e.fillInStackTrace().printStackTrace(printStream);
					t.getHistory().printStackTrace(printStream);
					printStream.close();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return sign;
	}

	/**
	 * 校验密文 解密 易宝 --> P2P
	 */
	public static boolean isVerifySign(String source, String sign) {
		String CFCASignUtilPaht = getCFCASignUtilPaht("SOAResponseYeepayData");
		writeMessage(source, CFCASignUtilPaht);
		try {
			SignUtil.verifySign(source, sign, "yeepay.com");			
			return true;
		} catch (Exception e) {
			PrintStream printStream;
			try {
				PKIException t = (PKIException)e.getCause();
				if (t.getHistory() != null) {					
					printStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(CFCASignUtilPaht, true)));
					e.fillInStackTrace().printStackTrace(printStream);
					t.getHistory().printStackTrace(printStream);
					printStream.close();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	public static void writeMessage(String str, String path) {
		try {
			
			FileWriter file = new FileWriter(path, true);			
			BufferedWriter out = new BufferedWriter(file);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDate = sdf.format(new Date());
			String message = "\r\n" + "[" + formatDate + "]" + str;
			System.out.println(message);
			out.write(message);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void createFile(String path){
		
		File f = new File(path);
		File parent = f.getParentFile();
        if(null != parent && !parent.exists()){
             parent.mkdirs();
        }        
	}
	
	private static String getCFCASignUtilPaht(String file){
		
		List<String> list = new ArrayList<String>();
		list.add("duanrongcfcaConfig.properties");
		HashMap<String, Properties> loadConstantsPro = LoadConstantProterties
				.loadConstantsPro(list);
		
		String CFCASignUtilPaht = null;
		if (loadConstantsPro != null && loadConstantsPro.size() > 0) {
			Properties properties = loadConstantsPro
					.get("duanrongcfcaConfig.properties");			
			CFCASignUtilPaht = properties.getProperty("CFCASignUtilPaht");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatDate = sdf.format(new Date());
		CFCASignUtilPaht = CFCASignUtilPaht+file+"."+formatDate+".txt";
		createFile(CFCASignUtilPaht);
		return CFCASignUtilPaht == null ? "" : CFCASignUtilPaht;
	}
}
