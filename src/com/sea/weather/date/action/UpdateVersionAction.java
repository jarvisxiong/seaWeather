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
		objUpdateVersionVO.setVersionCode(7);
		objUpdateVersionVO.setVersionName("1.6");
		objUpdateVersionVO.setUpdateUrl("http://gdown.baidu.com/data/wisegame/3e61ac38e4663dc7/haiyangtianqi_7.apk");
		objUpdateVersionVO.setUpdateContent("1.增加中国远海21个海区；2.全面优化布局，提升体验;3.修改部分缺陷;");
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
