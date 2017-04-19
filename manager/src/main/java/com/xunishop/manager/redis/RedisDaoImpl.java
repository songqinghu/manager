package com.xunishop.manager.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xunishop.manager.utils.redis.SimpleJedisTemplate;
import com.xunishop.manager.utils.redis.SimpleJedisTemplate.RedisCallback;

import redis.clients.jedis.JedisCommands;


@Repository
public class RedisDaoImpl implements RedisDao {

	@Autowired
	SimpleJedisTemplate jedisTemplate;
	
	@Override
	public String getValueByKey(final String key) {
		return jedisTemplate.execute(new SimpleJedisTemplate.RedisCallback<String>() {
			@Override
			public String doInRedis(JedisCommands commands) {
				return commands.get(key);
			}
		});
	}
	/**
	 * 获取boolean值并且删除这个值 如果获取不到返回false;
	 * <p>Title: getValueByKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 * @see com.kuaikanwang.image.redis.RedisDao#getValueByKey(java.lang.String)
	 */
	@Override
	public boolean getValueByKeyAndUpdate(final String key) {
		return jedisTemplate.execute(new SimpleJedisTemplate.RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(JedisCommands commands) {
				
				String flag = commands.get(key);
				if(flag==null|| flag.length()<1){
					return false;
				}else{
					commands.del(key);
					return true;
				}
			}
		});
	}
	
	@Override
	public Long getValueByKeyNum(final String key) {
		return jedisTemplate.execute(new SimpleJedisTemplate.RedisCallback<Long>() {
			@Override
			public Long doInRedis(JedisCommands commands) {
				
				String string = commands.get(key);
				
				if(string ==null){
					return 0l;
				}
				
				return Long.valueOf(string);
			}
		});
	}
	
	/**
	 * 指定的key加1
	 * <p>Title: incrValueByKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 */
	public Boolean incrValueByKey(final String key,final Integer seconds,final Integer max){
		
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {
				//一级监控  控制爬虫最多 1分钟访问10次
				if(!commands.exists(key)){ //不存在该key
					commands.incr(key);
					commands.expire(key,seconds);
				}else{
					Long num = commands.incr(key);
					if(num >max){
						commands.del(key);
						return  false;
					}
					
				}
				return true;
			}
		});
		
		
	}
	
	public Boolean decrValueByKey(final String key){
		
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {
				if(!commands.exists(key)){
					return true;
				}
				//存在  说明是 加法时存入 已经做了时间控制
				Long num = commands.decr(key);
				if(num<0){
					commands.del(key);
				}
				return true;
			}
		});
		
		
	}

	@Override
	public Boolean incrValueByKey(final String key) {
		
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {

				commands.incr(key);
	
				return true;
			}
		});
	}

	@Override
	public Boolean setValueByKey(final String key,final String value) {
		
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {

				commands.set(key, value);
	
				return true;
			}
		});
	}

	/**
	 * 将指定值放入list中的头部 不存在该list则创建
	 * <p>Title: lpushValue</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lpushValue(final String key,final String value){
		
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {
				
				commands.lpush(key, value);//放入list中
				
				return true;
			}
			
		});
	}
	/**
	 * 向set中添加指定元素
	 * <p>Title: saddValue</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean saddValue(final String key,final String value){
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {

				commands.sadd(key, value);
				
				return true;
			}
			
		});
	}
	/**
	 * 判断value是否在指定的set中
	 * <p>Title: sismemberValue</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return
	 * @see com.kuaikanwang.image.redis.RedisDao#sismemberValue(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sismemberValue(final String key,final String value) {
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {

				Boolean flag = commands.sismember(key, value);
				
				return flag;
			}
			
		});
	}
	/**
	 * 弹出list中最后一个元素
	 * <p>Title: brpopByKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 * @see com.kuaikanwang.image.redis.RedisDao#brpopByKey(java.lang.String)
	 */
	@Override
	public String rpopByKey(final String key) {
		return jedisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(JedisCommands commands) {

				String value = commands.rpop(key);//没有返回null
				
				return value;
			}
			
		});
	}
	
	/**
	 * 删除指定的可以从set中
	 * <p>Title: sremValueByKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return
	 * @see com.kuaikanwang.image.redis.RedisDao#sremValueByKey(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sremValueByKey(final String key,final String value) {
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {
				
				commands.srem(key, value);
				
				return true;
			}
			
		});
	}
	/**
	 * 删除指定的key
	 * <p>Title: delByKey</p>
	 * <p>Description: </p>
	 * @param key
	 * @return
	 * @see com.kuaikanwang.image.redis.RedisDao#delByKey(java.lang.String)
	 */
	@Override
	public boolean delByKey(final String key) {
		return jedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(JedisCommands commands) {
				
				commands.del(key);
				
				return true;
			}
			
		});
	}
	
	
	
}
