package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.NhWeatherAction;
import com.sea.weather.utils.Log;

public class NhTask extends TimerTask {

	@Override
	public void run() {
		NhWeatherAction objNhWeatherAction = new NhWeatherAction();
		try{
			objNhWeatherAction.loadNhCache();
		}catch(Exception e){
			Log.e("NhTask.run first exception", e);
			try {
				objNhWeatherAction.loadNhCache();
				Log.e("NhTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("NhTask.run two exception", e2);
			}
		}
	}

}
