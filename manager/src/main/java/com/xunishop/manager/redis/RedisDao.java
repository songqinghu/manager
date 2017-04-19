package com.xunishop.manager.redis;


public interface RedisDao {
	public String getValueByKey(String key);
	
	public Long getValueByKeyNum(String key);
	
	public Boolean incrValueByKey(String key,Integer seconds,Integer max);
	
	public Boolean decrValueByKey(String key);
	
	public Boolean incrValueByKey(String key);
	
	public Boolean setValueByKey(String key,String value);
	
	public boolean getValueByKeyAndUpdate( String key);
	
	public boolean lpushValue( String key, String value);
	
	public boolean saddValue(String key,String value);
	
	public boolean sismemberValue(String key,String value);
	
	
	public String  rpopByKey(String key);
	
	
	public boolean sremValueByKey(String key,String value);
	
	
	public boolean delByKey(String key);
	
	
	
}
