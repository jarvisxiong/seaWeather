package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.AreaWeatherVO;
import com.sea.weather.date.nmc.model.OceanWeatherVO;
import com.sea.weather.date.nmc.model.TimeWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class OceanWeatherAction {
	
	private Gson gson = new Gson();
	
	public OceanWeatherVO getOceanWeatherVO(){
		Document dc_ocean=null;
		try {
			dc_ocean = Jsoup.connect("http://www.nmc.gov.cn/publish/marine/ocean.htm").timeout(5000).get();
		} catch (IOException e) {
			Log.e("OceanWeatherAction.getOceanWeatherVO", e);
		}
		OceanWeatherVO objOceanWeatherVO = new OceanWeatherVO();
		objOceanWeatherVO.setLisAreaWeatherVO(getLisAreaWeatherVOAll(dc_ocean));
		objOceanWeatherVO.setPublishTime(getPublishTime(dc_ocean));
		objOceanWeatherVO.setGrabTime(new Date());
		return objOceanWeatherVO;
	}
	
	private  List<AreaWeatherVO> getLisAreaWeatherVOAll(Document dc_coast){
		Elements list_tr =dc_coast.select("#datatable").select("tbody").select("tr");
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		List<AreaWeatherVO> lisAreaWeatherVO = new ArrayList<AreaWeatherVO>();
		String strArea = "";
		int indexZ=0;
		for (int i = 0; i < list_tr.size(); i++) {
			int j=0;
			
			Element tr = list_tr.get(i);
			Elements list_td = tr.select("td");
			if(!strArea.equals(tr.attr("name"))){
				if(i>0){
					lisAreaWeatherVO.add(objAreaWeatherVO);
				}
				objAreaWeatherVO = new AreaWeatherVO();
				strArea = list_tr.get(i).attr("name");
				indexZ=0;
				objAreaWeatherVO.setCoastName(list_td.get(j++).text());
			}
			
			TimeWeatherVO objTimeWeatherVO = new TimeWeatherVO();
			objTimeWeatherVO.setValidTime(list_td.get(j++).text());
			objTimeWeatherVO.setWeather(list_td.get(j++).text());
			objTimeWeatherVO.setWindDirection(list_td.get(j++).text() + "��");
			objTimeWeatherVO.setWindPower(list_td.get(j++).text() + "��");
			objTimeWeatherVO.setWaveheight(list_td.get(j++).text() + "m");
			objTimeWeatherVO.setVisibility(list_td.get(j++).text() + "km");
			
			if(indexZ==0){
				objAreaWeatherVO.setTimeWv12(objTimeWeatherVO);
			}else if(indexZ==1){
				objAreaWeatherVO.setTimeWv24(objTimeWeatherVO);
			}else if(indexZ==2){
				objAreaWeatherVO.setTimeWv36(objTimeWeatherVO);
			}else if(indexZ==3){
				objAreaWeatherVO.setTimeWv48(objTimeWeatherVO);
			}else if(indexZ==4){
				objAreaWeatherVO.setTimeWv60(objTimeWeatherVO);
			}else if(indexZ==5){
				objAreaWeatherVO.setTimeWv72(objTimeWeatherVO);
			}
			indexZ++;
			if(i==list_tr.size()-1){
				lisAreaWeatherVO.add(objAreaWeatherVO);
			}
		}
		return lisAreaWeatherVO;
	}
	
	private String getPublishTime(Document dc_ocean) {
		String publishTime = dc_ocean.select("#txtContent1").select(".author").text();
		return publishTime;
	}
	
	public void loadOceanCache(){
		String oceanVO = gson.toJson(getOceanWeatherVO());
		Cache.putValue(Cachekey.oceankey, oceanVO);
	}
	
	public String getOceanCache(){
		String oceanVO = (String)Cache.getValue(Cachekey.oceankey);
		if(StringUtils.isBlank(oceanVO)){
			oceanVO = gson.toJson(getOceanWeatherVO());
			Cache.putValue(Cachekey.oceankey, oceanVO);
		}
		return oceanVO;
	}
	
	public static void main(String args[]) { 
		OceanWeatherAction objOceanWeatherAction = new OceanWeatherAction();
		OceanWeatherVO objOceanWeatherVO = objOceanWeatherAction.getOceanWeatherVO();
		Gson gson = new Gson();
		System.out.println(gson.toJson(objOceanWeatherVO));
	}
}
