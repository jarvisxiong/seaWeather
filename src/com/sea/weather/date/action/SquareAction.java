package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.SquareVO;

public class SquareAction {

	public String getSquare(){
		SquareVO objSquareVO = new SquareVO();
		objSquareVO.setSquareTitle("����:���������°淢��");
		objSquareVO.setSquareUrl("http://lightapp.duapp.com/?appid=125240");
		Gson gson = new Gson();
		return gson.toJson(objSquareVO);
	}
	
}
