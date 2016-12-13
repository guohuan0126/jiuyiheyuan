package com.duanrong.util.jedis;

import com.duanrong.util.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author xiao
 *
 */
public final class DRJedisCacheUtil {
	/**
	 * 根据正则查询所有符合规则的key
	 * @param patten
	 * @return
	 */
	public static Set<String> keys(String patten){
		return DRJedisCache.keys(patten);
	}
	/**
	 * 判断key存在
	 * @param key
	 * @return
	 */
	public static boolean exists(String key){
		return DRJedisCache.exists(key);
	}

	/**
	 * 设置key的过期时间
	 * @param key
	 * @return
	 */
	public static void expire(String key, int exp){
		DRJedisCache.expire(key, exp);
	}

	/**
	 * 删除指定的key
	 * @param key
	 * @return
	 */
	public static void del(String key){
		DRJedisCache.del(key);
	}

	/**
	 * 将String 类型 key设置为value
	 * @param key
	 * @param value
     * @return
     */
	public static void set(String key, String value){
		DRJedisCache.set(key, value);
	}

	/**
	 * 将String类型 key设置为value
	 * @param key
	 * @param value
	 * @param exp 缓存时间（秒）
     * @return
     */
	public static void set(String key, String value, int exp){
		DRJedisCache.set(key, value, exp);
	}
	
	/**
	 * 将String 类型 key设置为value
	 * @param key
	 * @param value
     * @return
     */
	public static void set(String key, Object value){
		set(key, FastJsonUtil.objToJson(value));
	}

	/**
	 * 将String类型 key设置为value
	 * @param key
	 * @param value
	 * @param exp 缓存时间（秒）
     * @return
     */
	public static void set(String key, Object value, int exp){
		set(key, FastJsonUtil.objToJson(value), exp);
	}

	/**
	 * 读取String的值key
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return DRJedisCache.get(key);
	}

	/**
	 * 读取String的值key
	 * @param key
	 * @return
	 */
	public static Object get(String key, Class<?> clazz){
		String str = get(key);
		if(str == null || str.equals("")) return null;
		if(str.startsWith("[") && str.endsWith("]")) return FastJsonUtil.jsonToArray(str, clazz);
		return FastJsonUtil.jsonToObj(str, clazz);
	}
	
	/**
	 * 向 hash 中添加数据
	 * @param key key
	 * @param hash hash数据
	 * @return
	 */
	public static void hsetstr(String key,  Map<String, String> hash){
		hsetstr(key, 0, hash);
	}
	
	/**
	 * 向 hash 中添加数据
	 * @param key key
	 * @param hash hash数据
	 * @return
	 */
	public static void hset(String key,  Map<String, Object> hash){
		hset(key, 0, hash);
	}

	/**
	 * 向 hash中添加数据
	 * @param key key
	 * @param exp 过期时间（秒）
	 * @param hash hash数据 
     * @return
     */
	public static void hsetstr(String key, int exp, Map<String, String> hash){
		DRJedisCache.hset(key, exp, hash);
	}
	
	/**
	 * 向 hash中添加数据
	 * @param key key
	 * @param exp 过期时间（秒）
	 * @param hash hash数据 
     * @return
     */
	public static void hset(String key, int exp, Map<String, Object> hash){	
		Map<String, String> hash1 = new HashMap<>();
		for(Map.Entry<String, Object> entry : hash.entrySet()){
			hash1.put(entry.getKey(), FastJsonUtil.objToJson(entry.getValue()));
		}
		hsetstr(key, exp, hash1);
	}

	/**
	 * 删除hash中的hashkey
	 * @param key
	 * @param hashkey
     * @return
     */
	public static void hdel(String key, String... hashkey){
		DRJedisCache.hdel(key, hashkey);
	}

	/**
	 * 获取hash中的值
	 * @param key key
	 * @param hashkey hashkey
     * @return
     */
	public static Map<String, String> hget(String key, String... hashkey){		
		return DRJedisCache.hget(key, hashkey);
	}
	/**
	 * 获取hash中所有的值
	 * @param key key
	 * @return
	 */
	public static Map<String, String> hget(String key){
		return DRJedisCache.hget(key);
	}
	
