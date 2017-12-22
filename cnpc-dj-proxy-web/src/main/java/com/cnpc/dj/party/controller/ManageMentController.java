package com.cnpc.dj.party.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cnpc.dj.cache.Cache;
import com.cnpc.dj.cache.CacheManager;
import com.cnpc.dj.party.config.WebSecurityConfig;
import com.cnpc.dj.party.entity.ManageMent;
import com.cnpc.dj.party.entity.Result;
import com.cnpc.dj.party.entity.ResultRedis;
import com.cnpc.dj.party.entity.ThirdCompany;
import com.cnpc.dj.party.interceptors.LoginInterceptor;
import com.cnpc.dj.party.service.ManageMentService;
import com.cnpc.dj.party.service.ThirdCompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
@Controller
public class ManageMentController {

	@Autowired
	ManageMentService manageMentService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	@Autowired
	ThirdCompanyService thirdCompanyService;
	
	@Autowired
	CacheManager cacheManager;
	
	@RequestMapping(value = "/get")
	@ResponseBody
	public ManageMent get(@RequestParam(required=false) String id) {
		return manageMentService.getFromRedis(id);
	}
	
	@RequestMapping(value = {"/list", ""})
	public String list(ManageMent manageMent, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		return"";
	}
	
	@RequestMapping(value = "/load")
	@ResponseBody
	public List<ManageMent> load(ManageMent manageMent, HttpServletRequest request, HttpServletResponse response, Model model) {
		return manageMentService.findList(manageMent);
	}
	
