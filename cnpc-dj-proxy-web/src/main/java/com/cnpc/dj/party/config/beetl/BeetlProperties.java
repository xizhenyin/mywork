package com.cnpc.dj.party.config.beetl;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="beetl")
public class BeetlProperties
{
  private String root = "/templates/";
  private String charset = "UTF-8";
  private String contentType = "text/html;charset=UTF-8";
  private String prefix = "/";
  private String suffix = ".html";
  private String properties = "/beetl.properties";
  
  public String getRoot()
  {
    return this.root;
  }
  
  public void setRoot(String root)
  {
    this.root = root;
  }
  
  public String getCharset()
  {
    return this.charset;
  }
  
  public void setCharset(String charset)
  {
    this.charset = charset;
  }
  
  public String getContentType()
  {
    return this.contentType;
  }
  
  public void setContentType(String contentType)
  {
    this.contentType = contentType;
  }
  
  public String getPrefix()
  {
    return this.prefix;
  }
  
  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
  }
  
  public String getSuffix()
  {
    return this.suffix;
  }
  
  public void setSuffix(String suffix)
  {
    this.suffix = suffix;
  }
  
  public String getProperties()
  {
    return this.properties;
  }
  
  public void setProperties(String properties)
  {
    this.properties = properties;
  }
}

