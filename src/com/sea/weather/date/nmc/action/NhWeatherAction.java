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
import com.sea.weather.date.nmc.model.NhWeatherVO;
import com.sea.weather.date.nmc.model.TimeWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class NhWeatherAction {

private Gson gson = new Gson();
	
	private NhWeatherVO getNhWeatherVO() throws IOException{
		Document dc_nh=null;
		dc_nh = Jsoup.connect("http://www.weather.com.cn/guangdong/hytq/").timeout(5000).get();
		NhWeatherVO objNhWeatherVO = new NhWeatherVO();
		objNhWeatherVO.setLisAreaWeatherVO(getLisAreaWeatherVO(dc_nh));
		objNhWeatherVO.setPublishTime(getPublishTime(dc_nh));
		objNhWeatherVO.setGrabTime(new Date());
		return objNhWeatherVO;
	}
	
	private List<AreaWeatherVO> getLisAreaWeatherVO(Document dc_nh){
		Elements list_tr_24 =dc_nh.select("#tlist7").select("table").select("tbody").select("tr");
		Elements list_tr_48 =dc_nh.select("#tlist8").select("table").select("tbody").select("tr");
		List<AreaWeatherVO> lisAreaWeatherVO = new ArrayList<AreaWeatherVO>();
		for(int i=1;i<list_tr_24.size()-1;i++){
			AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
			Elements list_td_24 = list_tr_24.get(i).select("td");
			objAreaWeatherVO.setCoastName(list_td_24.get(0).text());
			TimeWeatherVO obj24 = new TimeWeatherVO();
			setTimeWeather(obj24, list_td_24,"24");
			objAreaWeatherVO.setTimeWv24(obj24);
			Elements list_td_48 = list_tr_48.get(i).select("td");
			TimeWeatherVO obj48 = new TimeWeatherVO();
			setTimeWeather(obj48, list_td_48,"48");
			objAreaWeatherVO.setTimeWv48(obj48);
			lisAreaWeatherVO.add(objAreaWeatherVO);
		}
		return lisAreaWeatherVO;
	}

	private String getPublishTime(Document dc_nh){
		Elements list_tr =dc_nh.select("#tlist7").select("table").select("tbody").select("tr");
		String publishstr =  list_tr.get(list_tr.size()-1).select("td").text();
		publishstr = publishstr.substring(publishstr.indexOf("20"),publishstr.indexOf("发布")).trim();
		return publishstr;
	}
	
	private void setTimeWeather(TimeWeatherVO obj, Elements list_td,String time) {
		obj.setValidTime(time);
		String w12 = list_td.get(1).select("img").get(0).attr("alt");
		String w24 = list_td.get(1).select("img").get(1).attr("alt");
		if(w12.equals(w24)){
			obj.setWeather(w12);
		}else{
			obj.setWeather(w12+"到"+w24);
		}
		obj.setWindDirection(list_td.get(2).text());
		obj.setVisibility(list_td.get(3).text()+"km");
	}
	
	public String getNhCache(){
		String nhVO = (String)Cache.getValue(Cachekey.nhkey);
		if(StringUtils.isBlank(nhVO)){
			try {
				nhVO = gson.toJson(getNhWeatherVO());
			} catch (IOException e) {
				Log.e("NhWeatherAction.getNhCache", e);
			}
			Cache.putValue(Cachekey.nhkey, nhVO);
		}
		return nhVO;
	}
	
	public void loadNhCache(){
		String nhVO;
		try {
			nhVO = gson.toJson(getNhWeatherVO());
			Cache.putValue(Cachekey.nhkey, nhVO);
		} catch (IOException e) {
			Log.e("NhWeatherAction.loadNhCache", e);
		}
	}
	
	public static void main(String args[]) { 
		NhWeatherAction obj =new NhWeatherAction();
		System.out.print(obj.getNhCache());
	}
}
