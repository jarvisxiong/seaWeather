package com.sea.weather.date.nmc.action;

import java.util.Date;

import com.google.gson.Gson;
import com.sea.weather.date.action.SquareAction;
import com.sea.weather.date.action.TyphoonAction;
import com.sea.weather.date.action.UpdateVersionAction;
import com.sea.weather.date.nmc.model.AllDateVO;
import com.sea.weather.date.nmc.model.GdWeatherVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.StringUtils;
import com.sea.weather.utils.GZipUtil;

public class AllDateAction {
	public String getAllDateVO(){
		AllDateVO objAllDateVO = new AllDateVO();
		
		CoastWeatherAction objCoastWeatherAction = new CoastWeatherAction();
		objAllDateVO.setCoastWeatherVO(objCoastWeatherAction.getCoastCache());
		
		OffshoreWeatherAction objOffshoreWeatherAction = new OffshoreWeatherAction();
		objAllDateVO.setOffshoreWeatherVO(objOffshoreWeatherAction.getOffshoreCache());
		
		OceanWeatherAction objOceanWeatherAction = new OceanWeatherAction();
		objAllDateVO.setOceanWeatherVO(objOceanWeatherAction.getOceanCache());
		
		GdWeatherAction objGdWeatherAction = new GdWeatherAction();
		objAllDateVO.setGdWeatherVO(objGdWeatherAction.getGdCache());
		
		HnWeatherAction objHnWeatherAction = new HnWeatherAction();
		objAllDateVO.setHnWeatherVO(objHnWeatherAction.getHnCache());
		
		NhWeatherAction objNhWeatherAction = new NhWeatherAction();
		objAllDateVO.setNhWeatherVO(objNhWeatherAction.getNhCache());
		
		TyphoonAction objTyphoonAction = new TyphoonAction();
		objAllDateVO.setTyphoonVO(objTyphoonAction.getTfCache());
		
		UpdateVersionAction objUpdateVersionAction =new UpdateVersionAction();
		objAllDateVO.setUpdateVersionVO(objUpdateVersionAction.getVersion());
		
		SquareAction objSquareAction = new SquareAction();
		objAllDateVO.setSquareVO(objSquareAction.getSquare());
		
		GaleWarningAction objGaleWarningAction = new GaleWarningAction();
		objAllDateVO.setGaleWarningVO(objGaleWarningAction.getDfCache());
		
		ForecastAction objForecastAction = new ForecastAction();
		objAllDateVO.setForecastVO(objForecastAction.getGgCache());
		
		objAllDateVO.setZipTime(new Date());
		
		Gson gson = new Gson();
		return gson.toJson(objAllDateVO);
	}
	
	public String getAllDateCache(){
		String str = (String)Cache.getValue(Cachekey.alldatekey);
		if(StringUtils.isBlank(str)){
			str = GZipUtil.gzip(getAllDateVO());
			Cache.putValue(Cachekey.alldatekey, str);
		}
		return str;
	}
	
	public void loadAllDateCache(){
		String str = GZipUtil.gzip(getAllDateVO());
		Cache.putValue(Cachekey.alldatekey, str);
	}
	
	public static void main(String args[]) { 
		AllDateAction objAllDateAction = new AllDateAction();
		String str = objAllDateAction.getAllDateCache();
		String unStr = GZipUtil.unGzip(str);
		System.out.println(str);
		System.out.println(unStr);
		Gson gson = new Gson();
		AllDateVO objAllDateVO = gson.fromJson(unStr, AllDateVO.class);
		String gdString = objAllDateVO.getGdWeatherVO();
		GdWeatherVO objGdWeatherVO = gson.fromJson(gdString, GdWeatherVO.class);
		System.out.println(objGdWeatherVO.getLisAreaWeatherVO().get(0).getCoastName());
	}
}
