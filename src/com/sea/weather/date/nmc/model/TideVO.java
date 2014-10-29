package com.sea.weather.date.nmc.model;

import java.util.HashMap;
import java.util.Map;

public class TideVO {

	private Map<String,String> province = new HashMap<String,String>();
	
	private Map<String,String> portcode = new HashMap<String,String>();

	public Map<String, String> getProvince() {
		return province;
	}

	public void setProvince(Map<String, String> province) {
		this.province = province;
	}

	public Map<String, String> getPortcode() {
		return portcode;
	}

	public void setPortcode(Map<String, String> portcode) {
		this.portcode = portcode;
	}

	
	
	
}
