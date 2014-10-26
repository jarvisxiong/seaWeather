package com.sea.weather.date.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.HlWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.StringUtils;

public class HlAction {

	private final static String hdh="黄东海";
	private final static String nh="南海";
	private final static String ydy="印度洋";
	private final static String xbtpy="西北太平洋";
	private final static String qq="全球";
	
	private final static String t00="0小时";
	private final static String t24="24小时";
	private final static String t48="48小时";
	private final static String t72="72小时";
	private final static String t96="96小时";
	private final static String t120="120小时";
	
	public String setDate(){
		HlWeatherVO objHlWeatherVO = new HlWeatherVO();
		List<String> area = new ArrayList<>();
		area.add(hdh);
		area.add(nh);
		area.add(ydy);
		area.add(xbtpy);
		area.add(qq);
		objHlWeatherVO.setArea(area);
		
		Map<String,List<String>> time = new HashMap<String,List<String>>();
		List<String> lsthdh = new ArrayList<>(); 
			lsthdh.add(t24);
			lsthdh.add(t48);
			lsthdh.add(t72);
			lsthdh.add(t96);
			lsthdh.add(t120);
		time.put(hdh, lsthdh);
		
		List<String> lisnh = new ArrayList<>(); 
			lisnh.add(t24);
			lisnh.add(t48);
			lisnh.add(t72);
			lisnh.add(t96);
			lisnh.add(t120);
		time.put(nh, lisnh);	
			
		List<String> lisydy = new ArrayList<>(); 
			lisydy.add(t24);
			lisydy.add(t48);
			lisydy.add(t72);
			lisydy.add(t96);
			lisydy.add(t120);
		time.put(ydy, lisydy);
		
		List<String> lisxbtpy = new ArrayList<>(); 
			lisxbtpy.add(t24);
			lisxbtpy.add(t48);
			lisxbtpy.add(t72);
			lisxbtpy.add(t96);
			lisxbtpy.add(t120);
		
		time.put(xbtpy, lisxbtpy);	
		List<String> lisqq = new ArrayList<>(); 
			lisqq.add(t00);	
			lisqq.add(t24);
			lisqq.add(t48);
			lisqq.add(t72);
			lisqq.add(t96);
			lisqq.add(t120);
		time.put(qq, lisqq);
		
		objHlWeatherVO.setTime(time);
		
		Map<String,String> url = new HashMap<String,String>();
		//黄东海
		url.put(add(hdh,t24), "http://www.nmefc.gov.cn/chanpin/hyhj/c-hdh/sped-top-024.jpg");
		url.put(add(hdh,t48), "http://www.nmefc.gov.cn/chanpin/hyhj/c-hdh/sped-top-048.jpg");
		url.put(add(hdh,t72), "http://www.nmefc.gov.cn/chanpin/hyhj/c-hdh/sped-top-072.jpg");
		url.put(add(hdh,t96), "http://www.nmefc.gov.cn/chanpin/hyhj/c-hdh/sped-top-096.jpg");
		url.put(add(hdh,t120), "http://www.nmefc.gov.cn/chanpin/hyhj/c-hdh/sped-top-120.jpg");
		
		url.put(add(nh,t24), "http://www.nmefc.gov.cn/chanpin/hyhj/c-scs/024_uv_0000.png");
		url.put(add(nh,t48), "http://www.nmefc.gov.cn/chanpin/hyhj/c-scs/048_uv_0000.png");
		url.put(add(nh,t72), "http://www.nmefc.gov.cn/chanpin/hyhj/c-scs/072_uv_0000.png");
		url.put(add(nh,t96), "http://www.nmefc.gov.cn/chanpin/hyhj/c-scs/096_uv_0000.png");
		url.put(add(nh,t120), "http://www.nmefc.gov.cn/chanpin/hyhj/c-scs/120_uv_0000.png");
		
		url.put(add(ydy,t24), "http://www.nmefc.gov.cn/chanpin/hyhj/c-ind/024_uv_0000.png");
		url.put(add(ydy,t48), "http://www.nmefc.gov.cn/chanpin/hyhj/c-ind/048_uv_0000.png");
		url.put(add(ydy,t72), "http://www.nmefc.gov.cn/chanpin/hyhj/c-ind/072_uv_0000.png");
		url.put(add(ydy,t96), "http://www.nmefc.gov.cn/chanpin/hyhj/c-ind/096_uv_0000.png");
		url.put(add(ydy,t120), "http://www.nmefc.gov.cn/chanpin/hyhj/c-ind/120_uv_0000.png");
		
		url.put(add(xbtpy,t24), "http://www.nmefc.gov.cn/chanpin/hyhj/c-xbp/024_uv_0000.png");
		url.put(add(xbtpy,t48), "http://www.nmefc.gov.cn/chanpin/hyhj/c-xbp/048_uv_0000.png");
		url.put(add(xbtpy,t72), "http://www.nmefc.gov.cn/chanpin/hyhj/c-xbp/072_uv_0000.png");
		url.put(add(xbtpy,t96), "http://www.nmefc.gov.cn/chanpin/hyhj/c-xbp/072_uv_0000.png");
		url.put(add(xbtpy,t120), "http://www.nmefc.gov.cn/chanpin/hyhj/c-xbp/120_uv_0000.png");
		
		
		url.put(add(qq,t00), "http://www.nmefc.gov.cn/chanpin/szyb/c-gtsc/SSC.Global.00.png");
		url.put(add(qq,t24), "http://www.nmefc.gov.cn/chanpin/szyb/c-gtsc/SSC.Global.24.png");
		url.put(add(qq,t48), "http://www.nmefc.gov.cn/chanpin/szyb/c-gtsc/SSC.Global.48.png");
		url.put(add(qq,t72), "http://www.nmefc.gov.cn/chanpin/szyb/c-gtsc/SSC.Global.72.png");
		url.put(add(qq,t96), "http://www.nmefc.gov.cn/chanpin/szyb/c-gtsc/SSC.Global.96.png");
		url.put(add(qq,t120), "http://www.nmefc.gov.cn/chanpin/szyb/c-gtsc/SSC.Global.120.png");
		
		objHlWeatherVO.setUrl(url);
		
		Gson gson = new Gson();
		String hl = gson.toJson(objHlWeatherVO);
		
		Cache.putValue(Cachekey.hlkey, hl);
		return hl;
		
	}
	
	private String add(String area,String time){
		return area+"_"+time;
	}
	
	public String getHlCache(){
		String hl = (String)Cache.getValue(Cachekey.hlkey);
		if(StringUtils.isBlank(hl)){
			hl = setDate();
		}
		return hl;
	}
	
}
