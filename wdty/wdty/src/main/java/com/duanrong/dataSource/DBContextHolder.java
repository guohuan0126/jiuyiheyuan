package com.duanrong.dataSource;

/**
 * 
 * @author xiao
 * @date 下午3:27:52
 */
public final class DBContextHolder {
	
	/** 
     * 线程threadlocal 
     */  
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();  
  
    private static String DEFAUL_DB_TYPE_RW = "dataSourceKeyRW";     
    
    /**
     * 获取本线程的dbtype
     * @return
     */
    public static String getDbType() {  
        String db = contextHolder.get();  
        if (db == null) {  
            db = DEFAUL_DB_TYPE_RW;// 默认是读写库  
        }  
        return db;  
    }  
  
    /** 
     *  
     * 设置本线程的dbtype 
     *  
     * @param str 
     */  
    public static void setDbType(String str) {  
        contextHolder.set(str);  
    }  
  
    /** 
     * clearDBType 
     *  
     * @Title: clearDBType 
     * @Description: 清理连接类型 
     */  
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}
