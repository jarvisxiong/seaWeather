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

	private static Document doc_tf;
	private static Document doc_tf_yj;
	
	public static TyphoonVO getTyphoon(){
		
		TyphoonVO objTyphoonVO = new TyphoonVO();
		try {
			doc_tf = Jsoup.connect("http://typhoon.weather.com.cn/").timeout(5000).get();
			doc_tf_yj = Jsoup.connect("http://typhoon.weather.com.cn/alarm/index.shtml").timeout(5000).get();
		} catch (IOException e) {
			Log.e("TyphoonAction.getTyphoon IOException", e);
		}
		
		//����̨���ע
		try {
			Elements elGzTitle = doc_tf.select(".borBox").select(".blockLC");
			String strTitle = elGzTitle.select("em").get(0).text()
					.replaceAll("��", "");
			String strTime = elGzTitle.select("b").get(0).text();

			Elements elGzContent = doc_tf.select(".rbox").select("p");
			String strGzContent = "";
			for (int i = 0; elGzContent != null && i < elGzContent.size(); i++) {
				strGzContent = strGzContent + elGzContent.get(i).text();
			}

			objTyphoonVO.setGzTitle(strTitle);
			objTyphoonVO.setGzTime(strTime);
			objTyphoonVO.setGzContent(strGzContent);
		} catch (Exception e) {
			Log.e("TyphoonAction.getTyphoon Exception", e);
		}
		
		//��ȡ̨�綯̬
		String dtTitle = "̨�綯̬";
		String dtContent = getHnTfDt();
		objTyphoonVO.setDtTitle(dtTitle);
		objTyphoonVO.setDtContent(dtContent);
		
		//��ȡ̨��Ԥ��
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
				Log.e("TyphoonAction.getYfYj", e);
			}
		}
	    objTyphoonVO.setYjTitle(yjTitle);
	    objTyphoonVO.setYjContent(yjContent);
	    pushTfYjMsg(yjContent);
	}
	
	//��ʱ��Ҫ�ķ����������վ���½������������ӽ���
	private static String getTfDt(){
		try {
			Document doc_tf_dt = Jsoup.connect("http://www.typhoon.gov.cn/").timeout(5000).get();
			String tfdt =doc_tf_dt.select(".typhoon_warning_home").select(".font14bold").select(".fontyahei").text();
			return tfdt;
		} catch (IOException e) {
			Log.e("TyphoonAction.getTfDt", e);
		}
		return "";
	}
	
	private static String getHnTfDt(){
		try {
			Document doc_tf_dt = Jsoup.connect("http://typhoon.hainan.gov.cn/").timeout(5000).get();
			String tfdt =doc_tf_dt.select(".S_cloudCCC").text();
			return tfdt;
		} catch (IOException e) {
			Log.e("TyphoonAction.getHnTfDt", e);
		}
		return "";
	}
	
	public static void main(String args[]) { 
		System.out.println(getHnTfDt());
	}
	
	private static void pushTfYjMsg(String yjContent){
		String patternStr = "\\d{4}��\\d{1,2}��\\d{1,2}��\\d{1,2}ʱ"; 
		//��ȡ���ڵ�2014��07��16��10ʱ�����û��̨��Ԥ����������С��14�������뷢�����͵��߼�
		if(yjContent!=null&&yjContent.length()>14){
			String publishTime = yjContent.substring(0,14);
			//��̨��Ԥ�������һ�ν�����cacheΪ�գ��򲻵ȣ�����һ�����ͣ����ҽ�ʱ��ŵ�cache����
			//����ѭ�����������̨��Ԥ��ʱ��û�䣬��һֱ�������߼�������������,���һ���Ҫ���ϡ�2014��07��05��10ʱ����ʽ
			if(Pattern.matches(patternStr, publishTime)&&!publishTime.equals(CacheDate.getTfYjTime())){
				CacheDate.setTfYjTime(publishTime);
				PushMessagesVO objPushMessagesVO = new PushMessagesVO();
				PushCustomContentVO objPushCustomContentVO = new PushCustomContentVO();
				Gson gson = new Gson();
				objPushMessagesVO.setTitle("̨��Ԥ��");
				objPushMessagesVO.setDescription("����̨"+publishTime+"����̨��Ԥ��");
				
				//����Ϊ���ݣ�������ҳ
				objPushCustomContentVO.setActKey(SeaConstant.pushTypeMeg);
				objPushCustomContentVO.setActValue(SeaConstant.pushMegAct_SI);
				objPushMessagesVO.setCustom_content(gson.toJson(objPushCustomContentVO));
				
				String josn = gson.toJson(objPushMessagesVO);
				ChannelClient.pushBroadcastMessage(josn);
			}
		}
	}
}
