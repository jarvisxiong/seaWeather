package com.sea.weather.date.action;

import com.google.gson.Gson;
import com.sea.weather.date.model.UpdateVersionVO;

public class UpdateVersionAction {

	public static String showVersion(){
		UpdateVersionVO objUpdateVersionVO = new UpdateVersionVO();
		Gson gson = new Gson();
		objUpdateVersionVO.setAppName("��������");
		objUpdateVersionVO.setVersionCode(5);
		objUpdateVersionVO.setVersionName("1.4");
		objUpdateVersionVO.setUpdateUrl("http://gdown.baidu.com/data/wisegame/4c38781b9fd0f83c/haiyangtianqi_5.apk");
		objUpdateVersionVO.setUpdateContent("1������̨��������\n2���Ľ����ӣ�\n3��������������;");
		return gson.toJson(objUpdateVersionVO);
	}
	
}
