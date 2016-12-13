package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;


/**
 * 手机号归属地
 * 
 * @author Qiu Feihu
 * @version 2015年10月9日11:38:36
 */
public class PhoneNoAttributionUtils {
	
	    
	  

	/**
	    * 返回省份和城市数组
	    * @param mobile
	    * @return
	    */
	   public static String[] getAttributions(String mobile){
		   String[] arr = new String[2];
		   Document doc = null;
			try {
				doc = post(mobile);
				
				arr[0] = doc.getElementsByTag("province").text();
				 arr[1] =  doc.getElementsByTag("city").text();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   return arr;
	   }
	
		/**
	     * 获取JSON数据
	     * 
	     * @return
	     */
	    public static Document post(String mobile) throws Exception{					
		    BufferedReader bufferedReader = null;
		    String responseResult = "";
		    HttpURLConnection httpURLConnection = null;					//创建HttpURLConnection
		    Document doc=null;
		    try {		
		    	//拼接URL
		    	String url="http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile="+mobile;
		        URL realUrl = new URL(url);							
		        // 打开和URL之间的连接
		        httpURLConnection = (HttpURLConnection) realUrl.openConnection();
		        httpURLConnection.setDoOutput(true);
		        httpURLConnection.setDoInput(true);
		        // 根据ResponseCode判断连接是否成功
		        int responseCode = httpURLConnection.getResponseCode();

		        // 定义BufferedReader输入流来读取URL的ResponseData
		        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"GB2312"));
		        String line;
		        while((line = bufferedReader.readLine()) != null){
                    		            responseResult += line;
		        }
		       
		        if(responseCode == 200){
		        	 doc = Jsoup.parse(responseResult);
		        }else{
		        	System.out.println("获取失败！"+responseCode);
		        }
		    } catch (Exception e) {
		        responseResult = "send post request error!" + e;
		    } finally {
		        httpURLConnection.disconnect();
		        try {
		            if (bufferedReader != null) {
		                bufferedReader.close();
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		
		    }
		   
			
			return doc;
		}
	   
	    public static void main(String[] args){
	    	//139\138\137\136\135\134\159\158\157150\151\152\188这些都是中国移动的，
	    	//130、131、132、156、155这些都是中国联通的，
	    	//133、153、189这些都是中国电信，即以前的CDMA网络
	    	String[] mobiles = "139,138,137,136,135,134,159,158,157,150,151,152,188,130,131,132,156,186,185,155,133,153,189".split(",");
	    	for(int i=0;i<mobiles.length;i++){
	    		String[] arr = getAttributions(mobiles[i]+"10238875");
		    	System.out.println(arr[0]+","+arr[1]);
	    	}
	    	
	    }

}
