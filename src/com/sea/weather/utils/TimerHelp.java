package com.sea.weather.utils;

import java.util.Timer;

public class TimerHelp {

	public static void startTask(){
		
		GrabTask objGrabTask = new GrabTask();
		Timer timer = new Timer();
		timer.schedule(objGrabTask, 60000, 1800000);  
		
	}
}
