package com.cnpc.dj.party.service;


import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ProcesserFactory {
	private static final ConcurrentHashMap<String, ProcesserState> map = new ConcurrentHashMap<>();
	
	public Processer get(String key) {
		ProcesserState ps = map.get(key);
		if (null == ps || !ps.isState()) return null;
		return map.get(key).getProcesser();
	}
	
	public void put(String key, Processer processer) {
		map.put(key, new ProcesserState(processer, true));
	}
	
	public void remove(String key) {
		ProcesserState ps = map.get(key);
		ps.setState(false);
	}
	
	public void recover(String key) {
		ProcesserState ps = map.get(key);
		ps.setState(true);
	}
	
	public ProcesserState getValue(String key) {
		return map.get(key);
	}
}
