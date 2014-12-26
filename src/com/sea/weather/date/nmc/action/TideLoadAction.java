package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.PortItemVO;
import com.sea.weather.date.nmc.model.TideItemVO;
import com.sea.weather.date.nmc.model.TideVO;
import com.sea.weather.date.nmc.model.TideWeatherVO;
import com.sea.weather.db.dao.TideDAO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;

public class TideLoadAction {

	private List<TideItemVO> lisTideItemVO = new ArrayList<TideItemVO>();
	private Gson gson = new Gson();;
	private void getItem(String url,String selectDate,String code){
		Document dc_Item=null;
		try {
			dc_Item = Jsoup.connect(url).timeout(10000).get();
		} catch (IOException e) {
			Log.e("TideLoadAction.getItem", e);
		}
		
		Elements table = dc_Item.select(".cx-table").select("table").select("tbody").select("tr");
		
		Elements timetd = table.get(1).select("td");
		Elements hightd = table.get(2).select("td");
		Map<String,String> timeMap = new HashMap<String,String>();
		for(int i=1;i<timetd.size();i++){
			TideItemVO objTideItemVO  = new TideItemVO();
			objTideItemVO.setSelectDate(selectDate);
			objTideItemVO.setCode(code);
			String time = timetd.get(i).text();
			String high = hightd.get(i).text();
			String cacheHigh = timeMap.get(time);
			if(high!=null&&!high.equals(cacheHigh)){
				timeMap.put(time, high);
				objTideItemVO.setShowTime(time);
				objTideItemVO.setHigh(high);
				lisTideItemVO.add(objTideItemVO);
			}
		}
		timeMap.clear();
	}
	
	public String showTide(){
		Gson gson = new Gson();
		getItem("http://www.chinaports.com/chaoxi?changed=0&state=0&country=0-0&province=0-0-0&portcode=0-0-0-0&date=2014-10-28","2014-10-28","0-0-0-0");
		return gson.toJson(lisTideItemVO);
	}
	
	private String putUrl(String province,String portcode,String date){
		String url = "http://www.chinaports.com/chaoxi?changed=0&state=0&country=0-0&province="+province+"&portcode="+portcode+"&date="+date;
		return url;
	}
	
	private List<String> getForecastTime(){
		List<String> forecastTime = new ArrayList<String>();
		Date today = new Date();
		long now = today.getTime();
		for(int i=0;i<7;i++){
			today.setTime(now+24*60*60*1000*i);
			forecastTime.add(dateToString(today));
		}
		return forecastTime;
		
	}
	
	private String inForecastTime(){
		List<String> forecastTime = getForecastTime();
		String inForecastTime="";
		for(int i=0;i<forecastTime.size();i++){
			inForecastTime =inForecastTime+"'"+forecastTime.get(i)+"'";
			if(i<forecastTime.size()-1){
				inForecastTime = inForecastTime+",";
			}
		}
		return inForecastTime;
	}
	
