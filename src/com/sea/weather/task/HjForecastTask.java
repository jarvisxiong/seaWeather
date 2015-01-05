package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.HjForecastAction;
import com.sea.weather.utils.Log;

public class HjForecastTask extends TimerTask{

	@Override
	public void run() {
		HjForecastAction objHjForecastAction = new HjForecastAction();
		try {
			objHjForecastAction.loadHjCache();
		} catch (Exception e) {
			Log.e("HjForecastTask.run first exception", e);
			try {
				objHjForecastAction.loadHjCache();
				Log.e("HjForecastTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("HjForecastTask.run two exception", e2);
			}
		}
	}

}
