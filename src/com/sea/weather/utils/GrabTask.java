package com.sea.weather.utils;

import java.util.TimerTask;

public class GrabTask extends TimerTask{
	@Override
	public void run() {
		try{
			CacheDate.loadDataSource();
		}catch(Exception e){
			System.out.println("ִ�ж�ʱ��������쳣:"+e.getClass().getName());
		}
	}

}
