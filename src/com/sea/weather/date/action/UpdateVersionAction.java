package com.sea.weather.date.action;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.sea.weather.date.model.UpdateVersionVO;
import com.sea.weather.db.dao.UpdateVersionDAO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.UpdateLog;

public class UpdateVersionAction {

	public String loadVersion(){
		UpdateVersionDAO objUpdateVersionDAO = new UpdateVersionDAO();
		UpdateVersionVO objUpdateVersionVO=null;
		try {
			objUpdateVersionVO = objUpdateVersionDAO.get();
		} catch (SQLException e) {
			Log.e("UpdateVersionAction.loadVersion", e);
		}
		if(objUpdateVersionVO==null){
			objUpdateVersionVO = new UpdateVersionVO();
			objUpdateVersionVO.setAppName(UpdateLog.appName);
			objUpdateVersionVO.setVersionCode(UpdateLog.versionCode);
			objUpdateVersionVO.setVersionName(UpdateLog.versionName);
			objUpdateVersionVO.setUpdateUrl(UpdateLog.updateUrl);
			objUpdateVersionVO.setUpdateContent(UpdateLog.updateContent);
		}
		Gson gson = new Gson();
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
