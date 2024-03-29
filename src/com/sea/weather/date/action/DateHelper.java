package com.sea.weather.date.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.sea.weather.date.model.AllTfAreaVO;
import com.sea.weather.date.model.AllWeatherVO;
import com.sea.weather.date.model.AreaWeatherVO;
import com.sea.weather.date.model.TyphoonVO;
import com.sea.weather.date.model.WeatherVO;

public class DateHelper {
	private Document doc_gd_nh;
	
	private Document doc_hn;
	
	private Document doc_tf;
	private HtmlPage htmlPage ;
	private Document doc_tf_yj;
	public DateHelper(){
		try {
			doc_gd_nh = Jsoup.connect("http://www.gdweather.com.cn/guangdong/hytq/index.shtml").get();
			doc_hn = Jsoup.connect("http://hainan.weather.com.cn/hytq/index.shtml").get();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getTitle() {
		
		String title = doc_gd_nh.title();
		Element title_24 = doc_gd_nh.getElementById("stitle5");
		return title;
	}
	
	/**
	 * 获取广东24小时天气预报
	 * @return
	 */
	private AreaWeatherVO getGd24Weather(){
		AreaWeatherVO objAreaWeatherVO = getWeather(doc_gd_nh,"tlist5","stitle5","24");
		return objAreaWeatherVO;
	}
	
	/**
	 * 获取广东24小时天气预报
	 * @return
	 */
	private AreaWeatherVO getGd48Weather(){
		AreaWeatherVO objAreaWeatherVO = this.getWeather(doc_gd_nh,"tlist6", "stitle6", "48");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getNh24Weather(){
		AreaWeatherVO objAreaWeatherVO = this.getWeather(doc_gd_nh,"tlist7", "stitle7", "24");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getNh48Weather(){
		AreaWeatherVO objAreaWeatherVO = this.getWeather(doc_gd_nh,"tlist8", "stitle8", "48");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getHn24Weather(){
		AreaWeatherVO objAreaWeatherVO = this.getHnWeather(doc_hn,"tlist5", "stitle5","ltitle5", "24");
		return objAreaWeatherVO;
	}
	
	private AreaWeatherVO getHn48Weather(){
		AreaWeatherVO objAreaWeatherVO = this.getHnWeather(doc_hn,"tlist6", "stitle6","ltitle6", "48");
		return objAreaWeatherVO;
	}
	
	public String getAllWeatherVOJson(){
		AllWeatherVO objAllWeatherVO =this.getAllWeatherVO();
		Gson gson = new Gson(); 
		String str = gson.toJson(objAllWeatherVO);
		return str;
	}
	
	private AllWeatherVO getAllWeatherVO(){
		AllWeatherVO objAllWeatherVO = new AllWeatherVO();
		objAllWeatherVO.setGd24(this.getGd24Weather());
		objAllWeatherVO.setGd48(this.getGd48Weather());
		objAllWeatherVO.setNh24(this.getNh24Weather());
		objAllWeatherVO.setNh48(this.getNh48Weather());
		objAllWeatherVO.setHn24(this.getHn24Weather());
		objAllWeatherVO.setHn48(this.getHn48Weather());
		return objAllWeatherVO;
	}
	
	
	public String getAllTfAreaVOJson(){
		AllTfAreaVO objAllTfAreaVO = new AllTfAreaVO();
		objAllTfAreaVO.setAllWeatherVO(getAllWeatherVO());
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		objTyphoonVO = this.getTyphoon();
		objAllTfAreaVO.setTf(objTyphoonVO);
		
		objAllTfAreaVO.setGrabTime(new Date());
		
		Gson gson = new Gson(); 
		String str = gson.toJson(objAllTfAreaVO);
		return str;
	}
	
	public static void main(String args[]) { 
		DateHelper objDateHelper = new DateHelper();
		System.out.println(objDateHelper.getTyphoon().getYjContent());
    } 
	
	/**
	 * 获取天气预报的方法
	 * @param tlist
	 * @param stitle
	 * @return
	 */
	private AreaWeatherVO getWeather(Document doc,String tlist,String stitle,String forecastTime) {
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		List<WeatherVO> lisWeatherVO = new ArrayList<WeatherVO>();
		Element list = doc.getElementById(tlist);
		Element list_areaTitle = doc.getElementById(stitle);
		objAreaWeatherVO.setAreaTitle(list_areaTitle.text());
		Elements list_tr = list.select("tr");
		for(int i=1;i<list_tr.size()-1;i++){
			Elements list_td = list_tr.get(i).select("td");
				WeatherVO objWeatherVO = new WeatherVO();
				objWeatherVO.setSeaArea(list_td.get(0).text());
				objWeatherVO.setWind(list_td.get(2).text());
				objWeatherVO.setVisibility(list_td.get(3).text());
				objWeatherVO.setForecastTime(forecastTime);
				lisWeatherVO.add(objWeatherVO);
		}
		Elements list_td = list_tr.get(list_tr.size()-1).select("td");
		objAreaWeatherVO.setCreateTitle(list_td.get(0).text());
		objAreaWeatherVO.setLisWeatherVO(lisWeatherVO);
		return objAreaWeatherVO;
	}
	
	/**
	 * 获取天气预报的方法
	 * @param tlist
	 * @param stitle
	 * @return
	 */
	private AreaWeatherVO getHnWeather(Document doc,String tlist,String stitle,String timeTitle,String forecastTime) {
		AreaWeatherVO objAreaWeatherVO = new AreaWeatherVO();
		List<WeatherVO> lisWeatherVO = new ArrayList<WeatherVO>();
		Element list = doc.getElementById(tlist);
		Element list_areaTitle = doc.getElementById(stitle);
		Element list_timeTitle = doc.getElementById(timeTitle);
		objAreaWeatherVO.setAreaTitle(list_areaTitle.text()+":"+list_timeTitle.text());
		Elements list_tr = list.select("tr");
		for(int i=1;i<list_tr.size()-1;i++){
			Elements list_td = list_tr.get(i).select("td");
				WeatherVO objWeatherVO = new WeatherVO();
				objWeatherVO.setSeaArea(list_td.get(0).text());
				objWeatherVO.setWind(list_td.get(2).text());
				objWeatherVO.setVisibility(list_td.get(3).text());
				objWeatherVO.setForecastTime(forecastTime);
				lisWeatherVO.add(objWeatherVO);
		}
		Elements list_td = list_tr.get(list_tr.size()-1).select("td");
		objAreaWeatherVO.setCreateTitle(list_td.get(0).text());
		objAreaWeatherVO.setLisWeatherVO(lisWeatherVO);
		return objAreaWeatherVO;
	}
	
	private TyphoonVO getTyphoon(){
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		try {
			doc_tf = Jsoup.connect("http://typhoon.weather.com.cn/").get();
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setCssEnabled(false);
	        webClient.getOptions().setJavaScriptEnabled(true);
	        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	        webClient.getOptions().setThrowExceptionOnScriptError(false);
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	        
	        //屏蔽掉日志信息
	        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
	        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
	        webClient.getOptions().setThrowExceptionOnScriptError(false);
	        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	        
	        
	        webClient.setJavaScriptTimeout(5000);
	        htmlPage = (HtmlPage)webClient.getPage("http://typhoon.weather.com.cn/");
			webClient.closeAllWindows();
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
	     Document doc_tfxml = Jsoup.parse(htmlPage.asXml());
	     String dtTitle = doc_tfxml.select(".left_lbox1").select(".tf").select("h1").text();
	     String dtContent = doc_tfxml.select(".left_lbox1").select(".tf").select("ul").text();
	     objTyphoonVO.setDtTitle(dtTitle);
	     objTyphoonVO.setDtContent(dtContent);
		}catch(Exception e){
			System.out.println("set typhoon error!");
		}
	    getYfYj(objTyphoonVO);
		return objTyphoonVO;
		
	}
	private void getYfYj(TyphoonVO objTyphoonVO) {
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
}
