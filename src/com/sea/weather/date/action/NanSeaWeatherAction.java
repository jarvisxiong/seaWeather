package com.sea.weather.date.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sea.weather.date.model.AllWeatherVO;
import com.sea.weather.date.model.AreaWeatherVO;
import com.sea.weather.date.model.WeatherVO;
import com.sea.weather.utils.Log;

public class NanSeaWeatherAction {
	private Document doc_gd_nh;
	private Document doc_hn;
	
	public AllWeatherVO getAllWeatherVO(){
		try {
			doc_hn = Jsoup.connect("http://hainan.weather.com.cn/hytq/index.shtml").timeout(5000).get();
		} catch (IOException e) {
			Log.e("NanSeaWeatherAction.getHnWeatherVO IO", e);
			return null;
		} catch(Exception e){
			Log.e("NanSeaWeatherAction.getHnWeatherVO Exception", e);
			return null;
		}
		
		try {
			doc_gd_nh = Jsoup.connect("http://www.gdweather.com.cn/guangdong/hytq/index.shtml").timeout(5000).get();
		} catch (IOException e) {
			try{
				doc_gd_nh = Jsoup.connect("http://www.weather.com.cn/guangdong/hytq/").timeout(5000).get();
			}catch(IOException e2){
				Log.e("NanSeaWeatherAction.getGdWeatherVO IO", e);
				return null;
			}
		} catch(Exception e){
			Log.e("NanSeaWeatherAction.getGdWeatherVO Exception", e);
			return null;
		}
		AllWeatherVO objAllWeatherVO = new AllWeatherVO();
		objAllWeatherVO.setGd24(getGd24Weather());
		objAllWeatherVO.setGd48(getGd48Weather());
		objAllWeatherVO.setNh24(getNh24Weather());
		objAllWeatherVO.setNh48(getNh48Weather());
		objAllWeatherVO.setHn24(getHn24Weather());
		objAllWeatherVO.setHn48(getHn48Weather());
		return objAllWeatherVO;
	}
	/**
	 * 获取广东24小时天气预报
	 * @return
	 */
	private AreaWeatherVO getGd24Weather(){
		AreaWeatherVO objAreaWeatherVO = getWeather(doc_gd_nh,"tlist5","stitle5","24");
		return objAreaWeatherVO;
	}
	/**
	 * 获取广东24小时天气预报
	 * @return
	 */
	private AreaWeatherVO getGd48Weather(){
		AreaWeatherVO objAreaWeatherVO = getWeather(doc_gd_nh,"tlist6", "stitle6", "48");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getNh24Weather(){
		AreaWeatherVO objAreaWeatherVO = getWeather(doc_gd_nh,"tlist7", "stitle7", "24");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getNh48Weather(){
		AreaWeatherVO objAreaWeatherVO = getWeather(doc_gd_nh,"tlist8", "stitle8", "48");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getHn24Weather(){
		AreaWeatherVO objAreaWeatherVO = getHnWeather(doc_hn,"tlist5", "stitle5","ltitle5", "24");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getHn48Weather(){
		AreaWeatherVO objAreaWeatherVO = getHnWeather(doc_hn,"tlist6", "stitle6","ltitle6", "48");
		return objAreaWeatherVO;
	}
	
	
	/**
	 * 获取天气预报的方法
	 * @param tlist
	 * @param stitle
	 * @return
	 */
	private AreaWeatherVO getWeather(Document doc,String tlist,String stitle,String forecastTime) {
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		try {
			List<WeatherVO> lisWeatherVO = new ArrayList<WeatherVO>();
			Element list = doc.getElementById(tlist);
			Element list_areaTitle = doc.getElementById(stitle);
			objAreaWeatherVO.setAreaTitle(list_areaTitle.text());
			Elements list_tr = list.select("tr");
			for (int i = 1; i < list_tr.size() - 1; i++) {
				Elements list_td = list_tr.get(i).select("td");
				WeatherVO objWeatherVO = new WeatherVO();
				objWeatherVO.setSeaArea(list_td.get(0).text());
				objWeatherVO.setWind(list_td.get(2).text());
				objWeatherVO.setVisibility(list_td.get(3).text());
				objWeatherVO.setForecastTime(forecastTime);
				lisWeatherVO.add(objWeatherVO);
			}
			Elements list_td = list_tr.get(list_tr.size() - 1).select("td");
			objAreaWeatherVO.setCreateTitle(list_td.get(0).text());
			objAreaWeatherVO.setLisWeatherVO(lisWeatherVO);
		} catch (Exception e) {
			Log.e("NanSeaWeatherAction.getWeather", e);
		}
		return objAreaWeatherVO;
	}
	/**
	 * 获取天气预报的方法
	 * @param tlist
	 * @param stitle
	 * @return
	 */
	private AreaWeatherVO getHnWeather(Document doc,String tlist,String stitle,String timeTitle,String forecastTime) {
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		try {
			List<WeatherVO> lisWeatherVO = new ArrayList<WeatherVO>();
			Element list = doc.getElementById(tlist);
			Element list_areaTitle = doc.getElementById(stitle);
			Element list_timeTitle = doc.getElementById(timeTitle);
			objAreaWeatherVO.setAreaTitle(list_areaTitle.text() + ":"
					+ list_timeTitle.text());
			Elements list_tr = list.select("tr");
			for (int i = 1; i < list_tr.size() - 1; i++) {
				Elements list_td = list_tr.get(i).select("td");
				WeatherVO objWeatherVO = new WeatherVO();
				objWeatherVO.setSeaArea(list_td.get(0).text());
				objWeatherVO.setWind(list_td.get(2).text());
				objWeatherVO.setVisibility(list_td.get(3).text());
				objWeatherVO.setForecastTime(forecastTime);
				lisWeatherVO.add(objWeatherVO);
			}
			Elements list_td = list_tr.get(list_tr.size() - 1).select("td");
			objAreaWeatherVO.setCreateTitle(list_td.get(0).text());
			objAreaWeatherVO.setLisWeatherVO(lisWeatherVO);
		} catch (Exception e) {
			Log.e("NanSeaWeatherAction.getHnWeather", e);
		}
		return objAreaWeatherVO;
	}
	
}
