package com.sea.weather.date.nmc.model;

import java.util.ArrayList;
import java.util.List;

public class TideVO {

	private List<PortItemVO> province = new ArrayList<PortItemVO>();
	
	private List<PortItemVO> portcode = new ArrayList<PortItemVO>();

	public List<PortItemVO> getProvince() {
		return province;
	}

	public void setProvince(List<PortItemVO> province) {
		this.province = province;
	}

	public List<PortItemVO> getPortcode() {
		return portcode;
	}

	public void setPortcode(List<PortItemVO> portcode) {
		this.portcode = portcode;
	}

}
