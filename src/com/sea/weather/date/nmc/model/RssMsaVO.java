package com.sea.weather.date.nmc.model;

import java.util.Date;


public class RssMsaVO {

	
	private String marine;

	private String title;

	private String description;
	
	private String link;

	private Date publishedDate;

	public String getMarine() {
		return marine;
	}

	public void setMarine(String marine) {
		this.marine = marine;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
}
