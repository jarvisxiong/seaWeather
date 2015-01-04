package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.HnWeatherAction;
import com.sea.weather.utils.Log;

public class HnTask extends TimerTask{

	@Override
	public void run() {
		HnWeatherAction objHnWeatherAction = new HnWeatherAction();
		try{
			objHnWeatherAction.loadHnCache();
		}catch(Exception e){
			Log.e("HnTask.run first exception", e);
			try {
				objHnWeatherAction.loadHnCache();
				Log.e("HnTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("HnTask.run two exception", e2);
			}
		}
	}

}
