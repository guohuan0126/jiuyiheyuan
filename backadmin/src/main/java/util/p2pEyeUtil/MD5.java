package util.p2pEyeUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class MD5 {

	 public static String getMd5(byte[] buffer) throws NoSuchAlgorithmException{
		    String s  = null;
		    char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		    MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(buffer);
		    byte[] datas = md.digest(); //16个字节的长整数
		    char[] str = new char[2*16];
		    int k = 0;
		    for(int i=0;i<16;i++){
		      byte b   = datas[i];
		      str[k++] = hexDigist[b>>>4 & 0xf];//高4位
		      str[k++] = hexDigist[b & 0xf];//低4位
		    }
		    s = new String(str);
		    return s;
	}
	 	/**
		 * 参数做一个字母小写升序排序
		 * @param strParam
		 * @return
		 */
		public static String paramSort(String strParam){
			if(null != strParam){
				String[] str = strParam.split("&");
				StringBuilder sb = new StringBuilder();
				if(null != str){
					List<String> list = new ArrayList<String>();
					Map map = new HashedMap();
					for(int i=0;i<str.length;i++){
						String str1[] = str[i].split("=");
						if(null != str1){
							if(str1.length>1){
								map.put(str1[0], str1[1]);
							}else{
								map.put(str1[0], "");
							}
							list.add(str1[0].toLowerCase()); // 全部转成小写字母
						}
					}
					// 排序后
					list = MD5.sort(list);
					for(int i=0;i<list.size();i++){
						sb.append(list.get(i)+"="+map.get(list.get(i)));
						if(list.size()-1 != i){
							sb.append("&");
						}
					}
				}else{
					return null;
				}
				return sb.toString();
			}
			return null;
		}
		
		/**
		 * 按字母升序排序 ac,ba,c,d
		 * @param list
		 * @return
		 */
		public static List<String> sort(List<String> list){
			Collections.sort(list);
			return list;
		}
}
