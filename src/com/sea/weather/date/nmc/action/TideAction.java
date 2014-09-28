package com.sea.weather.date.nmc.action;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TideAction {

	public String getTide(){
		Document dc_tide=null;
		try {
			dc_tide = Jsoup.connect("http://ocean.cnss.com.cn/index.php?m=resource&c=tide&a=get_tide_data&portid=181&date=2014-09-26").timeout(5000).post();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return dc_tide.text();
	}
	
	public static void main(String args[]) { 
		TideAction objTideAction = new TideAction();
		System.out.println(objTideAction.getTide());
	}
}
