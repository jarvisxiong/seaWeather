package com.sea.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
    
	public static void e(String msg,Exception e){
		  String now=dateformat1.format(new Date());
		  System.out.println("---start---");
		  System.out.println("time:"+now);
		  System.out.println("msg:"+msg);
		  System.out.println("ExceptionName:"+e.getClass().getName());
	}
	
	public static void i(String msg){
		String now=dateformat1.format(new Date());
		System.out.println("---start info---");
		System.out.println("time:"+now);
		System.out.println("msg:"+msg);
	}
}
