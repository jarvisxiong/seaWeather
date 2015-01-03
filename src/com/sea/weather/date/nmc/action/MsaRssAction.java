package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class MsaRssAction {

	private Gson gson = new Gson();
	
	private String getHxJG() throws IOException{
		Document dc_df = Jsoup.connect("http://127.0.0.1:8080/seaWeather/sea/RSS.html").timeout(5000).get();
		Elements es = dc_df.select(".nrTable").get(0).select("tbody").select("tr");
		HashMap<String, String> mapJG = new HashMap<String, String>();
		for(int i=0;i<es.size();i++){
			Elements td = es.get(i).select("td");
			if(td.size()>2){
				String name = td.get(0).text();
				if(name.endsWith("航行警告")){
					mapJG.put(name.substring(0, name.length()-4), td.get(1).text());
				}
			}
		}
		return gson.toJson(mapJG);
	}
	
	private String getHxTG() throws IOException{
		Document dc_df = Jsoup.connect("http://127.0.0.1:8080/seaWeather/sea/RSS.html").timeout(5000).get();
		Elements es = dc_df.select(".nrTable").get(0).select("tbody").select("tr");
		HashMap<String, String> mapTG = new HashMap<String, String>();
		for(int i=0;i<es.size();i++){
			Elements td = es.get(i).select("td");
			if(td.size()>2){
				String name = td.get(0).text();
				if(name.endsWith("航行通告")){
					mapTG.put(name.substring(0, name.length()-4), td.get(1).text());
				}
			}
		}
		return gson.toJson(mapTG);
	}
	
	public HashMap<String, String> getJgCache(){
		String jg = (String)Cache.getValue(Cachekey.hxjgKey);
		if(StringUtils.isBlank(jg)){
			try {
				jg = getHxJG();
			} catch (IOException e) {
				Log.e("MsaRssAction.getJgCache", e);
			}
		}
		HashMap<String, String> map = gson.fromJson(jg, HashMap.class);
		return map;
	}
	
	public HashMap<String, String> getTgCache(){
		String tg = (String)Cache.getValue(Cachekey.hxtgKey);
		if(StringUtils.isBlank(tg)){
			try {
				tg = getHxTG();
			} catch (IOException e) {
				Log.e("MsaRssAction.getTgCache", e);
			}
		}
		HashMap<String, String> map = gson.fromJson(tg, HashMap.class);
		return map;
	}
	
	public static void main(String args[]) throws IOException { 
		MsaRssAction objMsaRssAction = new MsaRssAction();
		HashMap<String, String> mapJG = objMsaRssAction.getTgCache();
		Log.i(mapJG.get("四川省地方海事局"));
	}
}
