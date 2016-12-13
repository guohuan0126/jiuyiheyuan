package com.duanrong.newadmin.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUtil {
		
	public final static int SUCCESS = 1 ;
	
	public final static int FAIL = 0 ;
	
	public static String getResponseBodyAsString(String url ){
		GetMethod get = new GetMethod(url);
		
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(get);
//			System.out.println(url);
//			System.out.println( get.getResponseCharSet() );
			return get.getResponseBodyAsString();
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public static int saveHtmlFromRemoteSite(String url ,String filePath){
		File file = new File(filePath);
			
		return saveHtmlFromRemoteSite(url ,file) ;
		
	}
	
	
	public static int saveHtmlFromRemoteSite(String url ,File file){
		if(!file.exists()){
			//file.mkdirs();
			try {
				File temp = file.getParentFile();
				if(!temp.exists()){
					temp.mkdirs();
				}
				file.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
				return FAIL ;
			}
		}
		
		final String response = getResponseBodyAsString(url);
		//System.out.println(response);
//			FileUtils.writeStringToFile(file, response , EncodingUtil.UTF8);
//			FileUtils.writeByteArrayToFile(file, response.getBytes( "utf-8" ));
		
		return SUCCESS ;
	}
}
