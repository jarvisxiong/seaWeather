package com.sea.weather.date.action;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.model.PushCustomContentVO;
import com.sea.weather.date.model.PushMessagesVO;
import com.sea.weather.date.model.TyphoonVO;
import com.sea.weather.push.ChannelClient;
import com.sea.weather.utils.CacheDate;
import com.sea.weather.utils.Log;
import com.sea.weather.utils.SeaConstant;
import com.sea.weather.utils.StringUtils;

public class TyphoonAction {

	private Document doc_tf;
	private Document doc_tf_yj;
	
	public TyphoonVO getTyphoon(){
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		try {
			doc_tf = Jsoup.connect("http://typhoon.weather.com.cn/").timeout(5000).get();
			doc_tf_yj = Jsoup.connect("http://typhoon.weather.com.cn/alarm/index.shtml").timeout(5000).get();
		} catch (IOException e) {
			Log.e("TyphoonAction.getTyphoon IOException", e);
			return null;
		}
		
		setZyGz(objTyphoonVO);
		
		//获取台风动态
		String dtTitle = "台风动态";
		String dtContent = getZyTfDt();
		objTyphoonVO.setDtTitle(dtTitle);
		objTyphoonVO.setDtContent(dtContent);
		
		//获取台风预警
	    //getYfYj(objTyphoonVO);
		getAllYjTf(objTyphoonVO);
		return objTyphoonVO;
	}
	
	private void setGz(TyphoonVO objTyphoonVO) {
		//设置台风关注
		try {
			Elements elGzTitle = doc_tf.select(".borBox").select(".blockLC");
			String strTitle = elGzTitle.select("em").get(0).text()
					.replaceAll("：", "");
			String strTitleUrl = elGzTitle.select("h2").select("span").select("a").attr("href");
			Document gzTldoc = Jsoup.connect(strTitleUrl).timeout(5000).get();
			String strTime = elGzTitle.select("b").get(0).text();
			String strImgUrl = gzTldoc.select(".content_doc").select("img").attr("src");
			Elements elGzContent = doc_tf.select(".rbox").select("p");
			String strGzContent = "";
			for (int i = 0; elGzContent != null && i < elGzContent.size(); i++) {
				strGzContent = strGzContent + elGzContent.get(i).text();
			}

			objTyphoonVO.setGzTitle(strTitle);
			objTyphoonVO.setGzTime(strTime);
			objTyphoonVO.setGzImgUrl(strImgUrl);
			objTyphoonVO.setGzContent(strGzContent);
		} catch (Exception e) {
			Log.e("TyphoonAction.getTyphoon Exception", e);
		}
	}
	
	private void setZyGz(TyphoonVO objTyphoonVO){
		try {
			String rooturl = "http://www.weather.gov.cn/publish/typhoon/warning.htm";
			String imgUrl ="http://www.weather.gov.cn/publish/typhoon/probability-img.html";
			Document doc_tf_gz = Jsoup.connect(rooturl).timeout(5000).get();
			Document doc_img = Jsoup.connect(imgUrl).timeout(5000).get();
			String gzContent = doc_tf_gz.select(".writing").text();
			String gzImgUrl = doc_img.select("#img_path").attr("src");
			String gzTime = doc_tf_gz.select("#txtContent1").select(".author").text();
			gzTime = gzTime.substring(gzTime.indexOf("20"));
			objTyphoonVO.setGzTitle("台风关注");
			objTyphoonVO.setGzTime(gzTime);
			objTyphoonVO.setGzImgUrl(gzImgUrl);
			objTyphoonVO.setGzContent(gzContent);
		} catch (IOException e) {
			Log.e("TyphoonAction.getTfDt", e);
		}
	}
	
