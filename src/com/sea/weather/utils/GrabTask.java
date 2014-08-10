package com.sea.weather.utils;

import java.util.TimerTask;

import com.sea.weather.date.action.SeaWeatherDateAction;

public class GrabTask extends TimerTask{
	@Override
	public void run() {
		SeaWeatherDateAction objSeaWeatherDateAction = new SeaWeatherDateAction();
		try {
			objSeaWeatherDateAction.loadAllTfAreaCache();
		} catch (Exception e) {
			Log.e("GrabTask.run first exception", e);
			try {
				objSeaWeatherDateAction.loadAllTfAreaCache();
				Log.e("GrabTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("GrabTask.run two Exception", e2);
			}

		}
	}
}
