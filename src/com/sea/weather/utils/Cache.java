package com.sea.weather.utils;

import java.util.HashMap;

public class Cache {

	private static final HashMap<String, Object> map = new HashMap<String, Object>();
	
	
	 public static Object getValue(String key){
		 Object v = map.get(key);
		 return v;
	 }
	 
	 public static synchronized void putValue(String key,Object obj){
		 map.put(key, obj);
	 }
}
