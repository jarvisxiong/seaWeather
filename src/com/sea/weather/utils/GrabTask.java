package com.sea.weather.utils;

import java.util.TimerTask;

public class GrabTask extends TimerTask{
	@Override
	public void run() {
		try {
			CacheDate.loadDataSource();
		} catch (Exception e) {
			Log.e("GrabTask.run first exception", e);
			try {
				CacheDate.loadDataSource();
				Log.e("GrabTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("GrabTask.run two Exception", e2);
			}

		}
	}
}
