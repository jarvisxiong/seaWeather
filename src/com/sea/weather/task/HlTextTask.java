package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.HlTextWeatherAction;
import com.sea.weather.utils.Log;

public class HlTextTask extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HlTextWeatherAction objHlTextWeatherAction = new HlTextWeatherAction();
		try {
			objHlTextWeatherAction.loadHlTextWeatherVO();
		} catch (Exception e) {
			Log.e("HlTextTask.run first exception", e);
			try {
				objHlTextWeatherAction.loadHlTextWeatherVO();
				Log.e("HlTextTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("HlTextTask.run two exception", e);
			}
		}
	}

}
