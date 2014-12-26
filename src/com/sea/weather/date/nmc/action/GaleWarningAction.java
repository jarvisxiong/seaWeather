package com.sea.weather.date.nmc.action;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.GaleWarningVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class GaleWarningAction {
	private Gson gson = new Gson();

	private GaleWarningVO getGaleWarningVO() throws IOException{
		Document dc_df=null;
		dc_df = Jsoup.connect("http://sea.weather.gov.cn/mdps/list_t/0601").timeout(5000).get();
		GaleWarningVO objGaleWarningVO = new GaleWarningVO();
		objGaleWarningVO.setAuthor(getAuthor(dc_df));
		objGaleWarningVO.setTitle(getTitle(dc_df));
		objGaleWarningVO.setContent(getContent(dc_df));
		return objGaleWarningVO;
	}
	
	private String getTitle(Document dc_df){
		String title = dc_df.select("#container_right").select("#context_show").select(".writing").select(".subhead").text();
		Log.i(String.valueOf(dc_df.select("#context_show").text()));
		return title;
	}
	
	private String getContent(Document dc_df){
		Elements contents = dc_df.select("#context_show").select(".writing").select("p");
		String content = "";
		for(int i=0;i<contents.size();i++){
			content = content+contents.get(i).text()+"\n";
		}
		return content;
	}
	
	private String getAuthor(Document dc_df){
		String author = dc_df.select("#context_show").select(".author").text();
		return author;
	}
	
	public String getDfCache(){
		String dfVO = (String)Cache.getValue(Cachekey.dfYjKey);
		if(StringUtils.isBlank(dfVO)){
			try {
				dfVO = gson.toJson(getGaleWarningVO());
				Cache.putValue(Cachekey.dfYjKey, dfVO);
			} catch (IOException e) {
				Log.e("GaleWarningAction.getDfCache", e);
			}
		}
		
		return dfVO;
	}
	
	public void loadDfCache(){
		String dfVO;
		try {
			dfVO = gson.toJson(getGaleWarningVO());
			Cache.putValue(Cachekey.dfYjKey, dfVO); 
		} catch (IOException e) {
			Log.e("GaleWarningAction.loadDfCache", e);
		}
	}
	public static void main(String args[]) throws IOException { 
		GaleWarningAction objGaleWarningAction = new GaleWarningAction();
		Gson gson = new Gson();
		System.out.print(gson.toJson(objGaleWarningAction.getGaleWarningVO()));
	}
}
