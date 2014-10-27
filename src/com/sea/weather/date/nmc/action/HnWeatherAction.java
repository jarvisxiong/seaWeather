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
import com.sea.weather.date.nmc.model.HnWeatherVO;
import com.sea.weather.date.nmc.model.TimeWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class HnWeatherAction {

private Gson gson = new Gson();
	
	private HnWeatherVO getHnWeatherVO() throws IOException{
		Document dc_hn=null;
		dc_hn = Jsoup.connect("http://hainan.weather.com.cn/hytq/index.shtml").timeout(5000).get();
		HnWeatherVO objHnWeatherVO = new HnWeatherVO();
		objHnWeatherVO.setLisAreaWeatherVO(getLisAreaWeatherVO(dc_hn));
		objHnWeatherVO.setPublishTime(getPublishTime(dc_hn));
		objHnWeatherVO.setGrabTime(new Date());
		return objHnWeatherVO;
	}
	
	private List<AreaWeatherVO> getLisAreaWeatherVO(Document dc_hn){
		Elements list_tr_24 =dc_hn.select("#tlist5").get(0).select("table").select("tbody").select("tr");
		Elements list_tr_48 =dc_hn.select("#tlist6").select("table").select("tbody").select("tr");
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

	private String getPublishTime(Document dc_hn){
		Elements list_tr =dc_hn.select("#tlist5").select("table").select("tbody").select("tr");
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
	
	public String getHnCache(){
		String hnVO = (String)Cache.getValue(Cachekey.hnkey);
		if(StringUtils.isBlank(hnVO)){
			try {
				hnVO = gson.toJson(getHnWeatherVO());
				Cache.putValue(Cachekey.hnkey, hnVO);
			} catch (IOException e) {
				Log.e("HnWeatherAction.getHnCache", e);
			}
		}
		return hnVO;
	}
	
	public void loadHnCache(){
		String hnVO;
		try {
			hnVO = gson.toJson(getHnWeatherVO());
			Cache.putValue(Cachekey.hnkey, hnVO);
		} catch (IOException e) {
			Log.e("HnWeatherAction.loadHnCache", e);
		}
	}
	
	public static void main(String args[]) { 
		HnWeatherAction obj =new HnWeatherAction();
		System.out.print(obj.getHnCache());
	}
	
}
