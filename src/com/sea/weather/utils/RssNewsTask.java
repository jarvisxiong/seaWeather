package com.sea.weather.utils;

import java.util.Date;
import java.util.TimerTask;


public class RssNewsTask extends TimerTask{

	@Override
	public void run() {
		try {
			CacheDate.loadRssNewsList();
		} catch (Exception e) {
			System.out.println("do RssNewsTask Exception first:"
					+ e.getClass().getName() + ",time:" + new Date());
			try {
				CacheDate.loadRssNewsList();
				System.out.println("do RssNewsTask two sucess :"
						+ e.getClass().getName() + ",time:" + new Date());
			} catch (Exception e2) {
				System.out.println("do RssNewsTask Exception two:"
						+ e2.getClass().getName() + ",time:" + new Date());
			}
		}
	}

}
