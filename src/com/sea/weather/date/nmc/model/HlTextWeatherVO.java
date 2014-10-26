package com.sea.weather.date.nmc.model;

import java.util.Date;
import java.util.List;

public class HlTextWeatherVO {

	private List<HlTextItemWeatherVO> lisHlTextItemWeatherVO;
	
	private String publishTime;
	
	private Date grabTime;

	public List<HlTextItemWeatherVO> getLisHlTextItemWeatherVO() {
		return lisHlTextItemWeatherVO;
	}

	public void setLisHlTextItemWeatherVO(
			List<HlTextItemWeatherVO> lisHlTextItemWeatherVO) {
		this.lisHlTextItemWeatherVO = lisHlTextItemWeatherVO;
	}

	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	
}
