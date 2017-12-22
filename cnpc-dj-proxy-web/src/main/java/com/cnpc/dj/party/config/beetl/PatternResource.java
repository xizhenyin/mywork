package com.cnpc.dj.party.config.beetl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;

public class PatternResource extends org.beetl.core.Resource
{
	  private String path;
	  private File file = null;
	  private long lastModified = 0L;
	  
	  public PatternResource(String id, String path, ResourceLoader loader)
	  {
	    super(id, loader);
	    this.path = path;
	  }
	  
	  public Reader openReader()
	  {
	    ResourcePatternResolverLoader resolverLoader = (ResourcePatternResolverLoader)this.resourceLoader;
	    URL url = null;
	    try
	    {
	      url = resolverLoader.getResourcePatternResolver().getResource(this.path).getURL();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    if (url == null)
	    {
	      BeetlException be = new BeetlException("TEMPLATE_LOAD_ERROR");
	     // be.resourceId = this.id;
	      throw be;
	    }
	    InputStream is;
	    try
	    {
	      is = url.openStream();
	    }
	    catch (IOException e1)
	    {
	      
	      BeetlException be = new BeetlException("TEMPLATE_LOAD_ERROR");
	    //  be.resourceId = this.id;
	      throw be;
	    }
	    if (is == null)
	    {
	      BeetlException be = new BeetlException("TEMPLATE_LOAD_ERROR");
	   //   be.resourceId = this.id;
	      throw be;
	    }
	    if (url.getProtocol().equals("file"))
	    {
	      this.file = new File(url.getFile());
	      this.lastModified = this.file.lastModified();
	    }
	    try
	    {
	      return new BufferedReader(new InputStreamReader(is, ((ResourcePatternResolverLoader)this.resourceLoader).getCharset()));
	    }
	    catch (UnsupportedEncodingException e) {}
	    return null;
	  }
	  
	  public boolean isModified()
	  {
	    if (this.file != null) {
	      return this.file.lastModified() != this.lastModified;
	    }
	    return false;
	  }
	  
	  public String getId()
	  {
	    return this.id;
	  }
	}

