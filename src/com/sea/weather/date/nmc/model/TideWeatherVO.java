package com.sea.weather.date.nmc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TideWeatherVO {

	private List<String> forecastTime = new ArrayList<String>();
	
	private Date grabTime;
	
	private String tideUrl;

	public List<String> getForecastTime() {
		return forecastTime;
	}

	public void setForecastTime(List<String> forecastTime) {
		this.forecastTime = forecastTime;
	}

	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}

	public String getTideUrl() {
		return tideUrl;
	}

	public void setTideUrl(String tideUrl) {
		this.tideUrl = tideUrl;
	}
	
	
}
