package test;

import com.duanrong.util.jedis.DRJedisCacheUtil;

public class CacheTest {

	public static void main(String[] args) {
		System.out.println(DRJedisCacheUtil.get("k"));;

	}

}
