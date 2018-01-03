package com.cnpc.dj.party.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.cnpc.dj.cache.Cache;
import com.cnpc.dj.cache.CacheManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RemoteAPIFactory {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	CacheManager cacheManager;
	
	public RemoteAPIState get(String key) {
		//String v = redisTemplate.opsForValue().get(key);
		
		Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
		String v = (String)cache.get(key);
		
		RemoteAPIState ps = null;
		try {
			ps = objectMapper.readValue(v, RemoteAPIState.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (null == ps || !ps.isState()) return null;
		return ps;
	}
	
	public void put(String key, RemoteAPIState remoteAPIState) {
		try {
			String v = objectMapper.writeValueAsString(remoteAPIState);
			//redisTemplate.opsForValue().set(key, v);
			Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
			cache.put(key, v);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void remove(String key) {
		RemoteAPIState ps = get(key);
		if (null != ps) {
			ps.setState(false);
			put(key, ps);
		}
		
	}
	
	public void recover(String key) {
		RemoteAPIState ps = get(key);
		if (null != ps) {
			ps.setState(true);
			put(key, ps);
		}
	}
	

}
