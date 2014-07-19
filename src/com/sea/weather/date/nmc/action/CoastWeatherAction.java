package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.AreaWeatherVO;
import com.sea.weather.date.nmc.model.CoastWeatherVO;
import com.sea.weather.date.nmc.model.TimeWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class CoastWeatherAction {

	private static Gson gson = new Gson();
	
	public static CoastWeatherVO getCoastWeather(){
		Document dc_coast=null;
		try {
			dc_coast = Jsoup.connect("http://www.nmc.gov.cn/publish/marine/newcoastal.htm").get();
		} catch (IOException e) {
			Log.e("CoastWeatherAction.getCoastWeather", e);
		}
		CoastWeatherVO objCoastWeatherVO = new CoastWeatherVO();
		objCoastWeatherVO.setLisAreaWeatherVO(getLisAreaWeatherVO(dc_coast));
		objCoastWeatherVO.setGrabTime(new Date());
		return objCoastWeatherVO;
	}
	
	
	private static List<AreaWeatherVO> getLisAreaWeatherVO(Document dc_coast){
		Elements list_tr =dc_coast.select("#datatable").select("tbody").select("tr");
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		List<AreaWeatherVO> lisAreaWeatherVO = new ArrayList<AreaWeatherVO>();
		for (int i = 0; i < list_tr.size(); i++) {
			
			Elements list_td = list_tr.get(i).select("td");
			int j=0;
			if (list_td.size() == 6) {
				objAreaWeatherVO =new AreaWeatherVO();
				objAreaWeatherVO.setCoastName(list_td.get(j++).text());
			}
			
			TimeWeatherVO objTimeWeatherVO = new TimeWeatherVO();
			objTimeWeatherVO.setValidTime(list_td.get(j++).text());
			objTimeWeatherVO.setWeather(list_td.get(j++).text());
			objTimeWeatherVO.setWindDirection(list_td.get(j++).text());
			objTimeWeatherVO.setWindPower(list_td.get(j++).text()+"¼¶");
			objTimeWeatherVO.setVisibility(list_td.get(j++).text()+"km");
			
			if(i%6==0){
				objAreaWeatherVO.setTimeWv12(objTimeWeatherVO);
			}else if(i%6==1){
				objAreaWeatherVO.setTimeWv24(objTimeWeatherVO);
			}else if(i%6==2){
				objAreaWeatherVO.setTimeWv36(objTimeWeatherVO);
			}else if(i%6==3){
				objAreaWeatherVO.setTimeWv48(objTimeWeatherVO);
			}else if(i%6==4){
				objAreaWeatherVO.setTimeWv60(objTimeWeatherVO);
			}else if(i%6==5){
				objAreaWeatherVO.setTimeWv72(objTimeWeatherVO);
				lisAreaWeatherVO.add(objAreaWeatherVO);
			}
		}
		return lisAreaWeatherVO;
	}
	
	public static String getCoastCache(){
		String coastVO = (String)Cache.getValue(Cachekey.coastkey);
		if(StringUtils.isBlank(coastVO)){
			coastVO = gson.toJson(getCoastWeather());
			Cache.putValue(Cachekey.coastkey, coastVO);
		}
		return coastVO;
	}
	
	public static void loadCoastCache(){
		String coastVO = gson.toJson(getCoastWeather());
		Cache.putValue(Cachekey.coastkey, coastVO);
	}
	
	public static void main(String args[]) { 
		System.out.println(getCoastCache());
	}
	
}
