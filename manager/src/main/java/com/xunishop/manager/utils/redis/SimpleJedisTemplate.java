package com.xunishop.manager.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;

/**
 * @Description redis基于jedis模板 
 * @author wanggang-ds6
 * @date 2015年11月11日 上午10:48:14
 */
public class SimpleJedisTemplate {
	
	// redis连接池
	private JedisPool jedisPool;
	
	// redis集群
	private JedisCluster jedisCluster;
	
	// 是否为集群，默认不是集群
	private boolean isCluster = false;
	
	/**
	 * @Description 构建连接池方式模板
	 * @param jedisPool redis连接池
	 * @author wanggang-ds6
	 * @date 2015年11月11日 上午10:48:14
	 */
	public SimpleJedisTemplate(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/**
	 * @Description 构建集群方式模板
	 * @param jedisCluster redis集群实例
	 * @author wanggang-ds6
	 * @date 2015年11月11日 上午10:48:14
	 */
	public SimpleJedisTemplate(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
		isCluster = true;
	}
	
	/**
	 * @Description 获取jedis实例 
	 * @author wanggang-ds6
	 * @date 2015年11月11日 上午11:22:35
	 * @return
	 */
	private JedisCommands getJedisCommands() {
		if (isCluster) {
			return jedisCluster;
		} else {
			return jedisPool.getResource();
		}
	}
		
	/**
	 * @Description 执行命令 
	 * @author wanggang-ds6
	 * @date 2015年11月11日 上午11:33:51
	 * @param action 
	 * @return
	 */
	public <T> T execute(RedisCallback<T> action) {
		boolean broken = false;
		JedisCommands jedisCommands = null;
		try {
			jedisCommands = getJedisCommands();
			return action.doInRedis(jedisCommands);
		} catch (Exception e) {
			broken = true;
			throw e;
		} finally {
			if (!isCluster) {
				closeResource((Jedis) jedisCommands, broken);
			}
		}
		
	}
	
	/**
	 * @Description 释放redis 
	 * @author wanggang-ds6
	 * @date 2015年11月11日 下午12:00:46
	 * @param jedis redis实例
	 * @param connectionBroken
	 */
	private void closeResource(Jedis jedis, boolean connectionBroken) {
		if (jedis != null) {
			if (connectionBroken) {
				this.jedisPool.returnBrokenResource(jedis);
			} else {
				this.jedisPool.returnResource(jedis);
			}
		}	
	}

	/**
	 * @Description 回调函数 
	 * @author wanggang-ds6
	 * @date 2015年11月11日 下午12:01:12
	 * @param <T>
	 */
	public static abstract interface RedisCallback<T> {
		T doInRedis(JedisCommands commands);
	}
}
