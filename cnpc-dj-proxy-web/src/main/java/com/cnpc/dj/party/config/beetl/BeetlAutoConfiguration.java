package com.cnpc.dj.party.config.beetl;

import java.util.Map;

import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableConfigurationProperties({BeetlProperties.class})
public class BeetlAutoConfiguration
  extends WebMvcConfigurerAdapter
{
  private static final Logger logger = LoggerFactory.getLogger(BeetlAutoConfiguration.class);
  @Autowired
  private BeetlProperties properties;
  
  @Autowired(required=false)
  @Qualifier("beetlFunctionPackages")
  private Map<String, Object> functionPackages = null;
  
  @Bean(initMethod="init", name={"beetlConfig"})
  public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration()
  {
    BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
    ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
    logger.debug("beetl初始化....");
    try
    {
    	if ((this.functionPackages != null) && (this.functionPackages.get("beetlFunctionPackages") != null)) {
            beetlGroupUtilConfiguration.setFunctionPackages((Map)this.functionPackages.get("beetlFunctionPackages"));
        }
    	
    	WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(patternResolver.getResource("classpath:/templates/").getFile().getPath());
        beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
        
        beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
        logger.debug("beetl成功....");
      
	      return beetlGroupUtilConfiguration;
	   }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
    return null;
  }
  
  @Bean(name={"beetlViewResolver"})
  public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration)
  {
    BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
    //beetlSpringViewResolver.setPrefix("/");
    beetlSpringViewResolver.setSuffix(".html");
    beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
    beetlSpringViewResolver.setOrder(0);
    beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
    return beetlSpringViewResolver;
  }
}

