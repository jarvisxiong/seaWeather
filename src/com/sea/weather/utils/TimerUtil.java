package com.sea.weather.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {

	//默认时间间隔一天  
	private static long PERIOD_TIME = 24 * 60 * 60 * 1000;    
    /** 
     * 执行定时任务 
     * @param myTask 要执行的任务 
     * @param dayInterval 每隔多少天执行一次任务 
     * @param hour 每隔多少天在几点执行任务 
     * @param minute 每隔多少天在几点几分执行任务 
     * @author linshutao 
     * */  
    public void startMyTask(TimerTask myTask,int hour,int minute){  
       try{
    	Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, hour);  
        calendar.set(Calendar.MINUTE, minute);   
        calendar.set(Calendar.SECOND, 0);  
        //执行定时任务的时间      
        Date date=calendar.getTime();   
        Date now = new Date();
        long after = 0;
        //为了避免若容器启动的时间晚于定时时间，在重启容器的时候会立刻执行任务，这里进行处理  
        if (date.before(new Date())) {  
              date = this.addDay(date, 1);  
              after = date.getTime()-now.getTime();
        }else{
        	  after = date.getTime()-now.getTime();
        }
        Log.i("Timer start after "+String.valueOf(after/1000/60/60)+" hours");
        Timer timer = new Timer();  
       //任务在指定的时间开始进行重复的固定延迟执行  
        timer.schedule(myTask,after,PERIOD_TIME); 
       }catch(Exception e){
    	   Log.e("TimerUtil.startMyTask", e);
       }
    }  
      
 
     public Date addDay(Date date, int num) {  
      Calendar startDT = Calendar.getInstance();  
      startDT.setTime(date);  
      startDT.add(Calendar.DAY_OF_MONTH, num);  
      return startDT.getTime();  
     }  
	
}
