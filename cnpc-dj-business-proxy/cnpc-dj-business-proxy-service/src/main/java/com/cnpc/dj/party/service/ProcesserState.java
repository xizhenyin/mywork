package com.cnpc.dj.party.service;


public class ProcesserState {
	private Processer processer;
	private boolean state = true;
	
	public ProcesserState() {
	}
	
	public ProcesserState(Processer processer, boolean state) {
		this.processer = processer;
		this.state = state;
	}
	
	public Processer getProcesser() {
		return processer;
	}
	public void setProcesser(Processer processer) {
		this.processer = processer;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	
}
