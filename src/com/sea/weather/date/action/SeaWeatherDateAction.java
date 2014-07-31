package com.sea.weather.date.action;

import java.util.Date;

import com.google.gson.Gson;
import com.sea.weather.date.model.AllTfAreaVO;
import com.sea.weather.date.model.TyphoonVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;

public class SeaWeatherDateAction {

	private Gson gson = new Gson(); 
	public String getAllTfAreaVOJson(){
		AllTfAreaVO objAllTfAreaVO = new AllTfAreaVO();
		NanSeaWeatherAction objNanSeaWeatherAction = new NanSeaWeatherAction();
		objAllTfAreaVO.setAllWeatherVO(objNanSeaWeatherAction.getAllWeatherVO());
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		TyphoonAction objTyphoonAction =new TyphoonAction();
		objTyphoonVO = objTyphoonAction.getTyphoon();
		objAllTfAreaVO.setTf(objTyphoonVO);
		
		objAllTfAreaVO.setGrabTime(new Date());
		String str = gson.toJson(objAllTfAreaVO);
		//需要放在转换后面否则会出现都有版本升级信息
		putCacheTfVersion(objAllTfAreaVO);
		return str;
	}
	
	private void putCacheTfVersion(AllTfAreaVO objAllTfAreaVO){
		TyphoonVO objTyphoonVO =  objAllTfAreaVO.getTf();
		String tfGz = objTyphoonVO.getGzContent();
		tfGz = "海洋天气1.5版本发布了，请点击新闻获取下载地址\n"+tfGz;
		objTyphoonVO.setGzContent(tfGz);
		objAllTfAreaVO.setTf(objTyphoonVO);
		String str = gson.toJson(objAllTfAreaVO);
		Cache.putValue(Cachekey.tfkey_5, str); 
	}
}
