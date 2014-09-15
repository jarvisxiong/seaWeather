package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.UpdateVersionVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.UpdateLog;

public class UpdateVersionAction {

	public String loadVersion(){
		UpdateVersionVO objUpdateVersionVO = new UpdateVersionVO();
		Gson gson = new Gson();
		objUpdateVersionVO.setAppName(UpdateLog.appName);
		objUpdateVersionVO.setVersionCode(UpdateLog.versionCode);
		objUpdateVersionVO.setVersionName(UpdateLog.versionName);
		objUpdateVersionVO.setUpdateUrl(UpdateLog.updateUrl);
		objUpdateVersionVO.setUpdateContent(UpdateLog.updateContent);
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
