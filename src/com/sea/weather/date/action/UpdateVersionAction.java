package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.UpdateVersionVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;

public class UpdateVersionAction {

	public String loadVersion(){
		UpdateVersionVO objUpdateVersionVO = new UpdateVersionVO();
		Gson gson = new Gson();
		objUpdateVersionVO.setAppName("海洋天气");
		objUpdateVersionVO.setVersionCode(5);
		objUpdateVersionVO.setVersionName("1.4");
		objUpdateVersionVO.setUpdateUrl("http://gdown.baidu.com/data/wisegame/4c38781b9fd0f83c/haiyangtianqi_5.apk");
		objUpdateVersionVO.setUpdateContent("1、增加台风天气；\n2、改进样子；\n3、增加新闻速览;");
		String str = gson.toJson(objUpdateVersionVO);
		Cache.putValue(Cachekey.versionkey, str);
		return str;
	}
	
	public String getVersion(){
		String strVersion = (String)Cache.getValue(Cachekey.versionkey);
		if(strVersion==null){
			strVersion = loadVersion();
		}
		return strVersion;
	}
}
