package com.sea.weather.date.nmc.model;

public class AreaWeatherVO {

	private String coastName;
	
	private TimeWeatherVO timeWv12;
	
	private TimeWeatherVO timeWv24;
	
	private TimeWeatherVO timeWv36;
	
	private TimeWeatherVO timeWv48;
	
	private TimeWeatherVO timeWv60;
	
	private TimeWeatherVO timeWv72;

	public TimeWeatherVO getTimeWv12() {
		return timeWv12;
	}

	public void setTimeWv12(TimeWeatherVO timeWv12) {
		this.timeWv12 = timeWv12;
	}

	public TimeWeatherVO getTimeWv24() {
		return timeWv24;
	}

	public void setTimeWv24(TimeWeatherVO timeWv24) {
		this.timeWv24 = timeWv24;
	}

	public TimeWeatherVO getTimeWv36() {
		return timeWv36;
	}

	public void setTimeWv36(TimeWeatherVO timeWv36) {
		this.timeWv36 = timeWv36;
	}

	public TimeWeatherVO getTimeWv48() {
		return timeWv48;
	}

	public void setTimeWv48(TimeWeatherVO timeWv48) {
		this.timeWv48 = timeWv48;
	}

	public TimeWeatherVO getTimeWv60() {
		return timeWv60;
	}

	public void setTimeWv60(TimeWeatherVO timeWv60) {
		this.timeWv60 = timeWv60;
	}

	public TimeWeatherVO getTimeWv72() {
		return timeWv72;
	}

	public void setTimeWv72(TimeWeatherVO timeWv72) {
		this.timeWv72 = timeWv72;
	}

	public String getCoastName() {
		return coastName;
	}

	public void setCoastName(String coastName) {
		this.coastName = coastName;
	}
	
}
