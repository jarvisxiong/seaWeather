package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.OffshoreWeatherAction;
import com.sea.weather.utils.Log;

public class OffshoreTask  extends TimerTask{

	@Override
	public void run() {
		try {
			OffshoreWeatherAction.loadOffshoreCache();
		} catch (Exception e) {
			Log.e("OffshoreTask.run first exception", e);
			try {
				OffshoreWeatherAction.loadOffshoreCache();
				Log.e("OffshoreTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("OffshoreTask.run two exception", e);
			}
		}
	}

}
