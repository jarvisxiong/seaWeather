package com.sea.weather.date.model;

public class PushCustomContentVO {
	
	String pushCustomType;

	String urlKey;

	String urlValue;

	String msgKey;

	String msgValue;

	public String getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

	public String getUrlValue() {
		return urlValue;
	}

	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getMsgValue() {
		return msgValue;
	}

	public void setMsgValue(String msgValue) {
		this.msgValue = msgValue;
	}
	
	public String getPushCustomType() {
		return pushCustomType;
	}

	public void setPushCustomType(String pushCustomType) {
		this.pushCustomType = pushCustomType;
	}
}
