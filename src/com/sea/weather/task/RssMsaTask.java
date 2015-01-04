package com.sea.weather.task;

import java.util.TimerTask;

import com.sea.weather.date.nmc.action.RssMsaAction;
import com.sea.weather.utils.Log;

public class RssMsaTask extends TimerTask{

	@Override
	public void run() {
		RssMsaAction objRssMsaAction = new RssMsaAction();
		try {
			objRssMsaAction.loadJgRss();
			objRssMsaAction.loadTgRss();
		} catch (Exception e) {
			Log.e("RssMsaTask.run first exception", e);
			try {
				objRssMsaAction.loadJgRss();
				objRssMsaAction.loadTgRss();
				Log.e("RssMsaTask.run two sucess", e);
			} catch (Exception e2) {
				Log.e("RssMsaTask.run two exception", e2);
			}
		}
	}

}
