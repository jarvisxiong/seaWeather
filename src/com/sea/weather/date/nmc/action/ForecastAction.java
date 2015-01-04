package com.sea.weather.date.nmc.action;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.ForecastVO;
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.Cachekey;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.StringUtils;

public class ForecastAction {

	private Gson gson = new Gson();
	
	private String seaUrl = "http://sea.weather.gov.cn";
	
	
	public ForecastVO getForecastVO() throws IOException{
		Document dc_seaGg = Jsoup.connect("http://sea.weather.gov.cn/mdps/list_t/0602").timeout(5000).get();
		Document dc_gg = getConDoc(dc_seaGg);
		ForecastVO objForecastVO = new ForecastVO();
		objForecastVO.setAuthor(getAuthor(dc_gg));
		objForecastVO.setContent(getContent(dc_gg));
		return objForecastVO;
	}
	
	private Document getConDoc(Document dc_df) throws IOException{
		String src = "";
		for(int i=0;i<dc_df.select("script").size();i++){
			String jsHtml = dc_df.select("script").get(i).html();
			if(jsHtml.indexOf("$(\"#context_show\").load")!=-1&&jsHtml.indexOf("htmlPath")==-1){
				String[] tem = dc_df.select("script").get(i).html().split("'");
				for(int j=0;j<tem.length;j++){
					if(tem[j].indexOf("htm")!=-1){
						src = seaUrl+tem[j];
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
	
	public String getGgCache(){
		String gg = (String)Cache.getValue(Cachekey.tqGgKey);
		if(StringUtils.isBlank(gg)){
			try {
				gg = gson.toJson(getForecastVO());
				Cache.putValue(Cachekey.tqGgKey, gg);
			} catch (IOException e) {
				Log.e("ForecastAction.getGgCache", e);
			}
		}
		return gg;
	}
	
	public void loadGgCache(){
		try {
			String gg = gson.toJson(getForecastVO());
			Cache.putValue(Cachekey.tqGgKey, gg);
		} catch (IOException e) {
			Log.e("ForecastAction.loadGgCache", e);
		}
	}
	
	
	private String getAuthor(Document dc_df){
		String author = dc_df.select(".author").text();
		return author;
	}
	public static void main(String args[]) throws IOException { 
		Gson gson = new Gson();
		ForecastAction objForecastAction  = new ForecastAction();
		String objForecastVO = gson.toJson(objForecastAction.getForecastVO());
		System.out.println(objForecastVO);
	}
}
