package com.cnpc.dj.party.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cnpc.dj.party.entity.Result;
import com.cnpc.dj.party.exception.ThridPartyException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HttpProcesser implements Processer{
	@Autowired
	private RemoteAPIFactory remoteAPIFactory;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostConstruct
	public void init() {
		//remoteAPIFactory.put("meeting", "http://11.8.45.152:8080/vdjcloud/api/vdjySaveEvent");
	}
	
	@Override
	public Result execute(String urlKey, String url, String paramType, Map<String, Object> params) {
		Result result = new Result();
		try {
			
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			
			if (("1").equals(paramType)) {  //get
				MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
				params.keySet().stream().forEach(key -> requestEntity.add(key, MapUtils.getString(params, key, "")));
        
				String ret = restTemplate.postForObject(url,headers, String.class, requestEntity);
				result.setData(ret);
			} else {  //post
				String v = objectMapper.writeValueAsString(params);
		          
				HttpEntity<String> formEntity = new HttpEntity<String>(v, headers);
				String ret = restTemplate.postForObject(url, formEntity, String.class);
				result.setData(ret);
			}
		} catch (Exception e) {
			remoteAPIFactory.recover(urlKey);
			throw new ThridPartyException(e.getMessage());
		}
		return result;
	}

/*	@Override
	public Result execute(String urlKey, String url, String paramType, String params) {
		Result result = new Result();
		try {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			HttpEntity<String> entity = new HttpEntity<String>(params, headers);
        
			String ret = restTemplate.postForObject(url,entity, String.class);
			result.setData(ret);
		} catch (Exception e) {
			remoteAPIFactory.recover(urlKey);
			throw new ThridPartyException(e.getMessage());
		}
		return result;
	}
	*/
}
