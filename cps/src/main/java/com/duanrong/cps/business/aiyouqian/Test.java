package com.duanrong.cps.business.aiyouqian;

public class Test {

	
	//java 中文编码函数:
		public static final boolean isChinese(char c) {   
			    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
			    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
			            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
			            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
			            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
			            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
			            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
			        return true;  
			    }  
			    return false;  
			}


		public static String stringToUnicode(String s){
				String st = "";
				for(int i = 0; i < s.length(); i++){
		            if(isChinese(s.charAt(i)))
		            	st = st+"\\u"+Integer.toHexString(s.codePointAt(i)).toLowerCase();
		            else
		            	st += s.charAt(i);
				}
				return st;
			}

		
		public static void main(String[] args){
			String str=Test.stringToUnicode(".");
			System.out.println(str);
		}
}
