package com.sea.weather.date.action;

import java.util.Date;




import com.google.gson.Gson;
import com.sea.weather.date.model.AllTfAreaVO;
import com.sea.weather.date.model.TyphoonVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.StringUtils;

public class SeaWeatherDateAction {

	private Gson gson = new Gson(); 
	public String getAllTfAreaVOJson(){
		AllTfAreaVO objAllTfAreaVO = new AllTfAreaVO();
		NanSeaWeatherAction objNanSeaWeatherAction = new NanSeaWeatherAction();
		objAllTfAreaVO.setAllWeatherVO(objNanSeaWeatherAction.getAllWeatherVO());
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		TyphoonAction objTyphoonAction =new TyphoonAction();
		objTyphoonVO = objTyphoonAction.getTyphoon();
		objAllTfAreaVO.setTf(objTyphoonVO);
		
		objAllTfAreaVO.setGrabTime(new Date());
		String str = gson.toJson(objAllTfAreaVO);
		//需要放在转换后面否则会出现都有版本升级信息
		return str;
	}
	
	
	
	private AllTfAreaVO getAllTfAreaVO(){
		AllTfAreaVO objAllTfAreaVO = new AllTfAreaVO();
		NanSeaWeatherAction objNanSeaWeatherAction = new NanSeaWeatherAction();
		objAllTfAreaVO.setAllWeatherVO(objNanSeaWeatherAction.getAllWeatherVO());
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		TyphoonAction objTyphoonAction =new TyphoonAction();
		objTyphoonVO = objTyphoonAction.getTyphoon();
		objAllTfAreaVO.setTf(objTyphoonVO);
		objAllTfAreaVO.setGrabTime(new Date());
		return objAllTfAreaVO;
	}
	public String getCacheAllTfAreaVOJson(){
		String allTfAreaVO = (String)Cache.getValue(Cachekey.allTfAreakey);
		if(allTfAreaVO==null){
			AllTfAreaVO objAllTfAreaVO = getAllTfAreaVO();
			allTfAreaVO = gson.toJson(objAllTfAreaVO);
			if(allTfAreaVOIsNull(objAllTfAreaVO)){
				Cache.putValue(Cachekey.allTfAreakey, allTfAreaVO);
			}
		}
		return allTfAreaVO;
	}
	
	public void loadAllTfAreaCache(){
		AllTfAreaVO objAllTfAreaVO = getAllTfAreaVO();
		if (allTfAreaVOIsNull(objAllTfAreaVO)) {
			String allTfAreaVO = gson.toJson(objAllTfAreaVO);
			Cache.putValue(Cachekey.allTfAreakey, allTfAreaVO);
		}
	}
	
	public boolean allTfAreaVOIsNull(AllTfAreaVO objAllTfAreaVO){
		if(objAllTfAreaVO==null){
			return false;
		}
		if(objAllTfAreaVO.getTf()==null){
			return false;
		}
		
		if(objAllTfAreaVO.getAllWeatherVO()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getGd24()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getGd48()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getNh24()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getNh48()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getHn24()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getHn48()==null){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getGd24().getLisWeatherVO().size()==0){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getGd48().getLisWeatherVO().size()==0){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getNh24().getLisWeatherVO().size()==0){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getNh48().getLisWeatherVO().size()==0){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getHn24().getLisWeatherVO().size()==0){
			return false;
		}
		if(objAllTfAreaVO.getAllWeatherVO().getHn48().getLisWeatherVO().size()==0){
			return false;
		}
		return true;
	}
}
