package com.sea.weather.date.action;

import java.util.Date;

import com.google.gson.Gson;
import com.sea.weather.date.model.AllTfAreaVO;
import com.sea.weather.date.model.TyphoonVO;

public class SeaWeatherDateAction {

	public static String getAllTfAreaVOJson(){
		AllTfAreaVO objAllTfAreaVO = new AllTfAreaVO();
		objAllTfAreaVO.setAllWeatherVO(NanSeaWeatherAction.getAllWeatherVO());
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		objTyphoonVO = TyphoonAction.getTyphoon();
		objAllTfAreaVO.setTf(objTyphoonVO);
		
		objAllTfAreaVO.setGrabTime(new Date());
		
		Gson gson = new Gson(); 
		String str = gson.toJson(objAllTfAreaVO);
		return str;
	}
}
