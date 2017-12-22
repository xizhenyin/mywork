package com.cnpc.dj.party.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnpc.dj.cache.Cache;
import com.cnpc.dj.cache.CacheManager;
import com.cnpc.dj.party.common.SpringContextHolder;
import com.cnpc.dj.party.entity.ManageMent;
import com.cnpc.dj.party.entity.Result;
import com.cnpc.dj.party.entity.ResultRedis;
import com.cnpc.dj.party.entity.ThirdCompany;
import com.cnpc.dj.party.mapper.ManageMentMapper;
import com.cnpc.dj.party.mapper.ThirdCompanyMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ThirdCompanyService {
	private static ThirdCompanyMapper thirdCompanyDao = SpringContextHolder.getBean(ThirdCompanyMapper.class);

	private static ManageMentMapper manageMentDao = SpringContextHolder.getBean(ManageMentMapper.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public int deleteByPrimaryKey(String id) {
		return thirdCompanyDao.deleteByPrimaryKey(id);
	}

	public int insert(ThirdCompany record) {
		record.setCreateBy("1");
		record.setCreateDate(new Date());
		return thirdCompanyDao.insert(record);
	}

	public int insertSelective(ThirdCompany record) {
		return thirdCompanyDao.insertSelective(record);
	}

	public ThirdCompany selectByPrimaryKey(String id) {
		return thirdCompanyDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKe3ySelective(ThirdCompany record) {
		return thirdCompanyDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(ThirdCompany record) {
		record.setUpdateBy("1");
		record.setUpdateDate(new Date());
		return thirdCompanyDao.updateByPrimaryKey(record);
	}

	public List<ThirdCompany> findList(ThirdCompany record) {
		return thirdCompanyDao.findList(record);
	}

	public ThirdCompany get(String id) {
		ThirdCompany thirdCompany = thirdCompanyDao.selectByPrimaryKey(id);
		return thirdCompany;
	}

	@Transactional
	public Result updateManageStatusByCompany(ThirdCompany thirdCompany) throws JsonProcessingException {
		
		ThirdCompany company = get(thirdCompany.getId());
		company.setDelFlag(thirdCompany.getDelFlag());
		thirdCompanyDao.updateByPrimaryKey(company);
		
		
		ManageMent manageMent = new ManageMent();
		manageMent.setCompanyId(thirdCompany.getId());
	
		Result result = new Result();
		// List<>
		List<ManageMent> ments = manageMentDao.findList(manageMent);
		for (ManageMent ment : ments) {
			ment.setStatus(Short.parseShort(thirdCompany.getDelFlag()));
		}

		if(ments.size()>0) {
			int count = manageMentDao.batchUpdate(ments);

			ObjectMapper mapper = new ObjectMapper();
			String json = "";
		
			if (count > 0) {
				for (ManageMent ment : ments) {
					ResultRedis ret = new ResultRedis(ment.getCovertUrl(), ment.getStatus() == 0 ? true : false,
							ment.getParamType());
					json = mapper.writeValueAsString(ret);

					Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
					
					//stringRedisTemplate.opsForValue().set(ment.getFromUrl(), json);
					cache.put(ment.getFromUrl(), json);
				
					//redisTemplate.opsForValue().set(ment.getId(), ment);
				}

				result.setCode("0");
			} else {
				result.setCode("1");
			}
		}


		return result;
	}

}
