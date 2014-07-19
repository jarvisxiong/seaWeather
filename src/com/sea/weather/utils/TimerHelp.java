package com.sea.weather.utils;

import java.util.Timer;

import com.sea.weather.task.CoastTask;
import com.sea.weather.task.OffshoreTask;

public class TimerHelp {

	public static void startTask(){
		
		GrabTask objGrabTask = new GrabTask();
		Timer timer = new Timer();
		timer.schedule(objGrabTask, 1000, 587000);  
		
		//新闻定时器
		RssNewsTask objRssNewsTask = new RssNewsTask();
		Timer rssTimer = new Timer();
		rssTimer.schedule(objRssNewsTask, 5870, 997000);  
		
		CoastTask objCoastTask = new CoastTask();
		Timer coastTimer = new Timer();
		coastTimer.schedule(objCoastTask, 7690, 769000);  
		
		OffshoreTask objOffshoreTask = new OffshoreTask();
		Timer offshoreTimer = new Timer();
		offshoreTimer.schedule(objOffshoreTask, 9970, 863000);  
	}
}