	private void getYfYj(TyphoonVO objTyphoonVO) {
		 String yjTitle ="台风预警";
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
				Log.e("TyphoonAction.getYfYj", e);
			}
		}
	    objTyphoonVO.setYjTitle(yjTitle);
	    objTyphoonVO.setYjContent(yjContent);
	    pushTfYjMsg(yjContent);
	}
	
	private void getAllYjTf(TyphoonVO objTyphoonVO){
		String yjTitle ="台风预警";
		Elements yjContentDoc = doc_tf_yj.select(".scroll").select(".clear").select("ul").select("li");
		String yjContent = "";
		if(yjContentDoc==null||yjContentDoc.size()==0){
			yjContent = doc_tf_yj.select(".scroll").select(".clear").select("ul").text();
		}else{
		for(int i=0;i<yjContentDoc.size();i++){
			yjContent = yjContent+yjContentDoc.get(i).text()+"\n";
		}
		}
		 String yjUrl = doc_tf_yj.select(".scroll").select(".clear").select("ul").select("a").attr("href");
		 String yjUrlName = doc_tf_yj.select(".scroll").select(".clear").select("ul").select("a").text();
		 if (yjUrl != null && StringUtils.isNoneBlank(yjUrl)&&yjUrlName.indexOf("中央")!=-1) {
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
					Log.e("TyphoonAction.getYfYj", e);
				}
			}
		 objTyphoonVO.setYjTitle(yjTitle);
		 objTyphoonVO.setYjContent(yjContent.trim());
		 //调试，暂时屏蔽消息推送
		 //pushTfYjMsg(yjContent);
	}
	
	//暂时不要的方法，这个网站更新较慢，并且链接较慢
	private String getTfDt(){
		try {
			Document doc_tf_dt = Jsoup.connect("http://www.typhoon.gov.cn/").timeout(5000).get();
			String tfdt =doc_tf_dt.select(".typhoon_warning_home").select(".font14bold").select(".fontyahei").text();
			return tfdt;
		} catch (IOException e) {
			Log.e("TyphoonAction.getTfDt", e);
		}
		return "";
	}
	
	private String getZyTfDt(){
		try {
			String rooturl = "http://www.weather.gov.cn/publish/typhoon/typhoon_new.htm";
			Document doc_tf_dt = Jsoup.connect(rooturl).timeout(10000).get();
			String title = doc_tf_dt.select(".number").text().substring(0,5)+doc_tf_dt.select(".ctitle").select("span").get(1).text()+"\n";
			Elements trs = doc_tf_dt.select(".writing").select("table").select("tbody").select("tr");
			for(int i=0;i<trs.size();i++){
				String td1 = trs.get(i).select("td").get(0).text();
				String td2 = trs.get(i).select("td").get(1).text()+"\n";
				title = title +td1 +td2;
			}
			return title;
		} catch (IOException e) {
			Log.e("TyphoonAction.getZyTfDt", e);
		}
		return null;
	}
	
	private String getHnTfDt(){
		try {
			String rooturl = "http://typhoon.hainan.gov.cn/";
			Document doc_tf_dt = Jsoup.connect(rooturl).timeout(5000).get();
			String url = rooturl+doc_tf_dt.select(".S_cloud6").select(".pp260").get(0).select("a").attr("href");
			Document yjdoc = Jsoup.connect(url).timeout(5000).get();
			String tfdt =yjdoc.select(".Detailed").text();
			return tfdt;
		} catch (IOException e) {
			Log.e("TyphoonAction.getHnTfDt", e);
		}
		return null;
	}
	
	public static void main(String args[]) { 
		TyphoonAction objTyphoonAction = new TyphoonAction();
		Gson gson = new Gson();
		System.out.println(gson.toJson(objTyphoonAction.getTyphoon()));
		objTyphoonAction.pushMessage();
	}
	
	private void pushTfYjMsg(String yjContent){
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
				objPushMessagesVO.setDescription("中央台"+publishTime.substring(5)+"发布");
				
				//设置为内容，启动首页
				objPushCustomContentVO.setActKey(SeaConstant.pushTypeMeg);
				objPushCustomContentVO.setActValue(SeaConstant.pushMegAct_SI);
				objPushMessagesVO.setCustom_content(gson.toJson(objPushCustomContentVO));
				
				String josn = gson.toJson(objPushMessagesVO);
				ChannelClient.pushBroadcastMessage(josn);
			}
		}
	}
	
	private void pushMessage(){
		PushMessagesVO objPushMessagesVO = new PushMessagesVO();
		PushCustomContentVO objPushCustomContentVO = new PushCustomContentVO();
		Gson gson = new Gson();
		objPushMessagesVO.setTitle("台风“凤凰”生成");
		objPushMessagesVO.setDescription("热带低压于今天（18日）凌晨发展为台风“凤凰”");
		//设置为内容，启动首页
		objPushCustomContentVO.setActKey(SeaConstant.pushTypeMeg);
		objPushCustomContentVO.setActValue(SeaConstant.pushMegAct_SI);
		objPushMessagesVO.setCustom_content(gson.toJson(objPushCustomContentVO));
		String josn = gson.toJson(objPushMessagesVO);
		System.out.print(josn);
		ChannelClient.pushBroadcastMessage(josn);
	}
}
