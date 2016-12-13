package com.duanrong.business.award.model;

/**
 * 项目类型枚举
 * @author xiao
 * @date 下午4:43:52
 */
public enum ItemType {	
	
		
	
	AWARD("award"), //财务专用
	
	//流程操作
	UNDEAL("undeal"), //未处理
	APPROVE("approve"), //同意
	UNAPPROVED("unapproved"); //否决
	
	
	// 定义私有变量
    private String itemType ;

    // 构造函数，枚举类型只能为私有
    // 为枚举类型赋值
    private ItemType( String itemType) {
       this.itemType = itemType;
    }
    
    //重写toString方法， 返回枚举值
	public String toString(){ 
		return String.valueOf(this.itemType);
	}		
	
}
