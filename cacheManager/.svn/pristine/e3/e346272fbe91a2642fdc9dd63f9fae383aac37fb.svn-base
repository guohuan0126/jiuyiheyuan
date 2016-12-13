package com.duanrong.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DRedisClient {

	Log log = LogFactory.getLog(DRedisClient.class);
	
	PropReader read = new PropReader("/redis.properties");
	
	String host = read.readProperty("host");
	
	int port = Integer.parseInt(read.readProperty("port"));
	
	int timeout = Integer.parseInt(read.readProperty("timeout"));
	
	String password = read.readProperty("password");
	
	private static DRedisClient dredisClient = new DRedisClient();
	
	private DRedisClient(){
		log.info("****************************************");		
		log.debug("host:"+host+"\t port:"+port+"\t timeout:"+timeout + "\t password:" + password);
		log.info("*************DRedisClient初始化***********");
	}
	
	private static final byte[] lock = new byte[0];
	
	public String send(String msg){
		Socket socket = new Socket();
        StringBuffer s = new StringBuffer();
        try {
        	socket.setReuseAddress(true);
            socket.setKeepAlive(true);      
            socket.setTcpNoDelay(true);     
            socket.setSoLinger(true, 0); 
        	socket.connect(new InetSocketAddress(host, port));
            socket.setSoTimeout(timeout);
            OutputStream os = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            int line = 0;
            byte[] b = new byte[1024];
            if(password != null && !password.equals("")){
            	 os.write(("auth "+password+"\r\n").getBytes());
                 os.flush();                      
                 while((line = in.read(b)) != -1) {
                     String result = new String(b, "UTF-8");
                     b = new byte[1024];
                     log.debug("line:"+line);
                     log.debug(result);
                     if(result.contains("+OK")){
                    	 log.debug("**************************************");
                    	 log.debug("msg:"+msg);
                    	 os.write((msg + "\r\n").getBytes());
                         os.flush();
                         socket.shutdownOutput();
                     }else{
                         s.append(result);
                     }
                 }
            }else{
            	 os.write((msg+"\r\n").getBytes());
                 os.flush();
                 socket.shutdownOutput();
                 while((line = in.read(b)) != -1) {
                     String result = new String(b, "UTF-8");
                     b = new byte[1024];
                     s.append(result);       
                 }
            }
            in.close();
            os.close();  
            socket.close();
            log.debug(s);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
	}

	public static DRedisClient geClient(){
		if(dredisClient == null){
			 synchronized (lock){
				 if(dredisClient == null) dredisClient = new DRedisClient();
			 }
		}
		return dredisClient;
	}
}
