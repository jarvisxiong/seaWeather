package com.sea.weather.date.nmc.model;

import java.util.List;

public class GaleWarningVO {

	private String publishTime;
	
	private String title;
	
	private String content;
	
	private List<String> lisImg;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getLisImg() {
		return lisImg;
	}

	public void setLisImg(List<String> lisImg) {
		this.lisImg = lisImg;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	
}
