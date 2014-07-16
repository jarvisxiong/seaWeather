package com.sea.weather.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.sea.weather.date.action.RssNewAction;
import com.sea.weather.date.action.SeaWeatherDateAction;
import com.sun.syndication.io.FeedException;

public class CacheDate {

	private final static String key = "allTfAreaVO";
	
	private final static String urlKey = "urlIndex";
	
	private final static String rssKey = "rssKey";
	
	private final static String tfYjTimeKey = "tfYjTimeKey";

	protected static final HashMap<String, Object> map = new HashMap<String, Object>(); // Cache table

	private static final Object lock = new Object();

	private CacheDate() {
	} // 防止在外部实例化

	public static String getData() {
		String v = (String)map.get(key);
		if (v == null) {
			synchronized (lock) {
				v = (String)map.get(key); // Check again to avoid re-load
				if (v == null)
					loadDataSource();
				v = (String)map.get(key); // retrieves data.
			}

		}
		return v;

	}
	
	public static String getRssNewsList() {
		String v = (String)map.get(rssKey);
		if (v == null) {
			synchronized (lock) {
				v = (String)map.get(rssKey); // Check again to avoid re-load
				if (v == null)
					try {
						loadRssNewsList();
					} catch (Exception e) {
						System.out.println("do RssNewsTask Exception first:"
								+ e.getClass().getName() + ",time:" + new Date());
					}
				v = (String)map.get(rssKey); // retrieves data.
			}
		}
		return v;

	}
	
	public static synchronized void loadDataSource() {
		String strAllWeatherVO = SeaWeatherDateAction.getAllTfAreaVOJson();
		map.put(key, strAllWeatherVO);
	}
	
	public static synchronized String loadNewDataSource() { 
		String strAllWeatherVO = SeaWeatherDateAction.getAllTfAreaVOJson();
		map.put(key, strAllWeatherVO);
		return strAllWeatherVO;
	}
	
	public static synchronized void loadRssNewsList() throws IllegalArgumentException, IOException, FeedException{
		map.put(rssKey, RssNewAction.gradNews());
	}
	
	public static synchronized int getUrlIndex(){
		int urlIndex = 0;
		Object obj = map.get(urlKey);
		if(obj!=null){
			urlIndex = (int)obj;
		}
		if (urlIndex < RssNewAction.urlSize) {
			int i = urlIndex;
			map.put(urlKey, ++i);
		}else{
			map.put(urlKey, 0);
		}
		return urlIndex;
	}

	public static String getTfYjTime() {
		String v = (String) map.get(tfYjTimeKey);
		return v;
	}
	
	public static synchronized void setTfYjTime(String tfYjTime) {
		map.put(tfYjTimeKey, tfYjTime);
	}
}
