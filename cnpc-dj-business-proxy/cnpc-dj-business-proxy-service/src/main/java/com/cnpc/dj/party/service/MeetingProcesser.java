package com.cnpc.dj.party.service;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MeetingProcesser {
	@Autowired
	private RemoteAPIFactory processerFactory;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	
	
/*	@Override
	public String execute(Map<String, Object> params) throws ThridPartyException{
		try {
			HttpHeaders headers = new HttpHeaders();
	        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
	        headers.setContentType(type);
	        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
	        
	        //HttpEntity<String> entity = new HttpEntity<String>()
	        
	        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
	        params.keySet().stream().forEach(key -> requestEntity.add(key, MapUtils.getString(params, key, "")));
            
			String ret = restTemplate.postForObject(Constants.YH_REMOETE_URL,requestEntity, String.class);
			return ret;
		} catch (Exception e) {
			processerFactory.remove(Constants.MEETING_KEY);
			throw new ThridPartyException(e.getMessage());
		}
	}*/
	
/*	@Scheduled(cron="0/10 * * * * ?") 
	public void heart() {
		try {
			restTemplate.getForObject(Constants.YH_HEART_URL, String.class);
		} catch (Exception e) {
			processerFactory.remove(Constants.MEETING_KEY);
			throw new ThridPartyException(e.getMessage());
		}
	}*/

}
