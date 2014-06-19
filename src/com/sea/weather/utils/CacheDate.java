package com.sea.weather.utils;

import java.util.HashMap;

import com.sea.weather.date.action.DateHelper;

public class CacheDate {

	private final static String key = "allTfAreaVO";

	protected static final HashMap<String, String> map = new HashMap<String, String>(); // Cache table

	private static final Object lock = new Object();

	private CacheDate() {
	} // 防止在外部实例化

	public static String getData() {
		String v = map.get(key);
		if (v == null) {
			synchronized (lock) {
				v = map.get(key); // Check again to avoid re-load
				if (v == null)
					loadDataSource();
				v = map.get(key); // retrieves data.
			}

		}
		return v;

	}
	
	public static synchronized void loadDataSource() {
		DateHelper objDateHelper = new DateHelper();
		String strAllWeatherVO = objDateHelper.getAllTfAreaVOJson();
		map.put(key, strAllWeatherVO);
	}
	
	public static synchronized String loadNewDataSource() { 
		DateHelper objDateHelper = new DateHelper();
		String strAllWeatherVO = objDateHelper.getAllTfAreaVOJson();
		map.put(key, strAllWeatherVO);
		return strAllWeatherVO;
	}
}
