package com.sea.weather.date.nmc.model;

import java.util.Date;
import java.util.List;

public class OffshoreWeatherVO {

	private List<AreaWeatherVO> lisAreaWeatherVO;
	
	private Date grabTime;

	public List<AreaWeatherVO> getLisAreaWeatherVO() {
		return lisAreaWeatherVO;
	}

	public void setLisAreaWeatherVO(List<AreaWeatherVO> lisAreaWeatherVO) {
		this.lisAreaWeatherVO = lisAreaWeatherVO;
	}

	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}
	
	
}
