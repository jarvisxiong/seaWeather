package com.sea.weather.date.nmc.model;

import java.util.List;

public class GaleWarningVO {

	private String author;
	
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getLisImg() {
		return lisImg;
	}

	public void setLisImg(List<String> lisImg) {
		this.lisImg = lisImg;
	}
	
	
}