/**
 * 
 */
/**
 * @author lenovo
 *
 */
package com.cnpc.dj.party.runner;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.cnpc.dj.party.entity.ManageMent;
import com.cnpc.dj.party.entity.ResultRedis;
import com.cnpc.dj.party.service.ManageMentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public  class StartupRunner implements CommandLineRunner{

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	ManageMentService manageMentService;
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("------StartupRunner is  start--------");
		ManageMent manageMent = new ManageMent();
		List<ManageMent> allMent = manageMentService.findList(manageMent);
		for(ManageMent ment : allMent) {
			ResultRedis ret = new ResultRedis();
			
			
			ret.setRemoteUrl(ment.getCovertUrl());
			ret.setParamType(ment.getParamType());
			ret.setState(ment.getStatus()==0 ? true : false);
			ObjectMapper mapper = new ObjectMapper();    
		     //  StringWriter w = new StringWriter();  
		       String json = mapper.writeValueAsString(ret); 
		       
		       
			redisTemplate.opsForValue().set(ment.getFromUrl(), json);
		}
		System.out.println("------StartupRunner is  end--------");
	}
	
}