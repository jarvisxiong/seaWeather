package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.CoastWeatherAction;
import com.sea.weather.utils.Log;

public class CoastTask  extends TimerTask{

	@Override
	public void run() {
		CoastWeatherAction objCoastWeatherAction = new CoastWeatherAction();
		try {
			objCoastWeatherAction.loadCoastCache();
		} catch (Exception e) {
			Log.e("CoastTask.run first exception", e);
			try {
				objCoastWeatherAction.loadCoastCache();
				Log.e("CoastTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("CoastTask.run two exception", e2);
			}
		}
	}

}
