package com.sea.weather.utils;

import java.util.TimerTask;

public class GrabTask extends TimerTask{
	@Override
	public void run() {
		CacheDate.loadDataSource();
	}

}
