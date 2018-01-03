package com.cnpc.dj.party.api;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(value = ThirdPartyGatwayApi.CNPC_DJ_BUSINESS_PROXY,
path = ThirdPartyGatwayApi.SERVICE_PATH)
public interface ThirdPartyGatwayApi {
	 String SERVICE_PATH = "proxy";
	public static final String CNPC_DJ_BUSINESS_PROXY = "CNPC-DJ-BUSINESS-PROXY";
	
	@RequestMapping(value="/{srvName}", method = RequestMethod.POST)
	@ResponseBody
	public String httpService(@PathVariable("srvName") String srvName, @RequestBody String params) throws Exception ;

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
	
	//private Map<String, Object> getAllParams(HttpServletRequest request) ;
	
}
