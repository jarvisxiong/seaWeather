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
		objUpdateVersionVO.setVersionCode(6);
		objUpdateVersionVO.setVersionName("1.5");
		objUpdateVersionVO.setUpdateUrl("http://gdown.baidu.com/data/wisegame/92cac8000afacb76/haiyangtianqi_6.apk");
		objUpdateVersionVO.setUpdateContent("1.增加中国沿岸34个海区天气，包括浙江，福建，山东等地方；2.增加中国近海18个海区天气预报，包括渤海等;3.修改台风天气布局，方便使用;");
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
