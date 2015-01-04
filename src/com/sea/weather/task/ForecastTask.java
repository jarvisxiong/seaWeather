package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.ForecastAction;
import com.sea.weather.utils.Log;

public class ForecastTask extends TimerTask{

	@Override
	public void run() {
		ForecastAction objForecastAction = new ForecastAction();
		try {
			objForecastAction.loadGgCache();
		} catch (Exception e) {
			Log.e("ForecastTask.run first exception", e);
			try {
				objForecastAction.loadGgCache();
				Log.e("ForecastTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("ForecastTask.run two exception", e2);
			}
		}
	}

}
