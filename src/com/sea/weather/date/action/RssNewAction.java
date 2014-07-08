package com.sea.weather.date.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssNewAction {

	public static void main(String args[]) { 
		RssNewAction objRssNewAction = new RssNewAction();
		objRssNewAction.gradNews();
	}
	
	public void gradNews(){
		try {
			URL feedurl = new URL("http://news.163.com/special/00011K6L/rss_newstop.xml");
			URLConnection uc = feedurl.openConnection();
			 SyndFeedInput input = new SyndFeedInput();   
             SyndFeed feed = input.build(new XmlReader(uc)); 
             List<?> entries = feed.getEntries(); 
             Date dateNow=new Date();
             Date date=new Date(dateNow.getTime() - 24*60*60*1000);
             SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             for (int i = 0; i < entries.size(); i++)   
             {   
            	
                 SyndEntry entry = (SyndEntry) entries.get(i);   
                 if(entry.getPublishedDate().after(date)){
	                 System.out.println(entry.getTitle());
	                 System.out.println(entry.getDescription().getValue());   
	                 System.out.println(entry.getLink());   
	                 System.out.println(fmt.format(entry.getPublishedDate()));   
                 }
             }   
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
