package com.sea.weather.utils;

import java.util.TimerTask;


public class RssNewsTask extends TimerTask{

	@Override
	public void run() {
		try {
			CacheDate.loadRssNewsList();
		} catch (Exception e) {
			Log.e("RssNewsTask.run first exception", e);
			try {
				CacheDate.loadRssNewsList();
				Log.e("RssNewsTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("RssNewsTask.run two exception", e);
			}
		}
	}

}
