package com.duanrong.util.jedis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;



/**
 * redis 分布式锁
 * @author xiao
 * @date 2016/05/25
 * @version 1.0
 * @description 实现基于redis的分布式锁
 */
public final class DRJedisDLock {

    private static Log log = LogFactory.getLog(DRJedisDLock.class);

    //分布式锁前缀，标志此数据为锁
    private static final String LOCK_PREFIX = DRJedis.prefix + "LOCK_";

    //锁持有时间, 超过此时间强制释放锁，防止死锁导致业务瘫痪
    private static final long LOCK_TIME = 30;

    /**
     * 获取分布式锁 DLock, 如果锁被持有，则一直等待，直到取得锁
     * @param key 锁标志
     * @param holder 锁持有者（一般为uuid，唯一标识当前锁的持有者）
     * @return
     */
    public static boolean getDLock(String key, String holder){
        while (true){
            Jedis jedis = DRJedis.getJedisPool();
            try {
                if (jedis.setnx(LOCK_PREFIX + key, holder) == 1) {
                    //设置锁强制过期时间
                    jedis.expire(LOCK_PREFIX + key, 30);
                    return true;
                }
                Thread.sleep(100);
            } catch (Exception e) {
                log.error("获取 DLock 异常", e);
            } finally {
                DRJedis.returnResource(jedis);
            }
        }
    }
    /**
     * 获取分布式锁 DLock
     * @param key 锁标志
     * @param holder 锁持有者（一般为uuid，唯一标识当前锁的持有者）
     * @param timeout 获取锁的超时时间 毫秒
     * @return
     */
    public static boolean getDLock(String key, String holder, long timeout){
        Jedis jedis = DRJedis.getJedisPool();
        //设置获取锁的超时时间
        long end = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < end){
            try {
                if (jedis.setnx(LOCK_PREFIX + key, holder) == 1) {
                    //设置锁强制过期时间
                    jedis.expire(LOCK_PREFIX + key, 30);
                    return true;
                }
                Thread.sleep(100);
            } catch (Exception e) {
                log.error("获取 DLock 异常", e);
            } finally {
                DRJedis.returnResource(jedis);
            }
        }
        DRJedis.returnResource(jedis);
        log.error("获取 DLock 超时");
        return false;
    }

    /**
     * 释放锁
     * @param key 锁标志
     * @param holder 锁持有者（一般为uuid，唯一标识当前锁的持有者）
     */
    public static void releaseDLock(String key, String holder){
        while (true){
            Jedis jedis = DRJedis.getJedisPool();
            try {
                jedis.watch(LOCK_PREFIX + key);
                String value = jedis.get(LOCK_PREFIX + key);
                if(value != null && value.equals(holder)){// 保证释放的是当前持有者的锁
                    //Transaction tran = jedis.multi();
                    jedis.del(LOCK_PREFIX + key);
                    //tran.exec();
                }
                jedis.unwatch();
                break;
            }catch (Exception e){
                log.error("释放锁异常", e);
                break;
            }finally {
                DRJedis.returnResource(jedis);
            }
        }
    }
}