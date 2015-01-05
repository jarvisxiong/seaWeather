package com.sea.weather.date.nmc.model;

public class HjForecastItemVO {

	// 海区
	private String area;
	
	private HjForecastTimeVO hj24;
	
	private HjForecastTimeVO hj48;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public HjForecastTimeVO getHj24() {
		return hj24;
	}

	public void setHj24(HjForecastTimeVO hj24) {
		this.hj24 = hj24;
	}

	public HjForecastTimeVO getHj48() {
		return hj48;
	}

	public void setHj48(HjForecastTimeVO hj48) {
		this.hj48 = hj48;
	}
	
	
}
