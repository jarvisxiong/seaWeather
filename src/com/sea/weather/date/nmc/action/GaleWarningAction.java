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
		Document dc_dfcon = this.getConDoc(dc_df);
		GaleWarningVO objGaleWarningVO = new GaleWarningVO();
		objGaleWarningVO.setAuthor(getAuthor(dc_dfcon));
		objGaleWarningVO.setTitle(getTitle(dc_dfcon));
		objGaleWarningVO.setContent(getContent(dc_dfcon));
		return objGaleWarningVO;
	}
	
	private String getTitle(Document dc_df){
		String title = dc_df.select(".writing").select(".subhead").text();
		return title;
	}
	
	private Document getConDoc(Document dc_df) throws IOException{
		String src = "";
		for(int i=0;i<dc_df.select("script").size();i++){
			if(dc_df.select("script").get(i).html().indexOf("#context_show")!=-1){
				String[] tem = dc_df.select("script").get(i).html().split("'");
				for(int j=0;j<tem.length;j++){
					if(tem[j].indexOf("htm")!=-1){
						src = "http://sea.weather.gov.cn"+tem[j];
					}
				}
			}
		}
		Document dc_dfcon=Jsoup.connect(src).timeout(5000).get();
		return dc_dfcon;
	}
	
	private String getContent(Document dc_df){
		Elements contents = dc_df.select(".writing").select("p");
		String content = "";
		for(int i=0;i<contents.size();i++){
			content = content+contents.get(i).html()+"\n";
		}
		return content;
	}
	
	private String getAuthor(Document dc_df){
		String author = dc_df.select(".author").text();
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
