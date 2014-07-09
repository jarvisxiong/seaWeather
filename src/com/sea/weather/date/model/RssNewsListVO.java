package com.sea.weather.date.model;

import java.util.Date;
import java.util.List;

public class RssNewsListVO {

	private List<RssNewsVO>  lisRssNewsVO;
	
	private Date rssGrabTime;

	public List<RssNewsVO> getLisRssNewsVO() {
		return lisRssNewsVO;
	}

	public void setLisRssNewsVO(List<RssNewsVO> lisRssNewsVO) {
		this.lisRssNewsVO = lisRssNewsVO;
	}

	public Date getRssGrabTime() {
		return rssGrabTime;
	}

	public void setRssGrabTime(Date rssGrabTime) {
		this.rssGrabTime = rssGrabTime;
	}
}
