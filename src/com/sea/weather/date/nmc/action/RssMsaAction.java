package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.MarineVO;
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
		Document dc_df = Jsoup.connect("http://readread.duapp.com/sea/RSS.html").timeout(5000).get();
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
		Document dc_df = Jsoup.connect("http://readread.duapp.com/sea/RSS.html").timeout(5000).get();
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
	
	private HashMap<String, String> getJgMarine(){
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
	
	private HashMap<String, String> getTgMarine(){
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
		HashMap<String, String> mapTG = getTgMarine();
		Set<String> key = mapTG.keySet();
		RssMsaDAO objSailNoticeDAO = new RssMsaDAO();
		Date updateDate = new Date(); 
		for(Iterator<String> it = key.iterator();it.hasNext();){
			String marine= it.next();
			String strUrl = mapTG.get(marine);
			URL url =  new URL(strUrl);
			try {
				String[] str = initRss(url,marine);
				String description = str[0];
				updateDate = gson.fromJson(str[1], Date.class);
				objSailNoticeDAO.insertSailNotice(marine,StringUtils.getFullSpell(marine), description,updateDate);
				putMarines(marine, "TG");
			} catch (Exception e) {
				
			}
		}
	}
	
	public void loadJgRss() throws IllegalArgumentException, IOException{
		HashMap<String, String> mapJG = getJgMarine();
		Set<String> key = mapJG.keySet();
		RssMsaDAO objRssMsaDAO = new RssMsaDAO();
		Date updateDate = new Date(); 
		for(Iterator<String> it = key.iterator();it.hasNext();){
			String marine= it.next();
			String strUrl = mapJG.get(marine);
			URL url =  new URL(strUrl);
			try {
				String[] str = initRss(url,marine);
				String description = str[0];
				updateDate = gson.fromJson(str[1], Date.class);
				objRssMsaDAO.insertSailWaring(marine,StringUtils.getFullSpell(marine), description,updateDate);
				putMarines(marine, "JG");
			} catch (Exception e) {
			}
		}
	}
	
	private String[] initRss(URL feedurl,String marine) throws IOException, IllegalArgumentException, FeedException, SQLException{
		List<RssMsaVO> lisRss =new ArrayList<RssMsaVO>();
		Date updateDate = null;
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
					if(updateDate==null||updateDate.before(entry.getPublishedDate())){
						updateDate = entry.getPublishedDate();
					}
					lisRss.add(objRssMsaVO);
				}
		 }
		 String[] str = new String[2];
		 str[0] = GZipUtil.gzip(gson.toJson(lisRss));
		 str[1] = gson.toJson(updateDate);
		 return str;
		 
	}
	
	public String getTgCache(String code){
		RssMsaDAO objRssMsaDAO = new RssMsaDAO();
		String str= "";
		try {
			str = objRssMsaDAO.getSailNotice(code);
		} catch (SQLException e) {
			Log.e("RssMsaAction.getTgCache", e);
		}
		return str;
	}
	
	public String getJgCache(String code){
		RssMsaDAO objRssMsaDAO = new RssMsaDAO();
		String str= "";
		try {
			str = objRssMsaDAO.getSailWaring(code);
		} catch (SQLException e) {
			Log.e("RssMsaAction.getJgCache", e);
		}
		return str;
	}
	
	/**
	 * 设置该海事局是否同时有通告或警告
	 * @param marine
	 * @param msg
	 * @throws SQLException 
	 */
	private void putMarines(String marine,String msg) throws SQLException{
		String code = StringUtils.getFullSpell(marine);
		RssMsaDAO objRssMsaDAO = new RssMsaDAO();
		MarineVO marineVO = objRssMsaDAO.getMarine(code);
		if(marineVO==null){
			marineVO = new MarineVO();
			marineVO.setCode(code);
			marineVO.setName(marine);
			marineVO.setType(msg);
			objRssMsaDAO.insertMarine(marineVO);
		}else{
			String type = marineVO.getType();
			if(type.length()<4&&type.indexOf(msg)==-1){
				if("JG".equals(msg)){
					type = msg+type;
				}else{
					type = type+msg;
				}
				marineVO.setType(type);
				objRssMsaDAO.insertMarine(marineVO);
			}
		}
	}
	
	
	public static void main(String args[]) throws IOException, IllegalArgumentException, FeedException, SQLException { 
		RssMsaAction objMsaRssAction = new RssMsaAction();
		//String str = objMsaRssAction.getJgCache("福建海事局");
		objMsaRssAction.loadTgRss();
		Log.i(StringUtils.getFullSpell("福建海事局"));
		//str = GZipUtil.unGzip(str);
		//Log.i(str);
	}
}
