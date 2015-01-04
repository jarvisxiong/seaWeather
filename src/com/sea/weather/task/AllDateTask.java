package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.AllDateAction;
import com.sea.weather.utils.Log;

public class AllDateTask extends TimerTask{

	@Override
	public void run() {
		AllDateAction objAllDateAction = new AllDateAction();
		try {
			objAllDateAction.loadAllDateCache();
		} catch (Exception e) {
			Log.e("AllDateTask.run first exception", e);
			try {
				objAllDateAction.loadAllDateCache();
				Log.e("AllDateTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("AllDateTask.run two exception", e2);
			}
		}
	}

}
