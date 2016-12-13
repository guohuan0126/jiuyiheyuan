package com.duanrong.cps.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


//读取txt文件中的内容
public class ReadUtil {

	public static String getAddr()
	{
	    String path=ReadUtil.class.getClassLoader().getResource("addrs.txt").getPath();
	    File file=new File(path);
	    FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(fis,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br=new BufferedReader(isr);
		StringBuffer line=new StringBuffer();
		String str=null;
		try {
			while((str=br.readLine())!=null){
				return str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getAddr());
		String[] addrs=getAddr().split(",");
		for (String addr : addrs) {
			System.out.println(addr);
		}
	}
}
