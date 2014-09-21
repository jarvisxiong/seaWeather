package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.GdWeatherAction;
import com.sea.weather.utils.Log;

public class GdTask extends TimerTask{

	@Override
	public void run() {
		GdWeatherAction objGdWeatherAction = new GdWeatherAction();
		try {
			objGdWeatherAction.loadGdCache();
		} catch (Exception e) {
			Log.e("GdTask.run first exception", e);
			try {
				objGdWeatherAction.loadGdCache();
				Log.e("GdTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("GdTask.run two exception", e);
			}
		}
	}

}
