package com.sea.weather.date.nmc.action;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.MoreFunctionVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;

public class MoreFunctionAction {

	public String getMoreFunction(){
		String mfVO = (String)Cache.getValue(Cachekey.moreFunction);
		if(mfVO==null){
			MoreFunctionVO objMoreFunctionVO = new MoreFunctionVO();
			Gson gson = new Gson();
			objMoreFunctionVO.setSms("海洋天气下载地址:http://seasea.duapp.com/");
			objMoreFunctionVO.setQrCode("http://readread.duapp.com/sea/img/ic_qr_code.png");
			objMoreFunctionVO.setFeedback("http://lightapp.baidu.com/?appid=125240");
			mfVO = gson.toJson(objMoreFunctionVO);
			Cache.putValue(Cachekey.moreFunction, mfVO);
		}
		return mfVO;
	}
	
}
