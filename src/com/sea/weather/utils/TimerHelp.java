package com.sea.weather.utils;

import java.util.Timer;

import com.sea.weather.task.AllDateTask;
import com.sea.weather.task.CoastTask;
import com.sea.weather.task.ForecastTask;
import com.sea.weather.task.GaleWarningTask;
import com.sea.weather.task.GdTask;
import com.sea.weather.task.HjForecastTask;
import com.sea.weather.task.HlTextTask;
import com.sea.weather.task.HnTask;
import com.sea.weather.task.NhTask;
import com.sea.weather.task.OceanTask;
import com.sea.weather.task.OffshoreTask;
import com.sea.weather.task.RssMsaTask;
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
		
		GaleWarningTask objGaleWarningTask = new GaleWarningTask();
		Timer galeWarningTaskTimer = new Timer();
		galeWarningTaskTimer.schedule(objGaleWarningTask, 10310,1031000);
		
		ForecastTask objForecastTask = new ForecastTask();
		Timer forecastTaskTimer = new Timer();
		forecastTaskTimer.schedule(objForecastTask, 11090,1109000);
		
		HjForecastTask objHjForecastTask = new HjForecastTask();
		Timer hjForecastTaskTimer = new Timer();
		hjForecastTaskTimer.schedule(objHjForecastTask, 12770,1277000);
		
		RssMsaTask objRssMsaTask = new RssMsaTask();
		Timer rssMsaTaskTimer = new Timer();
		rssMsaTaskTimer.schedule(objRssMsaTask, 30230,3023000);
		
		TimerUtil objTimerUtil = new TimerUtil();
		TideTask objTideTask = new TideTask();
		objTimerUtil.startMyTask(objTideTask, 23, 30);
		
	}
}
