package com.sea.weather.utils;

import java.util.Timer;

public class TimerHelp {

	public static void startTask(){
		
		GrabTask objGrabTask = new GrabTask();
		Timer timer = new Timer();
		timer.schedule(objGrabTask, 1000, 293000);  
		
		//新闻定时器
		RssNewsTask objRssNewsTask = new RssNewsTask();
		Timer rssTimer = new Timer();
		rssTimer.schedule(objRssNewsTask, 1000, 383000);  
	}
}
