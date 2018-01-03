package com.cnpc.dj.party.service;


import java.util.Map;

import com.cnpc.dj.party.entity.Result;


public interface Processer {
	Result execute(String urlKey, String url, String paramType, Map<String, Object> params);
	//Result execute(String urlKey, String url, String paramType, String params);
}
