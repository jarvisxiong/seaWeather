package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.TideLoadAction;
import com.sea.weather.utils.Log;

public class TideTask extends TimerTask{

	@Override
	public void run() {
		TideLoadAction objTideLoadAction =new TideLoadAction();
		try {
			objTideLoadAction.load7Date();
		} catch (Exception e) {
			Log.e("TideTask.run first exception", e);
			try {
				objTideLoadAction.load7Date();
				Log.e("TideTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("TideTask.run two exception", e2);
			}
		}
		
	}

}