	@ApiOperation(value="加载厂商", notes="加载厂商")
	@RequestMapping(value = "/loadByCompany")
	@ResponseBody
	public List<ManageMent> loadByCompany(ManageMent manageMent, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<ManageMent> list =  manageMentService.findList(manageMent);
		return list;
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	@ApiOperation(value="注销", notes="注销")
	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpServletResponse arg1,HttpSession session) {
		session.invalidate();
		return "login";
	}
	/**
	 * 注销页面
	 * @param model
	 * @return
	 */
	@ApiOperation(value="跳转到注销管理", notes="跳转到注销管理")
	@RequestMapping(value = "/logoffList")
	public String logoffList(Model model) {
		//ManageMent ment = new ManageMent();
		//ment.setCompanyId("111");
		List<ThirdCompany> companyList = thirdCompanyService.findList(new ThirdCompany());
		 ObjectMapper mapper = new ObjectMapper();    
	       StringWriter w = new StringWriter();  
	        //Convert between List and JSON    
	   
	   try {
		mapper.writeValue(w, companyList);
		System.out.println(w.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//开始序列化
		
		model.addAttribute("companyList", w.toString());
		return "logoffmain";
	}
	
	
	@ApiOperation(value="跳转到管理页面", notes="跳转到管理页面")
	@RequestMapping(value = "/toMain")
	public String toMain(Model model, HttpServletResponse arg1,String userName,String password, HttpSession session) {

	
      //  model.addAttribute(arg0, arg1)
		List<ThirdCompany> companyList = thirdCompanyService.findList(new ThirdCompany());
		 ObjectMapper mapper = new ObjectMapper();    
	       StringWriter w = new StringWriter();  
	   
	   try {
		mapper.writeValue(w, companyList);
		System.out.println(w.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		model.addAttribute("companyList", w.toString());
		return "main";
	}
	
	
	@ApiOperation(value="跳转到登录页面", notes="存服务接")
	@RequestMapping(value = "/tologin")
	public String tologin(Model model, HttpServletResponse arg1,String userName,String password, HttpSession session) {

		System.out.println(userName);
		System.out.println(password);
		
        if (!"111111".equals(password) || !"admin".equals(userName)) {
        	model.addAttribute("message", "用户名密码错误");
            return "login";
        }
    
        // 设置session
        session.setAttribute(LoginInterceptor.SESSION_KEY, userName);

        model.addAttribute("message", "登录成功");
        
      //  model.addAttribute(arg0, arg1)
//		List<ThirdCompany> companyList = thirdCompanyService.findList(new ThirdCompany());
//		 ObjectMapper mapper = new ObjectMapper();    
//	       StringWriter w = new StringWriter();  
//	   
//	   try {
//		mapper.writeValue(w, companyList);
//		System.out.println(w.toString());
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//		model.addAttribute("companyList", w.toString());
		return "toMain";
	}
	
	@RequestMapping(value = "/form")
	@ResponseBody
	public String form(ManageMent manageMent, Model model) {
		String ret = restTemplate.getForObject("http://localhost:8081/r", String.class);
		return ret;
	}
	
	@ApiOperation(value="保存服务接口", notes="存服务接")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(ManageMent manageMent, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
		//System.out.println("url==/save");
		manageMent.setId(UUID.randomUUID().toString());
		
		int count = manageMentService.insert(manageMent);
		Result result = new Result();
		if(count > 0) {
			result.setCode("0");
			ResultRedis ret	= new ResultRedis(manageMent.getCovertUrl(),manageMent.getStatus()==0 ? true : false,manageMent.getParamType());
			 ObjectMapper mapper = new ObjectMapper();    
		       String json = mapper.writeValueAsString(ret); 
		       
			//	stringRedisTemplate.opsForValue().set(manageMent.getFromUrl(), json);
				
			//redisTemplate.opsForValue().set(manageMent.getFromUrl(), ret);
		//	redisTemplate.opsForValue().set(manageMent.getId(), manageMent);
			
			Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
			cache.put(manageMent.getFromUrl(), json);
			
		}else {
			result.setCode("1");
		}
		
		return result;
		
	}
	
	@ApiOperation(value="更新", notes="更新")
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(ManageMent manageMent, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
		
		int count = manageMentService.updateByPrimaryKey(manageMent);
		Result result = new Result();
		if(count > 0) {
			ResultRedis ret	= new ResultRedis(manageMent.getCovertUrl(),manageMent.getStatus()==0 ? true : false,manageMent.getParamType());
			
			 ObjectMapper mapper = new ObjectMapper();    
		     //  StringWriter w = new StringWriter();  
		    String json = mapper.writeValueAsString(ret); 
		       
//		    stringRedisTemplate.opsForValue().set(manageMent.getFromUrl(), json);
//			
//			redisTemplate.opsForValue().set(manageMent.getId(), manageMent);
			
			Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
			cache.put(manageMent.getFromUrl(), json);
			
			
			result.setCode("0");
		}else {
			result.setCode("1");
		}
		
		return result;
		
	}
	
	/**
	 * 修改接口服务状态
	 * @param manageMent
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws JsonProcessingException
	 */
	@ApiOperation(value="修改状态", notes="修改状态")
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public Result updateStatus(ManageMent manageMent, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
		ManageMent manageMentNew = manageMentService.getFromRedis(manageMent.getId());
		manageMentNew.setStatus(manageMent.getStatus());
		
		
		int count = manageMentService.updateByPrimaryKey(manageMentNew);
		
		Result result = new Result();
		if(count > 0) {
			ResultRedis ret	= new ResultRedis(manageMentNew.getCovertUrl(),manageMentNew.getStatus()==0 ? true : false,manageMent.getParamType());
			
			
			 ObjectMapper mapper = new ObjectMapper();    
		     //  StringWriter w = new StringWriter();  
		       String json = mapper.writeValueAsString(ret); 
		       
		       
//		    stringRedisTemplate.opsForValue().set(manageMentNew.getFromUrl(), json);
//			redisTemplate.opsForValue().set(manageMentNew.getId(), manageMentNew);
			
			Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
			cache.put(manageMentNew.getFromUrl(), json);
			
			result.setCode("0");
		}else {
			result.setCode("1");
		}
		
		return result;
		
	}
	
	@ApiOperation(value="删除服务接口", notes="删除服务接口")
	@RequestMapping(value = "delete")
	public String delete(ManageMent manageMent, RedirectAttributes redirectAttributes) {
		return "";
	}
	
	@ApiOperation(value="删除服务接口", notes="删除服务接口")
	@RequestMapping(value = "ajaxDelete")
	@ResponseBody
	public Result ajaxDelete(String id, RedirectAttributes redirectAttributes) {
		ManageMent manageMent = manageMentService.selectByPrimaryKey(id);
		int  count = manageMentService.deleteByPrimaryKey(id);
		Result result = new Result();
		if(count > 0) {
			//ManageMent manageMentR = (ManageMent)redisTemplate.opsForValue().get(id);
			//if(manageMentR != null) {
				////stringRedisTemplate.delete(manageMent.getFromUrl());
				//// redisTemplate.delete(id);
				 Cache<String,String> cache = cacheManager.getCache("THIRDPROXY");
				 cache.remove(manageMent.getFromUrl());
					
			//}
			result.setCode("0");
		}else {
			result.setCode("1");
		}
		
		
		return result;
	}
}
