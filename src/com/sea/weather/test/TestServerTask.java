package com.sea.weather.test;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.sea.weather.date.model.AllTfAreaVO;


public class TestServerTask extends TimerTask{

	@Override
	public void run() {
		testCacheDate();
	}

	public static void main(String args[]) { 
		// TODO Auto-generated method stub
		testCacheDate();
    }

	private static void testCacheDate() {
		try {
			Document doc_test_date = Jsoup.connect(
					"http://readread.duapp.com/CacheAllTyAreaDate.jsp")
					.get();
			String str = doc_test_date.text();
			Gson gson = new Gson(); 
			AllTfAreaVO objAllTfAreaVO = gson.fromJson(str, AllTfAreaVO.class);
			Date grabTime = objAllTfAreaVO.getGrabTime();
			Date now = new Date();
			int time = 1300000;
			if(now.getTime() - grabTime.getTime()>time){
				System.out.println("出现异常:"+(now.getTime() - grabTime.getTime()));
			}else{
				System.out.println("正常"+now);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
}
