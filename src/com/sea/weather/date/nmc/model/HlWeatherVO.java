package com.sea.weather.date.nmc.model;

import java.util.List;
import java.util.Map;

public class HlWeatherVO {

	private List<String> area;
	
	private Map<String,List<String>> time;
	
	private Map<String,String> url;

	public List<String> getArea() {
		return area;
	}

	public void setArea(List<String> area) {
		this.area = area;
	}

	public Map<String, List<String>> getTime() {
		return time;
	}

	public void setTime(Map<String, List<String>> time) {
		this.time = time;
	}

	public Map<String, String> getUrl() {
		return url;
	}

	public void setUrl(Map<String, String> url) {
		this.url = url;
	}

	
	
}
 