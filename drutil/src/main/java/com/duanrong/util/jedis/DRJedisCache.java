package com.duanrong.util.jedis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 存储工具类，提供redis基本类型的存储
 * @author xiao
 * @date 2016/08/19
 * @version 1.0
 * @description 提供jedis
 */
public final class DRJedisCache {

	private static Log log = LogFactory.getLog(DRJedisCache.class);

	//缓存前缀
	private static final String CACHE_PREFIX = DRJedis.prefix + "CACHE_";
	/**
	 * 根据正则查询所有符合规则的key
	 * @param patten
	 * @return
	 */
	public static Set<String> keys(String patten){
		Jedis jedis = DRJedis.getJedisPool();
		log.debug("keys --- key: " + CACHE_PREFIX + patten);
		try{
			return jedis.keys(CACHE_PREFIX + patten);
		}catch(Exception e){
			log.error("jedis keys patten ["+patten+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
     */
	public static boolean exists(String key){
		log.debug("exists --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.exists(CACHE_PREFIX + key);
		}catch(Exception e){
			log.error("jedis exists key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return false;
	}


	/**
	 * 设置key的过期时间
	 * @param key
	 * @param exp 过期时间 时间为0，直接过期
	 * @return
	 */
	public static long expire(String key, int exp){
		log.debug("expire --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			if(exp >= 0) return jedis.expire(CACHE_PREFIX + key, exp);
		}catch(Exception e){
			log.error("jedis expire key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}


	/**
	 * 批量删除指定的key
	 * @param key
	 * @return
	 */
	public static long del(String key){
		log.debug("del --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.del(CACHE_PREFIX + key);
		}catch(Exception e){
			log.error("jedis del key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}

	/**
	 * 将String 类型 key设置为value
	 * @param key
	 * @param value
     * @return
     */
	public static String set(String key, String value){
		log.debug("set --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.set(CACHE_PREFIX + key, value);
		}catch(Exception e){
			log.error("jedis set key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}

	/**
	 * 将String类型 key设置为value
	 * @param key
	 * @param value
	 * @param exp 缓存时间（秒）
     * @return
     */
	public static String set(String key, String value, int exp){
		log.debug("set --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			if (exp > 0){
				return jedis.setex(CACHE_PREFIX + key, exp, value);
			}else {
				return jedis.set(CACHE_PREFIX + key, value);
			}
		}catch(Exception e){
			log.error("jedis set key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}

	/**
	 * 读取String的值key
	 * @param key
	 * @return
	 */
	public static String get(String key){
		log.debug("get --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.get(CACHE_PREFIX + key);
		}catch(Exception e){
			log.error("jedis get key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}

	/**
	 * 向 hash 中添加数据
	 * @param key key
	 * @param hash hash数据 例：hashkey1， hashvalue1， hashkey2，hashvalue2 .。。
	 * @return
	 */
	public static String hset(String key,   Map<String, String> hash){
		log.debug("hset --- key: " + key);
		return hset(key, 0, hash);
	}

	/**
	 * 向 hash中添加数据
	 * @param key key
	 * @param exp 过期时间（秒）
	 * @param hash hash数据 例：hashkey1， hashvalue1， hashkey2， hashvalue2 .。。
     * @return
     */
	public static String hset(String key,  int exp, Map<String, String> hash){
		log.debug("hset --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();	
		try{
		
			String result = jedis.hmset(CACHE_PREFIX + key, hash);
			if (exp > 0){
				jedis.expire(CACHE_PREFIX + key, exp);
			}
			return result;
		}catch(Exception e){
			log.error("jedis hset key ["+key+"]", e);
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}

	/**
	 * 删除hash中的hashkey
	 * @param key
	 * @param hashkey
     * @return
     */
	public static long hdel(String key, String... hashkey){
		log.debug("hdel --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.hdel(CACHE_PREFIX + key, hashkey);
		}catch (Exception e){
			log.debug("jedis hdel key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}

	/**
	 * 获取hash中的值
	 * @param key key
	 * @param hashkey hashkey
     * @return
     */
	public static Map<String, String> hget(String key, String... hashkey){
		log.debug("hget --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		Map<String, String> map = new HashMap<>();
		try{
			List<String> result = jedis.hmget(CACHE_PREFIX + key, hashkey);
			for (int i = 0; i < hashkey.length; i++){
				map.put(hashkey[i], result.get(i));
			}
		}catch (Exception e){
			log.debug("jedis hdel key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return map;
	}
	/**
	 * 获取hash中所有的值
	 * @param key key
	 * @return
	 */
	public static Map<String, String> hget(String key){
		log.debug("hget --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.hgetAll(CACHE_PREFIX + key);
		}catch (Exception e){
			log.debug("jedis hdel key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 获取hash 长度
	 * @param key key
	 * @return
	 */
	public static long hlen(String key){
		log.debug("hlen --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.hlen(CACHE_PREFIX + key);
		}catch (Exception e){
			log.debug("jedis hdel key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}

	/**
	 * 向list右端推入元素
	 * @param key key
	 * @param value
	 * @return
	 */
	public static long rpush(String key, String... value){
		log.debug("rpush --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.rpush(CACHE_PREFIX + key, value);
		}catch (Exception e){
			log.debug("jedis rpush key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}
	
	/**
	 * 向list右端推入元素
	 * @param key key
	 * @param exp 过期时间
	 * @param value
	 * @return
	 */
	public static long rpush(String key, int exp, String... value){
		log.debug("rpush --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			long status = jedis.rpush(CACHE_PREFIX + key, value);
			if(exp > 0){
				jedis.expire(CACHE_PREFIX + key, exp);
			}
			return status;
		}catch (Exception e){
			log.debug("jedis rpush key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}


	/**
	 * 获取list长度
	 * @param key key
	 * @return
	 */
	public static long llen(String key){
		log.debug("llen --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.llen(CACHE_PREFIX + key);
		}catch (Exception e){
			log.debug("jedis llen key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}

	/**
	 * 设置list中 索引为index的值为value
	 * @param key key
	 * @param index 索引
	 * @param value
	 * @return
	 */
	public static String lset(String key, int index, String value){
		log.debug("lset --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.lset(CACHE_PREFIX + key, index, value);
		}catch (Exception e){
			log.debug("jedis lset key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}
	
	/**
	 * 设置list中 索引为index的值为value
	 * @param key key
	 * @param index 索引
	 * @param value
	 * @return
	 */
	public static String lset(String key, int index, String value, int exp){
		log.debug("lset --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			String status = jedis.lset(CACHE_PREFIX + key, index, value);
			if(exp > 0){
				jedis.expire(CACHE_PREFIX + key, exp);
			}
			return status;
		}catch (Exception e){
			log.debug("jedis lset key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}
	
	/**
	 * 获取 list中 索引为index的值
	 * @param key key
	 * @param index 索引
	 * @return
	 */
	public static String lindex(String key, int index){
		log.debug("lindex --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.lindex(CACHE_PREFIX + key, index);
		}catch (Exception e){
			log.debug("jedis lindex key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return "";
	}
	
	/**
	 * 获取 list中 制定区间内元素
	 * @param key key
	 * @param start 
	 * @param stop
	 * @return
	 */
	public static List<String> lrange(String key, int start, int stop){
		log.debug("lrange --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.lrange(CACHE_PREFIX + key, start, stop);
		}catch (Exception e){
			log.debug("jedis lrange key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return null;
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
	public static long lrem(String key, int count, String value){
		log.debug("lrem --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.lrem(CACHE_PREFIX + key, count, value);
		}catch (Exception e){
			log.debug("jedis lrange key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}
	
	/**
	 * 向 set 中 添加 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static long sadd(String key, String... value){
		log.debug("sadd --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.sadd(CACHE_PREFIX + key, value);
		}catch (Exception e){
			log.debug("jedis sadd key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}
	
	/**
	 * 向 set 中 添加 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static long sadd(String key, int exp, String... value){
		log.debug("sadd --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			long status = jedis.sadd(CACHE_PREFIX + key, value);
			if(exp > 0){
				jedis.expire(CACHE_PREFIX + key, exp);
			}
			return status;
		}catch (Exception e){
			log.debug("jedis sadd key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}

	/**
	 * 获取 set 集合
	 * @param key key
	 * @return
	 */
	public static Set<String> sget(String key){
		log.debug("sget --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.sdiff(CACHE_PREFIX + key);
		}catch (Exception e){
			log.debug("jedis srem key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 判断 value 是否存在集合中
	 * @param key key
	 * @return
	 */
	public static boolean sexists(String key, String value){
		log.debug("sexists --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.sismember(CACHE_PREFIX + key, value);
		}catch (Exception e){
			log.debug("jedis srem key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return false;
	}

	/**
	 * 获取 set 长度
	 * @param key key
	 * @return
	 */
	public static long slen(String key){
		log.debug("slen --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.scard(CACHE_PREFIX + key);
		}catch (Exception e){
			log.debug("jedis srem key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}
	
	/**
	 * 从 set 中 移除 value
	 * @param key key
	 * @param value
	 * @return
	 */
	public static long srem(String key, String... value){
		log.debug("srem --- key: " + CACHE_PREFIX + key);
		Jedis jedis = DRJedis.getJedisPool();
		try{
			return jedis.srem(CACHE_PREFIX + key, value);
		}catch (Exception e){
			log.debug("jedis srem key ["+key+"]", e );
		}finally {
			DRJedis.returnResource(jedis);
		}
		return 0;
	}



}