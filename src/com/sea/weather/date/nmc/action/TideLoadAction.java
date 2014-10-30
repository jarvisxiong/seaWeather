package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.TideItemVO;
import com.sea.weather.utils.Log;

public class TideLoadAction {

	private List<TideItemVO> lisTideItemVO = new ArrayList<TideItemVO>();
	
	private void getItem(String url,String selectDate,String code){
		Document dc_Item=null;
		try {
			dc_Item = Jsoup.connect(url).timeout(5000).get();
		} catch (IOException e) {
			Log.e("TideLoadAction.getItem", e);
		}
		
		Elements table = dc_Item.select(".cx-table").select("table").select("tbody").select("tr");
		
		Elements timetd = table.get(1).select("td");
		Elements hightd = table.get(2).select("td");
		
		for(int i=1;i<timetd.size();i++){
			TideItemVO objTideItemVO  = new TideItemVO();
			objTideItemVO.setSelectDate(selectDate);
			objTideItemVO.setCode(code);
			objTideItemVO.setShowTime(timetd.get(i).text());
			objTideItemVO.setHigh(hightd.get(i).text());
			lisTideItemVO.add(objTideItemVO);
		}
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
	
	public static void main(String args[]) { 
		TideLoadAction objTideLoadAction = new TideLoadAction();
		System.out.print(objTideLoadAction.showTide());
	}

}
