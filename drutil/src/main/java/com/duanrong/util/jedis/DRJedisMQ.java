package com.duanrong.util.jedis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;

/**
 * redisMQ 操作工具类
 * @author xiao
 * @date 2016/05/23
 * @version 1.0
 * @description 定义基于发布/订阅，生产/消费模式的消息队列
 */
public final class DRJedisMQ {

    private static Log log = LogFactory.getLog(DRJedisMQ.class);

    //分布式锁前缀，标志此数据为锁
    private static final String MQ_PREFIX = DRJedis.prefix + "MQ_";
    /**
     * 发布消息
     * @param key 队列key
     * @param message
     */
    public static void publish(String key, String message){
        Jedis jedis = DRJedis.getJedisPool();
        try{
            jedis.publish(MQ_PREFIX + key, message);
        }catch(Exception e){
            log.error("jedis publish message", e);
        }finally {
            DRJedis.returnResource(jedis);
        }

    }
    /**
     * 订阅消息
     * @param pubSub
     * @param key 频道key
     */
    public static void subscribe(String key, JedisPubSub pubSub){
        Jedis jedis = DRJedis.getJedisPool();
        try{
            jedis.subscribe(pubSub, MQ_PREFIX + key);
        }catch(Exception e){
            log.error("jedis subscribe message", e);
        }finally {
            DRJedis.returnResource(jedis);
        }
    }


    /**
     * 生产消息
     * @param key 消息key
     * @param message 消息体，支持批量生产消息，至少生产一条消息
     */

    public static void product(String key, String... message){
        Jedis jedis = DRJedis.getJedisPool();
        try{
            jedis.rpush(MQ_PREFIX + key, message);
        }catch(Exception e){
            log.error("jedis product message", e);
        }finally {
            DRJedis.returnResource(jedis);
        }
    }


    /**
     * 消费消息
     * @param key 消息key
     * @param customer 消息消费业务，需要实现Customer接口的customer方法进行业务处理
     */
    public static void customer(final String key, final ICustomer customer){
        /**
         * 创建监听线程，防止阻塞命令引起主线程阻塞
         */
        new Thread(new Runnable(){
            @Override
            public void run() {
                while (true){
                    Jedis jedis = DRJedis.getJedisPool();
                    try{
                        //弹出消息
                        List<String> messages = jedis.blpop(0, MQ_PREFIX + key);
                        if (messages.size() >= 2){
                            try {
                                //消费消息
                                customer.customer(messages.get(0), messages.get(1));
                            }catch (RollbackException e){
                                //消息消费失败则要重新生产消息，并有右侧压入队列，重新排队
                                jedis.rpush(messages.get(0), messages.get(1));
                            }
                        }
                    }catch(Exception e){
                        log.error("jedis customer message", e);
                    }finally {
                        DRJedis.returnResource(jedis);
                    }
                }
            }
        }).start();
    }
}