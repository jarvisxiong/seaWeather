package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sea.weather.date.nmc.model.PortItemVO;
import com.sea.weather.db.dao.TideDAO;
import com.sea.weather.utils.Log;

public class TideInitAction {
	private List<PortItemVO> portcode = new ArrayList<PortItemVO>();
	public Document getTide(){
		Document dc_tide=null;
		try {
			dc_tide = Jsoup.connect("http://www.chinaports.com/chaoxi?changed=0&state=0&country=0-0&province=0-0-0&portcode=0-0-0-0&date=2014-10-28").timeout(5000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dc_tide;
	}
	
	private List<String> insertProvince(){
		Document dc_tide = getTide();
		String url = "http://www.chinaports.com/chaoxi?changed=0&state=0&country=0-0&province=";
		Elements e =  dc_tide.select("#provinceLi").select("select").select("option");
		
		List<String> lisurl = new ArrayList<String>();
		List<PortItemVO> province = new ArrayList<PortItemVO>();
		for(int i=0;i<e.size();i++){
			if(!"0".equals(e.get(i).attr("value"))){
				PortItemVO objPortItemVO = new PortItemVO();
				objPortItemVO.setCode(e.get(i).attr("value"));
				objPortItemVO.setName(e.get(i).text());
				province.add(objPortItemVO);
				String url1=url+e.get(i).attr("value")+"&portcode="+e.get(i).attr("value")+"-0&date=2014-10-28";
				lisurl.add(url1);
			}
		}
		TideDAO objTideDAO = new TideDAO();
		try {
			objTideDAO.bathInsertProvince(province);
		} catch (SQLException e1) {
			Log.e("TideAction.insertProvince", e1);
		}
		return lisurl;
	}
	
	private void insertPortcode() throws Exception{
		List<String> lisurl =insertProvince();
		Document dc_tide = null;
		
		for(int i=0;i<lisurl.size();i++){
			dc_tide = Jsoup.connect(lisurl.get(i)).get();
			getMap(dc_tide);
		}
		TideDAO objTideDAO = new TideDAO();
		try {
			objTideDAO.bathInsertPortcode(portcode);
		} catch (SQLException e1) {
			Log.e("TideAction.insertPortcode", e1);
		}
	}
	
	private void getMap(Document dc_tide){
		
		Elements e =  dc_tide.select(".select-4").select("option");
		for(int i=0;i<e.size();i++){
			if(!"0".equals(e.get(i).attr("value"))){
				PortItemVO objPortItemVO = new PortItemVO();
				objPortItemVO.setCode(e.get(i).attr("value"));
				objPortItemVO.setName(e.get(i).text());
				portcode.add(objPortItemVO);
			}
		}
	}
	
	public static void main(String args[]) throws Exception { 
		TideInitAction objTideAction = new TideInitAction();
		objTideAction.insertPortcode();
	}
}
 