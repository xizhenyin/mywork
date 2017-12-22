package com.cnpc.dj.party.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cnpc.dj.party.interceptors.LoginInterceptor;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

	 public void addInterceptors(InterceptorRegistry registry) {
	        InterceptorRegistration addInterceptor = registry.addInterceptor(new LoginInterceptor());

	        // 排除配置
	      //  addInterceptor.excludePathPatterns("/error");
	        addInterceptor.excludePathPatterns("/login");
	        addInterceptor.excludePathPatterns("/tologin");
	        addInterceptor.excludePathPatterns("/swagger");
	        // 拦截配置
	        addInterceptor.addPathPatterns("/**");
	    }
	 
}
