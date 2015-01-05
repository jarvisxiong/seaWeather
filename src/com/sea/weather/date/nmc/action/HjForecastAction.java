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
import com.sea.weather.date.nmc.model.HjForecastItemVO;
import com.sea.weather.date.nmc.model.HjForecastTimeVO;
import com.sea.weather.date.nmc.model.HjForecastVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class HjForecastAction {

	private Gson gson = new Gson();
	
	private HjForecastVO getHjForecastVO() throws IOException{
		HjForecastVO objHjForecastVO = new HjForecastVO();
		Document dc_hj = Jsoup.connect("http://www.nmefc.gov.cn/nr/jhhyhjyb.aspx").timeout(5000).get();
		List<Element> listr = getAllTr(dc_hj);
		objHjForecastVO.setLisHjForecastItemVO(putHjForecast(listr));
		objHjForecastVO.setGrabTime(new Date());
		objHjForecastVO.setPublishTime(getPublishTime(dc_hj));
		return objHjForecastVO;
	}
	
	private List<Element> getAllTr(Document dc_hj){
		Elements table = dc_hj.select("table");
		Elements tr = null;
		for(int i=0;i<table.size();i++){
			if(table.select("tr").size()>30){
				tr = table.select("tr");
			}
		}
		List<Element> listr = new ArrayList<Element>();
		for(int i=0;i<tr.size();i++){
			if (tr.get(i).select("td").size() == 7
					&& tr.get(i).select("td").get(0).text().indexOf("海区编号") == -1
					&& tr.get(i).select("td").get(1).text().indexOf("海区编号") == -1
					&& tr.get(i).select("td").get(0).text().indexOf("小时") == -1) {
				listr.add(tr.get(i));
			}
		}
		return listr;
	}
	
	private String getPublishTime(Document dc_hj){
		Elements table = dc_hj.select("table");
		Elements tr = null;
		String str = "";
		for(int i=0;i<table.size();i++){
			if(table.select("tr").size()>30){
				tr = table.select("tr");
			}
		}
		for(int i=0;i<tr.size();i++){
			if(tr.get(i).select("td").size()==1&&tr.get(i).select("td").get(0).text().indexOf("24")!=-1){
				str = tr.get(i).select("td").get(0).text();
			}
		}
		str = str.substring(5, 16);
		return str;
	}
	
	private List<HjForecastItemVO> putHjForecast(List<Element> listr){
		List<Element> listr24 = new ArrayList<Element>();
		List<Element> listr48 = new ArrayList<Element>();
		int count= listr.size()/2;
		for(int i=0;i<count;i++){
			listr24.add(listr.get(i));
			listr48.add(listr.get(count+i));
		}
		List<HjForecastItemVO> lisHjForecastItemVO = new ArrayList<HjForecastItemVO>();
		
		for(int i=0;i<listr24.size();i++){
			HjForecastItemVO objHjForecastItemVO = new HjForecastItemVO();
			String area = listr24.get(i).select("td").get(1).text();
			if(area.equals(listr48.get(i).select("td").get(1).text())){
				objHjForecastItemVO.setArea(area);
			}
			objHjForecastItemVO.setHj24(putTimeVO(listr24.get(i).select("td")));
			objHjForecastItemVO.setHj48(putTimeVO(listr48.get(i).select("td")));
			
			lisHjForecastItemVO.add(objHjForecastItemVO);
		}
		return lisHjForecastItemVO;
	}
	
	private HjForecastTimeVO putTimeVO(Elements td){
		HjForecastTimeVO objHjForecastTimeVO = new HjForecastTimeVO();
		objHjForecastTimeVO.setWaveheight(td.get(2).text());
		objHjForecastTimeVO.setSurgeheight(td.get(3).text());
		objHjForecastTimeVO.setSst(td.get(4).text());
		objHjForecastTimeVO.setSpeed(td.get(5).text());
		objHjForecastTimeVO.setDirection(td.get(6).text());
		return objHjForecastTimeVO;
	}
	
	public String getHjCache(){
		String str = (String)Cache.getValue(Cachekey.hjForecastKey);
		if(StringUtils.isBlank(str)){
			try {
				str = gson.toJson(getHjForecastVO());
				Cache.putValue(Cachekey.hjForecastKey, str);
			} catch (IOException e) {
				Log.e("HjForecastAction.getHjCache", e);
			}
		}
		return str;
	}
	
	public void loadHjCache(){
		try {
			String str = gson.toJson(getHjForecastVO());
			Cache.putValue(Cachekey.hjForecastKey, str);
		} catch (IOException e) {
			Log.e("HjForecastAction.loadHjCache", e);
		}
		
	}
	
	public static void main(String args[]) throws IOException { 
		HjForecastAction objHjForecastAction = new HjForecastAction();
		Gson gson = new Gson();
		//HjForecastVO objHjForecastVO = objHjForecastAction.getHjForecastVO();
		objHjForecastAction.loadHjCache();
	}
}
