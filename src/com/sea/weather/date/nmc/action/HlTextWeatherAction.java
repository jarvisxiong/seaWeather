package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.HlTextItemWeatherVO;
import com.sea.weather.date.nmc.model.HlTextWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;

public class HlTextWeatherAction {

	private static Gson gson = new Gson();
	
	public HlTextWeatherVO getHlTextWeatherVO() throws IOException{
		Document dc_hl = null;
		dc_hl = Jsoup.connect("http://www.nmefc.gov.cn/").timeout(5000).get();
		HlTextWeatherVO objHlTextWeatherVO = new HlTextWeatherVO();
		objHlTextWeatherVO.setLisHlTextItemWeatherVO(getLisHlTextItemWeatherVO(dc_hl));
		objHlTextWeatherVO.setPublishTime(getPublishTime(dc_hl));
		objHlTextWeatherVO.setGrabTime(new Date());
		return objHlTextWeatherVO;
	}
	
	private List<HlTextItemWeatherVO> getLisHlTextItemWeatherVO(Document dc_hl){
		Elements list_tr =dc_hl.select("#demo1_1").select("tbody").select("tr");
		List<HlTextItemWeatherVO> lisHlTextItemWeatherVO = new ArrayList<HlTextItemWeatherVO>();
		for(int i=0;i<list_tr.size();i++){
			if(list_tr.get(i).select("td").size()!=0){
				Elements list_td = list_tr.get(i).select("td");
					HlTextItemWeatherVO objHlTextItemWeatherVO = new HlTextItemWeatherVO();
					objHlTextItemWeatherVO.setArea(list_td.get(1).text());
					objHlTextItemWeatherVO.setSpeed(list_td.get(5).text());
					objHlTextItemWeatherVO.setDirection(list_td.get(6).text());
					lisHlTextItemWeatherVO.add(objHlTextItemWeatherVO);
			}
		}
		
		return lisHlTextItemWeatherVO;
	}
	
	private String getPublishTime(Document dc_hl){
		String publishTime = dc_hl.select("#td_1_1").select("table").get(0).select("tbody").select("tr").select("td").text();
		return publishTime;
	}
	
	public String getCacheHlTextWeatherVO(){
		String hlTextWeatherVO = (String)Cache.getValue(Cachekey.hltextkey);
			if(hlTextWeatherVO==null){
				try {
					hlTextWeatherVO = gson.toJson(getHlTextWeatherVO());
					Cache.putValue(Cachekey.hltextkey, hlTextWeatherVO);
				} catch (IOException e) {
					Log.e("HlTextWeatherAction.getCacheHlTextWeatherVO", e);
				}
			}
		return hlTextWeatherVO;
	}
	
	public void loadHlTextWeatherVO(){
		String hlTextWeatherVO;
		try {
			hlTextWeatherVO = gson.toJson(getHlTextWeatherVO());
			Cache.putValue(Cachekey.hltextkey, hlTextWeatherVO);
		} catch (IOException e) {
			Log.e("HlTextWeatherAction.loadHlTextWeatherVO", e);
		}
	}
	
	public static void main(String args[]) { 
		HlTextWeatherAction objHlTextWeatherAction = new HlTextWeatherAction();
		System.out.println(objHlTextWeatherAction.getCacheHlTextWeatherVO());
	}
}