package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.SquareVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;

public class SquareAction {

	public String getSquare(){
		SquareVO objSquareVO = (SquareVO)Cache.getValue(Cachekey.square);
		if (objSquareVO == null) {
			objSquareVO = new SquareVO();
			objSquareVO.setSquareTitle("海洋天气官网，点击访问");
			objSquareVO.setSquareUrl("http://seasea.duapp.com/");
			Cache.putValue(Cachekey.square, objSquareVO);
		}
		Gson gson = new Gson();
		return gson.toJson(objSquareVO);
	}
	
}
