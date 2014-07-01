package com.sea.weather.utils;

import java.util.Date;
import java.util.TimerTask;

public class GrabTask extends TimerTask{
	@Override
	public void run() {
		try {
			CacheDate.loadDataSource();
		} catch (Exception e) {
			System.out.println("do GrabTask Exception first:"
					+ e.getClass().getName() + ",time:" + new Date());
			try {
				CacheDate.loadDataSource();
			} catch (Exception e2) {
				System.out.println("do GrabTask Exception two:"
						+ e2.getClass().getName() + ",time:" + new Date());
			}

		}
	}
}
