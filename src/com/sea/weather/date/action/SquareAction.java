package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.SquareVO;

public class SquareAction {

	public String getSquare(){
		SquareVO objSquareVO = new SquareVO();
		objSquareVO.setSquareTitle("海洋天气网站发布了，点击访问");
		objSquareVO.setSquareUrl("http://seasea.duapp.com/");
		Gson gson = new Gson();
		return gson.toJson(objSquareVO);
	}
	
}
