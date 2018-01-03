package com.cnpc.dj.party.service;


public class RemoteAPIState {
	private String remoteUrl;
	private boolean state = true;
	private String paramType = "1";
	
	public RemoteAPIState() {
	}
	
	public RemoteAPIState(String remoteUrl, boolean state) {
		this.remoteUrl = remoteUrl;
		this.state = state;
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

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	
}