	/**
	 * 获取hash中的值
	 * @param key key
	 * @param hashkey hashkey
     * @return
     */
	public static Map<String, Object> hget(String key, Class<?> clazz, String... hashkey){		
		Map<String, Object> hash = new HashMap<>();
		Map<String, String> map = DRJedisCache.hget(key, hashkey);
		for(Map.Entry<String, String> entry : map.entrySet()){
			String str = entry.getValue();
			if(str == null || str.equals("")){
				hash.put(entry.getKey(), null);
				continue;
			}
			if(str.startsWith("[") && str.endsWith("]")) {
				hash.put(entry.getKey(), FastJsonUtil.jsonToArray(str, clazz));
				continue;
			}
			hash.put(entry.getKey(), FastJsonUtil.jsonToObj(entry.getValue(), clazz));
		}
		return hash;
	}
	/**
	 * 获取hash中所有的值
	 * @param key key
	 * @return
	 */
	public static Map<String, Object> hget(String key, Class<?> clazz){
		return hget(key, clazz);
	}

	/**
	 * 获取hash 长度
	 * @param key key
	 * @return
	 */
	public static long hlen(String key){	
		return DRJedisCache.hlen(key);
	}

	/**
	 * 向list右端推入元素
	 * @param key key
	 * @param value
	 * @return
	 */
	public static void rpush(String key, String... value){	
		rpush(key, 0, value);
	}
	
	/**
	 * 向list右端推入元素
	 * @param key key
	 * @param exp 过期时间
	 * @param value
	 * @return
	 */
	public static void rpush(String key, int exp, String... value){
		DRJedisCache.rpush(key, exp, value);
	}
	
	/**
	 * 向list右端推入元素
	 * @param key key
	 * @param value
	 * @return
	 */
	public static void rpush(String key, Object... value){	
		rpush(key, 0, value);
	}
	
	/**
	 * 向list右端推入元素
	 * @param key key
	 * @param exp 过期时间
	 * @param value
	 * @return
	 */
	public static void rpush(String key, int exp, Object... value){
		String[] str = new String[value.length];
		for(int i = 0; i < value.length; i++){
			str[i] = FastJsonUtil.objToJson(value[i]);
		}
		DRJedisCache.rpush(key, exp, str);
	}
	
	/**
	 * 获取list长度
	 * @param key key
	 * @return
	 */
	public static long llen(String key){	
		return DRJedisCache.llen(key);
	}

	/**
	 * 设置list中 索引为index的值为value
	 * @param key key
	 * @param index 索引
	 * @param value
	 * @return
	 */
	public static void lset(String key, int index, String value){
		lset(key, index, value, 0);
	}
	
	/**
	 * 设置list中 索引为index的值为value
	 * @param key key
	 * @param index 索引
	 * @param value
	 * @return
	 */
	public static void lset(String key, int index, Object value){
		lset(key, index, FastJsonUtil.objToJson(value));
	}
	
	/**
	 * 设置list中 索引为index的值为value
	 * @param key key
	 * @param index 索引
	 * @param value
	 * @return
	 */
	public static void lset(String key, int index, String value, int exp){
		DRJedisCache.lset(key, index, value, exp);
	}
	
	/**
	 * 设置list中 索引为index的值为value
	 * @param key key
	 * @param index 索引
	 * @param value
	 * @return
	 */
	public static void lset(String key, int index, Object value, int exp){
		lset(key, index, FastJsonUtil.objToJson(value), exp);
	}
	
	/**
	 * 获取 list中 索引为index的值
	 * @param key key
	 * @param index 索引
	 * @return
	 */
	public static String lindex(String key, int index){
		return DRJedisCache.lindex(key, index);
	}
	
	/**
	 * 获取 list中 索引为index的值
	 * @param key key
	 * @param index 索引
	 * @return
	 */
	public static Object lindex(String key, int index, Class<?> clazz){
		String str = lindex(key, index);
		if(str == null || str.equals("")) return null;
		if(str.startsWith("[") && str.endsWith("]")) return FastJsonUtil.jsonToArray(str, clazz);		
		return FastJsonUtil.jsonToObj(str, clazz);
	}
	