	public void loadDate(List<String> forecastTime){
		TideDAO objTideDAO = new TideDAO();
		try {
			List<PortItemVO> province = objTideDAO.queryProvince();
			for (int t = 0; t < forecastTime.size(); t++) {
				String loadDate = forecastTime.get(t);
				for (int i = 0; i < province.size(); i++) {
					String provincecode = province.get(i).getCode();
					List<PortItemVO> portcode = objTideDAO.queryPortcode(provincecode);
					for (int j = 0; j < portcode.size(); j++) {
						String portcodeCode = portcode.get(j).getCode();
						loadOndayDate(loadDate, provincecode, portcodeCode);
					}

				}
				objTideDAO.bathInsertTideItem(lisTideItemVO, "'"+loadDate+"'");
				lisTideItemVO.clear();
			}
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

	private void loadOndayDate(String loadDate, String provincecode,
			String portcodeCode) {
		String url = putUrl(provincecode, portcodeCode, loadDate);
		getItem(url, loadDate, portcodeCode);
	}
	
	private List<TideItemVO> getOndayDate(String loadDate, String portcodeCode) {
		String provincecode = this.getProvinceCode(portcodeCode);
		String url = putUrl(provincecode, portcodeCode, loadDate);
		return getOneItem(url, loadDate, portcodeCode);
	}
	
	
	private List<TideItemVO> getOneItem(String url,String selectDate,String code){
		List<TideItemVO> lisTideItemVO = new ArrayList<TideItemVO>();
		Document dc_Item=null;
		try {
			dc_Item = Jsoup.connect(url).timeout(10000).get();
		} catch (IOException e) {
			Log.e("TideLoadAction.getItem", e);
		}
		
		Elements table = dc_Item.select(".cx-table").select("table").select("tbody").select("tr");
		
		Elements timetd = table.get(1).select("td");
		Elements hightd = table.get(2).select("td");
		Map<String,String> timeMap = new HashMap<String,String>();
		for(int i=1;i<timetd.size();i++){
			TideItemVO objTideItemVO  = new TideItemVO();
			objTideItemVO.setSelectDate(selectDate);
			objTideItemVO.setCode(code);
			String time = timetd.get(i).text();
			String high = hightd.get(i).text();
			String cacheHigh = timeMap.get(time);
			if(high!=null&&!high.equals(cacheHigh)){
				timeMap.put(time, high);
				objTideItemVO.setShowTime(time);
				objTideItemVO.setHigh(high);
				lisTideItemVO.add(objTideItemVO);
			}
		}
		timeMap.clear();
		return lisTideItemVO;
	}
	
	public void load7Date(){
		Log.i("start tide timer");
		Date today = new Date();
		
		TideDAO objTideDAO = new TideDAO();
		
		today.setTime(today.getTime()+24*60*60*1000*6);
		List<String> forecastTime = new ArrayList<String>();
					 forecastTime.add(dateToString(today));
		loadDate(forecastTime);
		
		//删掉昨天的数据
		Date yesterday = new Date();
		try {
			yesterday.setTime(yesterday.getTime()-24*60*60*1000);
			objTideDAO.deleteListTideItem(dateToString(yesterday));
		} catch (SQLException e) {
			Log.e("TideLoadAction.load7Date", e);
		}
		Cache.putValue(Cachekey.tideTimekey, this.getForecastTime());
		String objTideWeatherVO = getTideWeatherVO();
		Cache.putValue(Cachekey.tideWeatherkey, objTideWeatherVO);
		Log.i("end tide timer");
	}
	
	public void loadAllDate(){
		loadDate(getForecastTime());
	}
	
	public String queryDate(String selectDate,String code){
		TideDAO objTideDAO = new TideDAO();
		try {
			List<TideItemVO> lis = objTideDAO.queryListTideItemVO(selectDate, code);
			if(lis==null||lis.size()==0){
				lis = this.getOndayDate(selectDate, code);
				objTideDAO.bathInsertOneTideItem(lis, selectDate, code);
			}
			return gson.toJson(lis);
		} catch (SQLException e) {
			Log.e("TideLoadAction.queryDate", e);
		}
		return null;
	}
	
	private String getProvinceCode(String code){
		return code.substring(0,code.lastIndexOf("-"));
	}
	
	public String queryProvinceAndPortcode(){
		TideVO objTideVO = new TideVO();
		TideDAO objTideDAO = new TideDAO();
		try {
			objTideVO.setProvince(objTideDAO.queryProvince());
			objTideVO.setPortcode(objTideDAO.queryAllPortcode());
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return gson.toJson(objTideVO);
	}
	
	public String getTideWeatherVO(){
		TideWeatherVO objTideWeatherVO = new TideWeatherVO();
		List<String> forecastTime = (List<String>)Cache.getValue(Cachekey.tideTimekey);
		if(forecastTime==null){
			forecastTime = this.getForecastTime();
			Cache.putValue(Cachekey.tideTimekey, forecastTime);
		}
		objTideWeatherVO.setForecastTime(forecastTime);
		objTideWeatherVO.setGrabTime("2014-11-03");
		objTideWeatherVO.setTideUrl("http://readread.duapp.com/sea/TideQueryDate.jsp");
		return gson.toJson(objTideWeatherVO);
	}
	
	private String dateToString(Date time){
		String date =String.format("%tY", time)+"-"+String.format("%tm", time)+"-"+ String.format("%td", time);
		return date;
	}
	
	public String getCacheTideWeatherVO(){
		String objTideWeatherVO = (String)Cache.getValue(Cachekey.tideWeatherkey);
		if(objTideWeatherVO==null){
			objTideWeatherVO = getTideWeatherVO();
			Cache.putValue(Cachekey.tideWeatherkey, objTideWeatherVO);
		}
		return objTideWeatherVO;
	}
	
	public static void main(String args[]) { 
		TideLoadAction objTideLoadAction = new TideLoadAction();
		List<String> forecastTime = new ArrayList<String>();
		forecastTime.add("2014-11-12");
		objTideLoadAction.load7Date();
		Log.i(objTideLoadAction.queryDate("2014-12-01", "0-0-0-0"));
		
	}

}
