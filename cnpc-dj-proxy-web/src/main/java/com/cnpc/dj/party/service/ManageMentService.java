package com.cnpc.dj.party.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cnpc.dj.cache.Cache;
import com.cnpc.dj.cache.CacheManager;
import com.cnpc.dj.party.common.SpringContextHolder;
import com.cnpc.dj.party.entity.ManageMent;
import com.cnpc.dj.party.mapper.ManageMentMapper;


@Service
public class ManageMentService {
	private static ManageMentMapper manageMentDao = SpringContextHolder.getBean(ManageMentMapper.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	CacheManager cacheManager;
	
	public int deleteByPrimaryKey(String id) {
		return manageMentDao.deleteByPrimaryKey(id);
	}

	public  int insert(ManageMent record) {
		record.setCreateBy("1");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		java.util.Date date=new java.util.Date();  
		String str=sdf.format(date); 
		record.setCreateDate(str);
    	return manageMentDao.insert(record);
    }

	public int insertSelective(ManageMent record) {
    	return manageMentDao.insertSelective(record);
    }

	public ManageMent selectByPrimaryKey(String id) {
    	return manageMentDao.selectByPrimaryKey(id);
    }

	public int updateByPrimaryKe3ySelective(ManageMent record) {
    	return manageMentDao.updateByPrimaryKeySelective(record);
    }

	public int updateByPrimaryKey(ManageMent record) {
		record.setUpdateBy("1");
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		java.util.Date date=new java.util.Date();  
		String str=sdf.format(date); 
		record.setUpdateDate(str);
    	return manageMentDao.updateByPrimaryKey(record);
    }
    
   public List<ManageMent> findList(ManageMent record){
    	return manageMentDao.findList(record);
    }
   
	public ManageMent getFromRedis(String id) {
//		Cache<String,ManageMent> cache = cacheManager.getCache("THIRDPROXY");
//		//ManageMent manageMent = (ManageMent)redisTemplate.opsForValue().get(id);
//		ManageMent manageMent = (ManageMent)cache.get(id);
//		if (null == manageMent) {
		ManageMent	manageMent = manageMentDao.selectByPrimaryKey(id);
			//redisTemplate.opsForValue().set(id, manageMent);
//			cache.put(id, manageMent);
//		}
		return manageMent;
	}
}
