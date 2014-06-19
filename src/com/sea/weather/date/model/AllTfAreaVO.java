package com.sea.weather.date.model;

import java.util.Date;



public class AllTfAreaVO {

private AreaWeatherVO gd24;
	
	private AreaWeatherVO gd48;
	
	private AreaWeatherVO nh24;
	
	private AreaWeatherVO nh48;
	
	private AreaWeatherVO hn24;
	
	private AreaWeatherVO hn48;
	
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

	public AreaWeatherVO getGd24() {
		return gd24;
	}

	public void setGd24(AreaWeatherVO gd24) {
		this.gd24 = gd24;
	}

	public AreaWeatherVO getGd48() {
		return gd48;
	}

	public void setGd48(AreaWeatherVO gd48) {
		this.gd48 = gd48;
	}

	public AreaWeatherVO getNh24() {
		return nh24;
	}

	public void setNh24(AreaWeatherVO nh24) {
		this.nh24 = nh24;
	}

	public AreaWeatherVO getNh48() {
		return nh48;
	}

	public void setNh48(AreaWeatherVO nh48) {
		this.nh48 = nh48;
	}

	public AreaWeatherVO getHn24() {
		return hn24;
	}

	public void setHn24(AreaWeatherVO hn24) {
		this.hn24 = hn24;
	}

	public AreaWeatherVO getHn48() {
		return hn48;
	}

	public void setHn48(AreaWeatherVO hn48) {
		this.hn48 = hn48;
	}
	
}
