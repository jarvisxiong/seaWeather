package com.sea.weather.utils;

import java.util.List;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.AreaWeatherVO;
import com.sea.weather.date.nmc.model.CoastWeatherVO;
import com.sea.weather.date.nmc.model.GdWeatherVO;
import com.sea.weather.date.nmc.model.HnWeatherVO;
import com.sea.weather.date.nmc.model.NhWeatherVO;
import com.sea.weather.date.nmc.model.OceanWeatherVO;
import com.sea.weather.date.nmc.model.OffshoreWeatherVO;

public class EvenAdd {

	
	public void addEven(){
		Gson gson = new Gson();
		CoastWeatherVO objCoastWeatherVO = gson.fromJson((String)Cache.getValue(Cachekey.coastkey), CoastWeatherVO.class);
		add(objCoastWeatherVO.getLisAreaWeatherVO(),"ya");
		OffshoreWeatherVO objOffshoreWeatherVO = gson.fromJson((String)Cache.getValue(Cachekey.offshorekey), OffshoreWeatherVO.class);
		add(objOffshoreWeatherVO.getLisAreaWeatherVO(),"jh");
		OceanWeatherVO objOceanWeatherVO = gson.fromJson((String)Cache.getValue(Cachekey.oceankey), OceanWeatherVO.class);
		add(objOceanWeatherVO.getLisAreaWeatherVO(),"yh");
		GdWeatherVO objGdWeatherVO = gson.fromJson((String)Cache.getValue(Cachekey.gdkey), GdWeatherVO.class);
		add(objGdWeatherVO.getLisAreaWeatherVO(),"gd");
		HnWeatherVO objHnWeatherVO = gson.fromJson((String)Cache.getValue(Cachekey.hnkey), HnWeatherVO.class);
		add(objHnWeatherVO.getLisAreaWeatherVO(),"hn");
		NhWeatherVO objNhWeatherVO = gson.fromJson((String)Cache.getValue(Cachekey.nhkey), NhWeatherVO.class);
		add(objNhWeatherVO.getLisAreaWeatherVO(),"nh");
	}
	
	public void add(List<AreaWeatherVO>  lis,String space){
		for(int i=0;i<lis.size();i++){
			AreaWeatherVO objAreaWeatherVO = lis.get(i);
			Cache.putValue("onCheckAdd"+space+objAreaWeatherVO.getCoastName(), "收藏添加"+space+objAreaWeatherVO.getCoastName());
		}
	}
	public static void main(String[] args) {
		EvenAdd objEvenAdd = new EvenAdd();
		objEvenAdd.addEven();
	}
}
