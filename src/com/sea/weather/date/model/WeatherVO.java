package com.sea.weather.date.model;

public class WeatherVO {
	//����
	private String seaArea;
	//��������
	private String wind;
	//�ܼ���
	private String visibility;
	
	//Ԥ��ʱ��
	private String forecastTime;

	public String getSeaArea() {
		return seaArea;
	}

	public void setSeaArea(String seaArea) {
		this.seaArea = seaArea;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getForecastTime() {
		return forecastTime;
	}

	public void setForecastTime(String forecastTime) {
		this.forecastTime = forecastTime;
	}

	@Override
	public String toString() {
		return "WeatherVO [seaArea=" + seaArea + ", wind=" + wind
				+ ", visibility=" + visibility + ", forecastTime="
				+ forecastTime +"]";
	}
	
}
