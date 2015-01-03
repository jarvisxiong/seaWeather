package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.RssMsaVO;
import com.sea.weather.db.dao.RssMsaDAO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.GZipUtil;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssMsaAction {

	private Gson gson = new Gson();
	
	private String getHxJG() throws IOException{
		Document dc_df = Jsoup.connect("http://127.0.0.1:8080/seaWeather/sea/RSS.html").timeout(5000).get();
		Elements es = dc_df.select(".nrTable").get(0).select("tbody").select("tr");
		HashMap<String, String> mapJG = new HashMap<String, String>();
		for(int i=0;i<es.size();i++){
			Elements td = es.get(i).select("td");
			if(td.size()>2){
				String name = td.get(0).text();
				if(name.endsWith("航行警告")){
					mapJG.put(name.substring(0, name.length()-4), td.get(1).text());
				}
			}
		}
		return gson.toJson(mapJG);
	}
	
	private String getHxTG() throws IOException{
		Document dc_df = Jsoup.connect("http://127.0.0.1:8080/seaWeather/sea/RSS.html").timeout(5000).get();
		Elements es = dc_df.select(".nrTable").get(0).select("tbody").select("tr");
		HashMap<String, String> mapTG = new HashMap<String, String>();
		for(int i=0;i<es.size();i++){
			Elements td = es.get(i).select("td");
			if(td.size()>2){
				String name = td.get(0).text();
				if(name.endsWith("航行通告")){
					mapTG.put(name.substring(0, name.length()-4), td.get(1).text());
				}
			}
		}
		return gson.toJson(mapTG);
	}
	
	public HashMap<String, String> getJgCache(){
		String jg = (String)Cache.getValue(Cachekey.hxjgKey);
		if(StringUtils.isBlank(jg)){
			try {
				jg = getHxJG();
				Cache.putValue(Cachekey.hxjgKey, jg);
			} catch (IOException e) {
				Log.e("MsaRssAction.getJgCache", e);
			}
		}
		HashMap<String, String> map = gson.fromJson(jg, HashMap.class);
		return map;
	}
	
	public HashMap<String, String> getTgCache(){
		String tg = (String)Cache.getValue(Cachekey.hxtgKey);
		if(StringUtils.isBlank(tg)){
			try {
				tg = getHxTG();
				Cache.putValue(Cachekey.hxtgKey, tg);
			} catch (IOException e) {
				Log.e("MsaRssAction.getTgCache", e);
			}
		}
		HashMap<String, String> map = gson.fromJson(tg, HashMap.class);
		return map;
	}
	
	public void loadTgRss() throws IllegalArgumentException, IOException{
		HashMap<String, String> mapTG = getTgCache();
		Set<String> key = mapTG.keySet();
		for(Iterator<String> it = key.iterator();it.hasNext();){
			String marine= it.next();
			String strUrl = mapTG.get(marine);
			URL url =  new URL(strUrl);
			try {
				initRssTg(url,marine);
			} catch (Exception e) {
				
			}
		}
	}
	
	private void initRssTg(URL feedurl,String marine) throws IOException, IllegalArgumentException, FeedException, SQLException{
		List<RssMsaVO> lisRss =new ArrayList<RssMsaVO>();
		
		URLConnection uc = feedurl.openConnection();
		 uc.setConnectTimeout(5000);
		 uc.setReadTimeout(5000);
		 SyndFeedInput input = new SyndFeedInput();
		 SyndFeed feed = input.build(new XmlReader(uc)); 
		 List<?> entries = feed.getEntries(); 
		 for (int i = 0; i < entries.size(); i++){   
			    SyndEntry entry = (SyndEntry) entries.get(i);   
				if (entry != null ) {
					RssMsaVO objRssMsaVO = new RssMsaVO();
					objRssMsaVO.setMarine(marine);
					objRssMsaVO.setTitle(entry.getTitle().trim());
					objRssMsaVO.setDescription(entry.getDescription().getValue());
					objRssMsaVO.setLink(entry.getLink());
					objRssMsaVO.setPublishedDate(entry.getPublishedDate());
					lisRss.add(objRssMsaVO);
				}
		 }
		 
		 RssMsaDAO objSailNoticeDAO = new RssMsaDAO();
		 objSailNoticeDAO.insertSailNotice(marine, GZipUtil.gzip(gson.toJson(lisRss)));
		 
	}
	
	
	public static void main(String args[]) throws IOException, IllegalArgumentException, FeedException, SQLException { 
		RssMsaAction objMsaRssAction = new RssMsaAction();
		objMsaRssAction.loadTgRss();
	}
}
