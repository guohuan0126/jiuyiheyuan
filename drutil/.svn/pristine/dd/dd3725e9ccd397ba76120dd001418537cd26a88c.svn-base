package com.duanrong.util.jedis;

import com.duanrong.util.PropReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis 客户端工具类
 * @author xiao
 * @date 2016/05/23
 * @version 1.0
 * @description 提供jedis
 */
public final class DRJedis {

    private static Log log = LogFactory.getLog(DRJedis.class);

    //jedis pool
    private static JedisPool pool;

    private static PropReader pd;

    static String prefix = "";
    
    //jedis pool静态化参数
    static {
        try{
        	pd = new PropReader("/redis.properties");
        	log.info("加载配置文件成功");
        }catch(NullPointerException e){
        	log.error("redis.properties 不存在, 无法加载redis配置", e);
        }           
        prefix = pd.readProperty("prefix");
        if(prefix != null && !prefix.trim().equals("") && !prefix.trim().equals("null")) prefix += "_"; else prefix = "";
        JedisPoolConfig config = new JedisPoolConfig();
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(Boolean.parseBoolean(pd.readProperty("blockWhenExhausted")));
        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        //config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        //是否启用pool的jmx(监控)管理功能, 默认true
        /*config.setJmxEnabled(true);
        //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
        config.setJmxNamePrefix("pool");*/
        //是否启用后进先出, 默认true
        //config.setLifo(true);
        //最大空闲连接数, 默认10个
        config.setMaxIdle(Integer.parseInt(pd.readProperty("maxIdle")));
        //最大连接数, 默认1000个
        config.setMaxTotal(Integer.parseInt(pd.readProperty("maxTotal")));
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认60秒
        config.setMaxWaitMillis(Integer.parseInt(pd.readProperty("maxWaitMillis")));
        //逐出连接的最小空闲时间（空闲时间大于此时间才会被逐出扫描）, 默认1800000毫秒(30分钟) 交由softMinEvictableIdleTimeMillis控制
        //config.setMinEvictableIdleTimeMillis(1800000);
        //最小空闲连接数, 默认4
        config.setMinIdle(Integer.parseInt(pd.readProperty("minIdle")));
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认10
        config.setNumTestsPerEvictionRun(Integer.parseInt(pd.readProperty("numTestsPerEvictionRun")));
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(Integer.parseInt(pd.readProperty("softMinEvictableIdleTimeMillis")));
        //在获取连接的时候检查有效性（保证每次获取的对象均是有效的）, 默认false
        config.setTestOnBorrow(Boolean.parseBoolean(pd.readProperty("testOnBorrow")));
        config.setTestOnReturn(true);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(Boolean.parseBoolean(pd.readProperty("testWhileIdle")));
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认3分钟检查一次,
        //对象空闲时间超过softMinEvictableIdleTimeMillis则被逐出，
        //每次逐出numTestsPerEvictionRun个对象, -1则不检查
        config.setTimeBetweenEvictionRunsMillis(Integer.parseInt(pd.readProperty("timeBetweenEvictionRunsMillis")));
        //创建pool
        String password = pd.readProperty("password");
        if(password != null && password.trim().length() > 0){
            try{
                pool = new JedisPool(config, pd.readProperty("host"), Integer.parseInt(pd.readProperty("port")), Integer.parseInt(pd.readProperty("timeout")), password);
            }catch(Exception e){
                log.error("创建jedispool失败", e);
            }
        }else{
            try{
                pool = new JedisPool(config, pd.readProperty("host"), Integer.parseInt(pd.readProperty("port")), Integer.parseInt(pd.readProperty("timeout")));
            }catch(Exception e){
                log.error("创建jedispool失败", e);
            }
        }
        log.info("创建jedispool成功");
        //redis 集群客户端
        //new JedisSentinelPool(master, sentinels, poolConfig,timeout);//timeout 读取超时
    }

    @Deprecated
    public static Jedis getJedis(){
        Jedis jedis = null;
        try {
            jedis = new Jedis(pd.readProperty("host"), Integer.parseInt(pd.readProperty("port")),
                    Integer.parseInt(pd.readProperty("timeout")));
            String password = pd.readProperty("password");
            if(password != null && password.trim().length() > 0){
                jedis.auth(password);
            }
        }catch (Exception e){
            log.error("创建jedis失败", e);
        }
        return jedis;
    }

    /**
     * 从pool中获取jedis对象
     * @return
     */
    public static Jedis getJedisPool(){
        int total = 1;

        //redis 链接失败尝试重新获取链接，尝试3次，3次失败则不再连接
        do {
            try{
                Jedis jedis =  pool.getResource();
                total = 1;
                return jedis;
            }catch(JedisConnectionException e){
                log.error("redis 链接失败已超过"+total+"次，请检查服务器...", e);
                total ++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    log.error(e);
                }
            }
        }while (total < 3);
        log.error("redis 链接失败已超过3次，redis已失效");
        return null;
    }

    /**
     * jedis 回池，jedis用完一定要回池，不然会导致redis链接会消耗完
     * @param jedis
     */
    public static void returnResource(Jedis jedis){
        if(jedis != null) jedis.close();
    }

}