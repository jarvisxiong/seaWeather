package com.sea.weather.date.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import com.sea.weather.date.model.TyphoonVO;

public class TyphoonAction {

	private static Document doc_tf;
	private static Document doc_tf_yj;
	
	public static TyphoonVO getTyphoon(){
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		try {
			doc_tf = Jsoup.connect("http://typhoon.weather.com.cn/").get();
			doc_tf_yj = Jsoup.connect("http://typhoon.weather.com.cn/alarm/index.shtml").get();
		} catch (Exception e) {
			System.out.println("webClient error");
		}
		
		Elements elGzTitle =doc_tf.select(".borBox").select(".blockLC");
		String strTitle = elGzTitle.select("em").get(0).text().replaceAll("：", "");
		String strTime = elGzTitle.select("b").get(0).text();
		
		Elements elGzContent =doc_tf.select(".rbox").select("p");
		String strGzContent = "";
		for (int i = 0; elGzContent != null && i < elGzContent.size(); i++) {
			strGzContent = strGzContent + elGzContent.get(i).text();
		}
		
		objTyphoonVO.setGzTitle(strTitle);
		objTyphoonVO.setGzTime(strTime);
		objTyphoonVO.setGzContent(strGzContent);
		try{
	     String dtTitle = "台风动态";
	     String dtContent = getTfDt();
	     objTyphoonVO.setDtTitle(dtTitle);
	     objTyphoonVO.setDtContent(dtContent);
		}catch(Exception e){
			System.out.println("set typhoon error!");
		}
	    getYfYj(objTyphoonVO);
		return objTyphoonVO;
	}
	private static void getYfYj(TyphoonVO objTyphoonVO) {
		 String yjTitle =doc_tf_yj.select("div.col651").select(".borBox").get(0).select("span").text();
	    String yjContent = doc_tf_yj.select(".scroll").select(".clear").select("ul").text();
	    String yjUrl = doc_tf_yj.select(".scroll").select(".clear").select("ul").select("a").attr("href");
		if (yjUrl != null && StringUtils.isNoneBlank(yjUrl)) {
			try {
				Document yjdoc = Jsoup.connect(yjUrl).get();
				String yjUrlsub = yjdoc.select("#new").attr("src");
				Document yjdocSub = Jsoup.connect(yjUrlsub).get();
				Elements yjp =  yjdocSub.select(".content_business").select("div").select("p");
				String yjHasComtent="";
				for(int i=0;yjp!=null&&i<yjp.size();i++){
					if(StringUtils.isNoneBlank(yjp.get(i).text())){
						yjHasComtent = yjHasComtent +yjp.get(i).text()+"\n";
					}
				}
				if(StringUtils.isNoneBlank(yjHasComtent)){
					yjContent = yjHasComtent;
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	    objTyphoonVO.setYjTitle(yjTitle);
	    objTyphoonVO.setYjContent(yjContent);
	}
	
	private static String getTfDt(){
		try {
			Document doc_tf_dt = Jsoup.connect("http://www.typhoon.gov.cn/").get();
			String tfdt =doc_tf_dt.select(".typhoon_warning_home").select(".font14bold").select(".fontyahei").text();
			return tfdt;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String args[]) { 
		System.out.println(getTfDt());
	}
}
