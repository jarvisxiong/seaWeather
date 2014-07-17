package com.sea.weather.date.action;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;









import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sea.weather.date.model.PushCustomContentVO;
import com.sea.weather.date.model.PushMessagesVO;
import com.sea.weather.date.model.TyphoonVO;
import com.sea.weather.push.ChannelClient;
import com.sea.weather.utils.CacheDate;
import com.sea.weather.utils.SeaConstant;
import com.sea.weather.utils.StringUtils;

public class TyphoonAction {

	private static Document doc_tf;
	private static Document doc_tf_yj;
	
	public static TyphoonVO getTyphoon(){
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		try {
			doc_tf = Jsoup.connect("http://typhoon.weather.com.cn/").timeout(5000).get();
			doc_tf_yj = Jsoup.connect("http://typhoon.weather.com.cn/alarm/index.shtml").timeout(5000).get();
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
				Document yjdoc = Jsoup.connect(yjUrl).timeout(5000).get();
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
	    pushTfYjMsg(yjContent);
	}
	
	private static String getTfDt(){
		try {
			Document doc_tf_dt = Jsoup.connect("http://www.typhoon.gov.cn/").timeout(5000).get();
			String tfdt =doc_tf_dt.select(".typhoon_warning_home").select(".font14bold").select(".fontyahei").text();
			return tfdt;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String args[]) { 
		String str = "2014年07月16日24时";
		String patternStr = "\\d{4}年\\d{1,2}月\\d{1,2}日\\d{1,2}时"; 
		boolean result = Pattern.matches(patternStr, str); 
		System.out.println(result);
	}
	
	private static void pushTfYjMsg(String yjContent){
		String patternStr = "\\d{4}年\\d{1,2}月\\d{1,2}日\\d{1,2}时"; 
		//截取日期到2014年07月16日10时，如果没有台风预警，则字数小于14，不进入发送推送的逻辑
		if(yjContent!=null&&yjContent.length()>14){
			String publishTime = yjContent.substring(0,14);
			//有台风预警，则第一次进来，cache为空，则不等，发送一次推送，并且将时间放到cache里面
			//后面循环进来，如果台风预警时间没变，则一直不进该逻辑，不进入推送,并且还需要符合《2014年07月05日10时》格式
			if(Pattern.matches(patternStr, publishTime)&&!publishTime.equals(CacheDate.getTfYjTime())){
				CacheDate.setTfYjTime(publishTime);
				PushMessagesVO objPushMessagesVO = new PushMessagesVO();
				PushCustomContentVO objPushCustomContentVO = new PushCustomContentVO();
				Gson gson = new Gson();
				objPushMessagesVO.setTitle("台风预警");
				objPushMessagesVO.setDescription("中央台"+publishTime+"发布台风预警");
				
				//设置为内容，启动首页
				objPushCustomContentVO.setUrlKey(SeaConstant.pushTypeUrl);
				objPushCustomContentVO.setUrlValue("http://www.baidu.com/");
				objPushCustomContentVO.setMsgKey(SeaConstant.pushTypeMeg);
				objPushCustomContentVO.setMsgKey(SeaConstant.pushTypeMeg);
				objPushCustomContentVO.setMsgValue(SeaConstant.pushMegAct_SI);
				objPushCustomContentVO.setPushCustomType(SeaConstant.pushTypeMeg);
				objPushMessagesVO.setCustom_content(gson.toJson(objPushCustomContentVO));
				
				String josn = gson.toJson(objPushMessagesVO);
				ChannelClient.pushBroadcastMessage(josn);
			}
		}
	}
}
