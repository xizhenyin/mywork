//package com.cnpc.dj.party.config;
///
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
////@ConfigurationProperties(prefix = "spring.cache")
//public class CacheConfigProps {
//	private Map<String, String> mapProps = new HashMap<>();
//
//
//	
//	public Map<String, String> getMapProps() {
//		return mapProps;
//	}
//
//
//
//	public void setMapProps(Map<String, String> mapProps) {
//		this.mapProps = mapProps;
//	}
//
//
//
//	public Properties getProperties(){
//		Properties properties = new Properties();
//		Set<String> set = mapProps.keySet();
//		for(String key : set){
//			properties.setProperty(key, mapProps.get(key));
//		}
//		return properties;
//	}
//	
//}
