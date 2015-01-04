package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.GaleWarningAction;
import com.sea.weather.utils.Log;

public class GaleWarningTask extends TimerTask{

	@Override
	public void run() {

		GaleWarningAction objGaleWarningAction = new GaleWarningAction();
			try {
				objGaleWarningAction.loadDfCache();
			} catch (Exception e) {
				Log.e("GaleWarningTask.run first exception", e);
				try {
					objGaleWarningAction.loadDfCache();
					Log.e("GaleWarningTask.run two sucess", e);
				} catch (Exception e2) {
					Log.e("GaleWarningTask.run two exception", e2);
				}
			}
	}

}
