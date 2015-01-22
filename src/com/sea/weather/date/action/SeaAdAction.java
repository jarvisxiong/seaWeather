package com.sea.weather.date.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.sea.weather.date.model.SeaAdVO;
import com.sea.weather.db.dao.SeaAdDAO;

public class SeaAdAction {

	private final static String phone = "phone";
	
	
	public void insertSeaAdVO() throws SQLException{
		SeaAdDAO objSeaAdDAO = new SeaAdDAO();
		SeaAdVO objSeaAdVO  = new SeaAdVO();
		 UUID uuid = UUID.randomUUID();
		 objSeaAdVO.setId(uuid.toString());
		 objSeaAdVO.setContent("一直都是自己测试的广告系统，好垃圾的广告系统，真蛋疼");
		 objSeaAdVO.setType(phone);
		 objSeaAdVO.setTypevalue("13800138000");
		 objSeaAdVO.setBelong("FavoriteShowActivity");
		 objSeaAdVO.setCustom("张三");
		 objSeaAdVO.setPublishtime(new Date());
		 objSeaAdVO.setBegintime(new Date(System.currentTimeMillis()+12*60*60*1000));
		 objSeaAdVO.setEndtime(new Date(System.currentTimeMillis()+24*60*60*1000));
		 objSeaAdDAO.insertSeaAd(objSeaAdVO);
	}
	
	public String getSeaAdVO(String belong) throws SQLException{
		SeaAdDAO objSeaAdDAO = new SeaAdDAO();
		Gson gson = new Gson();
		SeaAdVO objSeaAdVO = objSeaAdDAO.getSeaAdVO(belong);
		if(objSeaAdVO==null){
			objSeaAdVO = new SeaAdVO();
			objSeaAdVO.setType("NoAD");
		}
		String str = gson.toJson(objSeaAdVO);
		return str;
	}
	
	public static void main(String[] args) throws SQLException {
		SeaAdAction objSeaAdAction = new SeaAdAction();
		objSeaAdAction.insertSeaAdVO();
		System.out.print(objSeaAdAction.getSeaAdVO("Tf3"));
		
	}
	
}
