package com.sea.weather.date.nmc.model;

import java.util.Date;
import java.util.List;

public class GdWeatherVO {

	private List<AreaWeatherVO> lisAreaWeatherVO;
	
	private String publishTime;
	
	private Date grabTime;

	public List<AreaWeatherVO> getLisAreaWeatherVO() {
		return lisAreaWeatherVO;
	}

	public void setLisAreaWeatherVO(List<AreaWeatherVO> lisAreaWeatherVO) {
		this.lisAreaWeatherVO = lisAreaWeatherVO;
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
