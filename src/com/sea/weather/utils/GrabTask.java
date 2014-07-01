package com.sea.weather.utils;

import java.util.TimerTask;

public class GrabTask extends TimerTask{
	@Override
	public void run() {
		try{
			CacheDate.loadDataSource();
		}catch(Exception e){
			System.out.println("执行定时任务出现异常:"+e.getClass().getName());
		}
	}

}
