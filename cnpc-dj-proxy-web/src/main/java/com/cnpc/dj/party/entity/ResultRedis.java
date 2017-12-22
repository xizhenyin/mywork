package com.cnpc.dj.party.entity;

import java.io.Serializable;

public class ResultRedis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6585358798569673300L;
	private String remoteUrl;
	private String paramType;
	private boolean state = true;
	
	public ResultRedis(String remoteUrl, boolean state,String paramType) {
		super();
		this.remoteUrl = remoteUrl;
		this.state = state;
		this.paramType = paramType;
	}
	
	public ResultRedis() {
		
	}
	
	
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	
}