	/**
	 * 获取 list中 制定区间内元素
	 * @param key key
	 * @param start 
	 * @param stop
	 * @return
	 */
	public static List<String> lrange(String key, int start, int stop){
		return DRJedisCache.lrange(key, start, stop);
	}
	
	/**
	 * 获取 list中 制定区间内元素
	 * @param key key
	 * @param start 
	 * @param stop
	 * @return
	 */
	public static List<Object> lrange(String key, int start, int stop, Class<?> clazz){
		List<Object> result = new ArrayList<>();
		List<String> list = lrange(key, start, stop);
		for(String str : list){
			if(str == null || "".equals(str)) {
				result.add(null);
				continue;
			}
			if(str.startsWith("[") && str.endsWith("]")) {
				result.add(FastJsonUtil.jsonToArray(str, clazz));
				continue;
			}
			result.add(FastJsonUtil.jsonToObj(str, clazz));
		}
		return result;
	}
	
	/**
	 * 移除 list中 与value相等的元素
	 * @param key key
	 * @param count count=0,移除所有与value相等的元素 
	 * 		  count>0 从左侧开始，移除count个与value相等的元素
	 * 		  count<0 从右侧开始，移除count绝对值个与value相等的元素
	 * @param value
	 * @return
	 */
	public static void lrem(String key, int count, String value){
		DRJedisCache.lrem(key, count, value);
	}
	
	/**
	 * 向 set 中 添加 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static void sadd(String key, String... value){
		sadd(key, 0, value);
	}
	
	/**
	 * 向 set 中 添加 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static void sadd(String key, int exp, String... value){		
		DRJedisCache.sadd(key, value);
	}
	
	/**
	 * 向 set 中 添加 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static void sadd(String key, Object... value){
		sadd(key, 0, value);
	}
	
	/**
	 * 向 set 中 添加 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static void sadd(String key, int exp, Object... value){		
		String[] str = new String[value.length];
		for(int i = 0; i < value.length; i++){
			str[i] = FastJsonUtil.objToJson(value[i]);
		}
		DRJedisCache.sadd(key,exp, str);
	}
	
	
	/**
	 * 获取 set 集合元素
	 * @param key key
	 * @return
	 */
	public static Set<String> sget(String key){		
		return DRJedisCache.sget(key);
	}
	
	/**
	 * 获取 set 集合元素
	 * @param key key
	 * @param clazz
	 * @return
	 */
	public static Set<Object> sget(String key, Class<?> clazz){		
		Set<String> set = sget(key);
		Set<Object> objs = new HashSet<>();
		for(String str : set){
			if(str == null || "".equals(str)) {
				objs.add(null);
				continue;
			}
			if(str.startsWith("[") && str.endsWith("]")){
				objs.add(FastJsonUtil.jsonToArray(str, clazz));
				continue;
			}
			objs.add(FastJsonUtil.jsonToObj(str, clazz));
		}
		return objs;
	}
	
	/**
	 * 判断集合中存在value元素
	 * @param key key
	 * @return
	 */
	public static boolean sexists(String key, String value){		
		return DRJedisCache.sexists(key, value);
	}
	
	/**
	 * 判断集合中存在value元素
	 * @param key key
	 * @return
	 */
	public static boolean sexists(String key, Object value){		
		return sexists(key, FastJsonUtil.objToJson(value));
	}
	
	/**
	 * 获取 set 长度
	 * @param key key
	 * @return
	 */
	public static long slen(String key){
		return DRJedisCache.slen(key);
	}
	
	
	
	/**
	 * 从 set 中 移除 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static long srem(String key, String... value){
		return DRJedisCache.srem(key, value);
	}
	
	/**
	 * 从 set 中 移除 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static long srem(String key, Object... value){
		String[] str = new String[value.length];
		for(int i = 0; i < value.length; i++){
			str[i] = FastJsonUtil.objToJson(value[i]);
		}
		return srem(key, str);
	}
	
}
