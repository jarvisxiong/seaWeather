package com.sea.weather.date.action;

import java.util.Date;

import com.google.gson.Gson;
import com.sea.weather.date.model.AllTfAreaVO;
import com.sea.weather.date.model.TyphoonVO;

public class SeaWeatherDateAction {

	public String getAllTfAreaVOJson(){
		AllTfAreaVO objAllTfAreaVO = new AllTfAreaVO();
		NanSeaWeatherAction objNanSeaWeatherAction = new NanSeaWeatherAction();
		objAllTfAreaVO.setAllWeatherVO(objNanSeaWeatherAction.getAllWeatherVO());
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		TyphoonAction objTyphoonAction =new TyphoonAction();
		objTyphoonVO = objTyphoonAction.getTyphoon();
		objAllTfAreaVO.setTf(objTyphoonVO);
		
		objAllTfAreaVO.setGrabTime(new Date());
		
		Gson gson = new Gson(); 
		String str = gson.toJson(objAllTfAreaVO);
		return str;
	}
}
