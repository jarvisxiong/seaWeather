package com.sea.weather.date.model;

public class TyphoonVO {

	private String GzTitle;
	
	private String GzContent;
	
	private String DtTitle;
	
	private String DtContent;
	
	private String GzTime;

	private String YjTitle;
	
	private String YjContent;
	
	public String getGzTime() {
		return GzTime;
	}

	public void setGzTime(String gzTime) {
		GzTime = gzTime;
	}

	public String getGzTitle() {
		return GzTitle;
	}

	public void setGzTitle(String gzTitle) {
		GzTitle = gzTitle;
	}

	public String getGzContent() {
		return GzContent;
	}

	public void setGzContent(String gzContent) {
		GzContent = gzContent;
	}

	public String getDtTitle() {
		return DtTitle;
	}

	public void setDtTitle(String dtTitle) {
		DtTitle = dtTitle;
	}

	public String getDtContent() {
		return DtContent;
	}

	public void setDtContent(String dtContent) {
		DtContent = dtContent;
	}

	public String toString() {
	    StringBuffer sb = new StringBuffer(512);
	    sb.append("TyphoonVO[");
	    sb.append("\n    GzTitle=").append(this.GzTitle);
	    sb.append("\n    GzContent=").append(this.GzContent);
	    sb.append("\n    DtTitle=").append(this.DtTitle);
	    sb.append("\n    DtContent=").append(this.DtContent);
	    sb.append("\n]");
	    return sb.toString();
	}

	public String getYjTitle() {
		return YjTitle;
	}

	public void setYjTitle(String yjTitle) {
		YjTitle = yjTitle;
	}

	public String getYjContent() {
		return YjContent;
	}

	public void setYjContent(String yjContent) {
		YjContent = yjContent;
	}
	
	
}
