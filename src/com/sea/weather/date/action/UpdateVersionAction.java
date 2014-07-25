package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.UpdateVersionVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;

public class UpdateVersionAction {

	public String loadVersion(){
		UpdateVersionVO objUpdateVersionVO = new UpdateVersionVO();
		Gson gson = new Gson();
		objUpdateVersionVO.setAppName("��������");
		objUpdateVersionVO.setVersionCode(6);
		objUpdateVersionVO.setVersionName("1.5");
		objUpdateVersionVO.setUpdateUrl("http://gdown.baidu.com/data/wisegame/92cac8000afacb76/haiyangtianqi_6.apk");
		objUpdateVersionVO.setUpdateContent("1.�����й��ذ�34�����������������㽭��������ɽ���ȵط���2.�����й�����18����������Ԥ��������������;3.�޸�̨���������֣�����ʹ��;");
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
