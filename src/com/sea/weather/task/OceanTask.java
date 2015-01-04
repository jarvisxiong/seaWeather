package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.OceanWeatherAction;
import com.sea.weather.utils.Log;

public class OceanTask extends TimerTask{
	@Override
	public void run() {
		OceanWeatherAction objOceanWeatherAction =new OceanWeatherAction();
		try {
			objOceanWeatherAction.loadOceanCache();
		} catch (Exception e) {
			Log.e("OceanTask.run first exception", e);
			try {
				objOceanWeatherAction.loadOceanCache();
				Log.e("OceanTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("OceanTask.run two exception", e2);
			}
		}
	}
}
