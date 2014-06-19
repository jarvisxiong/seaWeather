package com.sea.weather.date.model;

import java.util.Date;



public class AllTfAreaVO {


	private AllWeatherVO allWeatherVO;
	
	private TyphoonVO tf;

	private Date grabTime;
	
	public Date getGrabTime() {
		return grabTime;
	}

	public void setGrabTime(Date grabTime) {
		this.grabTime = grabTime;
	}

	public TyphoonVO getTf() {
		return tf;
	}

	public void setTf(TyphoonVO tf) {
		this.tf = tf;
	}

	public AllWeatherVO getAllWeatherVO() {
		return allWeatherVO;
	}

	public void setAllWeatherVO(AllWeatherVO allWeatherVO) {
		this.allWeatherVO = allWeatherVO;
	}

	
	
}
