package com.sea.weather.date.nmc.model;

public class TimeWeatherVO {
	
	//��Чʱ��
	private String validTime;
	
	//��������
	private String weather;
	
	//����
	private String windDirection;
	
	//����
	private String windPower;
	
	//�ܼ���
	private String visibility;
	
	private String waveheight;

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindPower() {
		return windPower;
	}

	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getWaveheight() {
		return waveheight;
	}

	public void setWaveheight(String waveheight) {
		this.waveheight = waveheight;
	}
	
}
