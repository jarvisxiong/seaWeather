package com.sea.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
    
	public static void e(String meg,Exception e){
		  String now=dateformat1.format(new Date());
		  System.out.println("---start---");
		  System.out.println("time:"+now);
		  System.out.println("meg:"+meg);
		  System.out.println("ExceptionName:"+e.getClass().getName());
	}
}
