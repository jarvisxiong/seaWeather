package com.sea.weather.test;

import java.util.Timer;

public class TestTimerHelp {

public static void startTask(){
		
	TestServerTask objTestServerTask = new TestServerTask();
		Timer timer = new Timer();
		timer.schedule(objTestServerTask, 1000, 3000);  
	}
}
