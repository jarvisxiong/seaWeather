package com.sea.weather.date.model;

import java.util.List;

public class AreaWeatherVO {

	List<WeatherVO> lisWeatherVO;
	
	String areaTitle;
	
	String createTitle;

	public List<WeatherVO> getLisWeatherVO() {
		return lisWeatherVO;
	}

	public void setLisWeatherVO(List<WeatherVO> lisWeatherVO) {
		this.lisWeatherVO = lisWeatherVO;
	}

	public String getAreaTitle() {
		return areaTitle;
	}

	public void setAreaTitle(String areaTitle) {
		this.areaTitle = areaTitle;
	}

	public String getCreateTitle() {
		return createTitle;
	}

	public void setCreateTitle(String createTitle) {
		this.createTitle = createTitle;
	}
}
