package com.sea.weather.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.sea.weather.task.AllDateTask;
import com.sea.weather.task.CoastTask;
import com.sea.weather.task.GdTask;
import com.sea.weather.task.HlTextTask;
import com.sea.weather.task.HnTask;
import com.sea.weather.task.NhTask;
import com.sea.weather.task.OceanTask;
import com.sea.weather.task.OffshoreTask;
import com.sea.weather.task.TideTask;

public class TimerHelp {

	public static void startTask(){
		
		GrabTask objGrabTask = new GrabTask();
		Timer timer = new Timer();
		timer.schedule(objGrabTask, 1000, 587000);  
		
		//新闻定时器
		RssNewsTask objRssNewsTask = new RssNewsTask();
		Timer rssTimer = new Timer();
		rssTimer.schedule(objRssNewsTask, 5990, 599000);  
		
		CoastTask objCoastTask = new CoastTask();
		Timer coastTimer = new Timer();
		coastTimer.schedule(objCoastTask, 6130, 613000);  
		
		OffshoreTask objOffshoreTask = new OffshoreTask();
		Timer offshoreTimer = new Timer();
		offshoreTimer.schedule(objOffshoreTask, 6610, 661000); 
		
		OceanTask objOceanTask = new OceanTask();
		Timer oceanTimer = new Timer();
		oceanTimer.schedule(objOceanTask, 7270,727000);
		
		GdTask objGdTask = new GdTask();
		Timer gdTimer = new Timer();
		gdTimer.schedule(objGdTask, 7690,769000);
		
		HnTask objHnTask = new HnTask();
		Timer hnTimer = new Timer();
		hnTimer.schedule(objHnTask, 8230, 823000);
		
		NhTask objNhTask = new NhTask();
		Timer nhTimer = new Timer();
		nhTimer.schedule(objNhTask, 8630,863000);
		
		AllDateTask objAllDateTask = new AllDateTask();
		Timer allDateTimer = new Timer();
		allDateTimer.schedule(objAllDateTask, 9290,929000);
		
		HlTextTask objHlTextTask = new HlTextTask();
		Timer hlTextTaskTimer = new Timer();
		hlTextTaskTimer.schedule(objHlTextTask, 9970,997000);
		
		TimerUtil objTimerUtil = new TimerUtil();
		TideTask objTideTask = new TideTask();
		objTimerUtil.startMyTask(objTideTask, 23, 30);
		
	}
}
