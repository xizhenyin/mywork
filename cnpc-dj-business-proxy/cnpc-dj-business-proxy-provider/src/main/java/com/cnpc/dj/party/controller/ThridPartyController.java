package com.cnpc.dj.party.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cnpc.dj.common.controller.BaseController;
import com.cnpc.dj.party.api.ThirdPartyGatwayApi;
import com.cnpc.dj.party.entity.Result;
import com.cnpc.dj.party.service.HttpProcesser;
import com.cnpc.dj.party.service.RemoteAPIFactory;
import com.cnpc.dj.party.service.RemoteAPIState;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(ThirdPartyGatwayApi.SERVICE_PATH)
public class ThridPartyController extends BaseController  implements ThirdPartyGatwayApi{
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RemoteAPIFactory remoteAPIFactory;
	@Autowired
	private HttpProcesser httpProcesser;
	
	/**
	 * 接收请求
	 */
	@RequestMapping(value="/{srvName}", method = RequestMethod.POST)
	@ResponseBody
	public String httpService(@PathVariable("srvName") String srvName, @RequestBody String params) throws Exception {
		Result result = new Result();
		
		System.out.println(params);
		RemoteAPIState remoteAPIState = remoteAPIFactory.get(srvName);
		if (null == remoteAPIState) {
			result.setCode("1");
			result.setMsg("该服务不存在！");
		} else {
			Map<String, Object> map = objectMapper.readValue(params, Map.class);
			result = httpProcesser.execute(srvName, remoteAPIState.getRemoteUrl(), remoteAPIState.getParamType(), map);
		}
		return objectMapper.writeValueAsString(result);
	}

/*	@RequestMapping("/meeting/yh")
	@ResponseBody
	public String meeting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		//String state = request.getParameter("state");
		Processer processer = processerFactory.get(Constants.MEETING_KEY);
		if (null == processer) {
			result.setCode("1");
			result.setMsg("该服务不存在！");
		} else {
			Map<String, Object> params = getAllParams(request);
			String ret = (String)processer.execute(params);
			result.setData(ret);
		}
		return objectMapper.writeValueAsString(result);
	}
	
	@RequestMapping("/meeting/yh/recover")
	@ResponseBody
	public String meetingRecover(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = new Result();
		ProcesserState processerState = processerFactory.getValue(Constants.MEETING_KEY);
		if (null == processerState) {
			result.setCode("1");
			result.setMsg("该服务不存在！");
		} else {
			processerState.setState(true);
			result.setCode("0");
			result.setMsg("该服务已恢复！");
		}
		return objectMapper.writeValueAsString(result);
	}*/
	
	private Map<String, Object> getAllParams(HttpServletRequest request) {
		Map<String, Object> ret =  new HashMap<String, Object>();
		Enumeration<String> enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName = enu.nextElement();  
			Object value = request.getParameter(paraName);
			ret.put(paraName, value);
		}
		return ret;
	}
}
