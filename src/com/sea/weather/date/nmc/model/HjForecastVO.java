package com.sea.weather.date.nmc.model;

import java.util.Date;
import java.util.List;

public class HjForecastVO {

	private List<HjForecastItemVO> lisHjForecastItemVO; 
	
	private String publishTime;
	
	private Date grabTime;

	public List<HjForecastItemVO> getLisHjForecastItemVO() {
		return lisHjForecastItemVO;
	}

	public void setLisHjForecastItemVO(List<HjForecastItemVO> lisHjForecastItemVO) {
		this.lisHjForecastItemVO = lisHjForecastItemVO;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}
	
	
}
