package com.cnpc.dj.party.config.beetl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.fun.FileFunctionWrapper;
import org.beetl.core.misc.BeetlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

public class ResourcePatternResolverLoader implements ResourceLoader
{
	  private static final Logger logger = LoggerFactory.getLogger(BeetlAutoConfiguration.class);
	  private String root;
	  private boolean autoCheck = false;
	  private String charset;
	  private ResourcePatternResolver resourcePatternResolver;
	  
	  public ResourcePatternResolverLoader()
	  {
	    this("");
	  }
	  
	  public ResourcePatternResolverLoader(String root)
	  {
	    this(root, "UTF-8");
	  }
	  
	  public ResourcePatternResolverLoader(String root, String charset)
	  {
	    this(root, charset, ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()));
	  }
	  
	  public ResourcePatternResolverLoader(String root, String charset, ResourcePatternResolver resourcePatternResolver)
	  {
	    this.resourcePatternResolver = resourcePatternResolver;
	    if (root.equals("/")) {
	      this.root = "";
	    } else {
	      this.root = root;
	    }
	    this.charset = charset;
	  }
	  
	  public org.beetl.core.Resource getResource(String key)
	  {
	    if (key.startsWith("/")) {
	      key = key.substring(1, key.length());
	    }
	    return new PatternResource(key, "classpath:" + this.root + key, this);
	  }
	  
	  public void close() {}
	  
	  public boolean isModified(org.beetl.core.Resource key)
	  {
	    if (this.autoCheck) {
	      return key.isModified();
	    }
	    return false;
	  }
	  
	  public void init(GroupTemplate gt)
	  {
	    Map<String, String> resourceMap = gt.getConf().getResourceMap();
	    if (resourceMap.get("root") != null)
	    {
	      String temp = (String)resourceMap.get("root");
	      if ((!temp.equals("/")) && (temp.length() != 0)) {
	        if (this.root.endsWith("/")) {
	          this.root += (String)resourceMap.get("root");
	        } else {
	          this.root = (this.root + "/" + (String)resourceMap.get("root"));
	        }
	      }
	    }
	    if (this.charset == null) {
	      this.charset = ((String)resourceMap.get("charset"));
	    }
	    String functionSuffix = (String)resourceMap.get("functionSuffix");
	    
	    this.autoCheck = Boolean.parseBoolean((String)resourceMap.get("autoCheck"));
	    String functionRoot = (String)resourceMap.get("functionRoot");
	    
	    GroupTemplate gt1 = gt;
	    try
	    {
	      PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
	      String s = this.root + functionRoot + File.separator + "**/*." + functionSuffix;
	      org.springframework.core.io.Resource[] resources = pathMatchingResourcePatternResolver.getResources(s);
	      if ((resources != null) && (resources.length > 0)) {
	        for (org.springframework.core.io.Resource resource : resources)
	        {
	          URL url = resource.getURL();
	          String file = url.getFile();
	          int i = file.lastIndexOf(functionRoot + File.separator);
	          String resourceId = file.substring(i, file.length());
	          String fileName = resource.getFilename();
	          String ns = "";
	          fileName = fileName.substring(0, fileName.length() - functionSuffix.length() - 1);
	          String functionName = ns.concat(fileName);
	          FileFunctionWrapper fun = new FileFunctionWrapper(resourceId);
	          gt.registerFunction(functionName, fun);
	        }
	      }
	    }
	    catch (IOException e)
	    {
	      logger.error("初始化functions 失败:" + e.getMessage());
	    }
	  }
	  
	  public boolean exist(String key)
	  {
	    URL url = null;
	    try
	    {
	      url = this.resourcePatternResolver.getResource(this.root + key).getURL();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return url != null;
	  }
	  
	  public String getCharset()
	  {
	    return this.charset;
	  }
	  
	  public void setCharset(String charset)
	  {
	    this.charset = charset;
	  }
	  
	  public String getResourceId(org.beetl.core.Resource resource, String id)
	  {
	    if (resource == null) {
	      return id;
	    }
	    return BeetlUtil.getRelPath(resource.getId(), id);
	  }
	  
	  public ResourcePatternResolver getResourcePatternResolver()
	  {
	    return this.resourcePatternResolver;
	  }
	  
	  public void setResourcePatternResolver(ResourcePatternResolver resourcePatternResolver)
	  {
	    this.resourcePatternResolver = resourcePatternResolver;
	  }

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	}
