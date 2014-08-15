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
import com.sea.weather.date.nmc.model.OffshoreWeatherVO;
import com.sea.weather.date.nmc.model.TimeWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class OffshoreWeatherAction {

	private Gson gson = new Gson();
	
	public OffshoreWeatherVO getOffshoreWeatherVO(){
		Document dc_offshore=null;
		try {
			dc_offshore = Jsoup.connect("http://www.nmc.gov.cn/publish/marine/offshore.htm").timeout(5000).get();
		} catch (IOException e) {
			Log.e("OffshoreWeatherAction.getOffshoreWeatherVO", e);
		}
		OffshoreWeatherVO objOffshoreWeatherVO = new OffshoreWeatherVO();
		objOffshoreWeatherVO = getTrueAreaWeatherVO(dc_offshore,objOffshoreWeatherVO);
		objOffshoreWeatherVO.setGrabTime(new Date());
		return objOffshoreWeatherVO;
	}
	
	private String getNoTablePublishTime(Document dc_offshore){
		String publishTime = dc_offshore.select("#txtContent1").select("h1").select("font ").text().split(":")[1];
		return publishTime;
	}

	private String getPublishTime(Document dc_offshore) {
		String publishTime = dc_offshore.select("#txtContent1").select(".author").text();
		return publishTime;
	}
	
	private List<AreaWeatherVO> getLisAreaWeatherVO(Document dc_coast){
		Elements list_tr =dc_coast.select("#datatable").select("tbody").select("tr");
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		List<AreaWeatherVO> lisAreaWeatherVO = new ArrayList<AreaWeatherVO>();
		for (int i = 0; i < list_tr.size(); i++) {
			
			Elements list_td = list_tr.get(i).select("td");
			int j=0;
			if (list_td.size() == 7) {
				objAreaWeatherVO =new AreaWeatherVO();
				objAreaWeatherVO.setCoastName(list_td.get(j++).text());
			}
			
			TimeWeatherVO objTimeWeatherVO = new TimeWeatherVO();
			objTimeWeatherVO.setValidTime(list_td.get(j++).text());
			objTimeWeatherVO.setWeather(list_td.get(j++).text());
			objTimeWeatherVO.setWindDirection(list_td.get(j++).text()+"风");
			objTimeWeatherVO.setWindPower(list_td.get(j++).text()+"级");
			objTimeWeatherVO.setWaveheight(list_td.get(j++).text()+"m");
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
			objTimeWeatherVO.setWindDirection(list_td.get(j++).text() + "风");
			objTimeWeatherVO.setWindPower(list_td.get(j++).text() + "级");
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
	
	public String getOffshoreCache(){
		String offshoreVO = (String)Cache.getValue(Cachekey.offshorekey);
		if(StringUtils.isBlank(offshoreVO)){
			offshoreVO = gson.toJson(getOffshoreWeatherVO());
			Cache.putValue(Cachekey.offshorekey, offshoreVO);
		}
		return offshoreVO;
	}
	
	public List<AreaWeatherVO> getNoTableLisAreaWeatherVOAll(Document dc_Offshore){
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		List<AreaWeatherVO> lisAreaWeatherVO = new ArrayList<AreaWeatherVO>();
		Elements list_tr =dc_Offshore.select("#txtContent1").select("p");
		String areaname="";
		int indexZ=0;
		for(int i=0;i<list_tr.size();i++){
			if(list_tr.get(i).select("a").size()>0){
				String str = list_tr.get(i).text();
				String[] strarray = str.split(" ");
				String inArea = strarray[0].substring(4);
				if(!areaname.equals(inArea)){
					if(!"".equals(areaname)){
						lisAreaWeatherVO.add(objAreaWeatherVO);
					}
					areaname = inArea;
					objAreaWeatherVO = new AreaWeatherVO();
					indexZ=0;
					objAreaWeatherVO.setCoastName(areaname);
				}
				TimeWeatherVO objTimeWeatherVO = new TimeWeatherVO();
				objTimeWeatherVO.setValidTime(strarray[1]);
				objTimeWeatherVO.setWeather(strarray[2]);
				objTimeWeatherVO.setWindDirection(strarray[3]);
				objTimeWeatherVO.setWindPower(strarray[4]);
				objTimeWeatherVO.setVisibility(strarray[5]);
				objTimeWeatherVO.setWaveheight("-");
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
			}
			if(i==list_tr.size()-1){
				lisAreaWeatherVO.add(objAreaWeatherVO);
			}
		}
		return lisAreaWeatherVO;
	}
	
	private OffshoreWeatherVO getTrueAreaWeatherVO(Document dc_Offshore,OffshoreWeatherVO objOffshoreWeatherVO){
		List<AreaWeatherVO> lisAreaWeatherVO = getLisAreaWeatherVOAll(dc_Offshore);
		if(lisAreaWeatherVO.size()==0){
			lisAreaWeatherVO = getNoTableLisAreaWeatherVOAll(dc_Offshore);
			objOffshoreWeatherVO.setPublishTime(getNoTablePublishTime(dc_Offshore));
		}else{
			objOffshoreWeatherVO.setPublishTime(getPublishTime(dc_Offshore));
		}
		    objOffshoreWeatherVO.setLisAreaWeatherVO(lisAreaWeatherVO);
		
		return objOffshoreWeatherVO;
	}
	
	public void loadOffshoreCache(){
		String offshoreVO = gson.toJson(getOffshoreWeatherVO());
		Cache.putValue(Cachekey.offshorekey, offshoreVO);
	}
	
	public static void main(String args[]) { 
		OffshoreWeatherAction objOffshoreWeatherAction =new OffshoreWeatherAction();
		System.out.println(objOffshoreWeatherAction.getOffshoreCache());
	}
}
