package com.sea.weather.date.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.sea.weather.date.model.RssNewsListVO;
import com.sea.weather.date.model.RssNewsVO;
import com.sea.weather.utils.CacheDate;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssNewAction {

	
	
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, FeedException { 
		System.out.print(gradNews());
	}
	
	
	
	private static List<URL> initUrl() throws MalformedURLException {
		List<URL> lisUrl = new ArrayList<URL>();

			/** 网易RSS 开始  */
			//网易头条 0
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_newstop.xml"));
			//深度新闻 1
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_hotnews.xml"));
			//评论新闻 2
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_newsspecial.xml"));
			//国内新闻 3
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_gn.xml"));
			//国际新闻 4
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_gj.xml"));
			//军事新闻 5
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_war.xml"));
			
			/** 网易RSS 结束  */
			
			/** 百度RSS 开始  */
			//国内焦点 6
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss"));
			//国际焦点 7
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=internews&tn=rss"));
			//军事焦点 8
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=mil&tn=rss"));
			//互联网焦点 9
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=internet&tn=rss"));
			//体育焦点 10
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=sportnews&tn=rss"));
			
			//国内最新 11
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=civilnews&tn=rss"));
			//国际最新 12
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=internews&tn=rss"));
			//军事最新 13
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=mil&tn=rss"));
			//互联网最新 14
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=internet&tn=rss"));
			//体育最新 15
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=sportnews&tn=rss"));
			/** 百度RSS 结束  */
			
			/** 新浪RSS 开始  */
			//新闻要闻 16
			lisUrl.add(new URL("http://rss.sina.com.cn/news/marquee/ddt.xml"));
			//国内要闻 17
			lisUrl.add(new URL("http://rss.sina.com.cn/news/china/focus15.xml"));
			//国际要闻 18
			lisUrl.add(new URL("http://rss.sina.com.cn/news/world/focus15.xml"));
			//社会新闻 19
			lisUrl.add(new URL("http://rss.sina.com.cn/news/society/focus15.xml"));
			//时政要闻 20
			lisUrl.add(new URL("http://rss.sina.com.cn/news/china/politics15.xml"));
			//独家 21
			lisUrl.add(new URL("http://rss.sina.com.cn/blog/index/exc.xml"));
			
			/** 新浪RSS 结束  */
			

		return lisUrl;
	}
	
	public static String gradNews() throws IOException, IllegalArgumentException, FeedException{
			 URL feedurl = initUrl().get(CacheDate.getUrlIndex());
			 URLConnection uc = feedurl.openConnection();
			 SyndFeedInput input = new SyndFeedInput();   
             SyndFeed feed = input.build(new XmlReader(uc)); 
             List<?> entries = feed.getEntries(); 
             Date dateNow=new Date();
             Date date=new Date(dateNow.getTime() - 48*60*60*1000);
             RssNewsListVO objRssNewsListVO = new RssNewsListVO();
             List<RssNewsVO>  lisRssNewsVO = new ArrayList<RssNewsVO>();
             Gson gson = new Gson();
             
             //读取一个源，将其放到list里面
             for (int i = 0; i < entries.size(); i++){   
                SyndEntry entry = (SyndEntry) entries.get(i);   
				if (entry != null && entry.getPublishedDate() != null
						&& entry.getPublishedDate().after(date)) {
					 RssNewsVO objRssNewsVO = new RssNewsVO();
					 objRssNewsVO.setTitle(entry.getTitle().trim());
					 objRssNewsVO.setDescriptionValue(rmHtml(entry.getDescription().getValue()));
					 objRssNewsVO.setLink(entry.getLink());
					 objRssNewsVO.setPublishedDate(entry.getPublishedDate());
					 lisRssNewsVO.add(objRssNewsVO);
                 }
             }
             //设置RSSlist
             objRssNewsListVO.setLisRssNewsVO(lisRssNewsVO);
             objRssNewsListVO.setRssGrabTime(dateNow);
            
             return gson.toJson(objRssNewsListVO);
		
	}
	
	private static String rmHtml(String inputStr){
		String outStr = inputStr.replaceAll("\\&[a-zA-Z]{1,10};", "")//去除类似&lt; &gt; &nbsp;的字串 
                .replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "") //去除开始标签及没有结束标签的标签 
                .replaceAll("</[a-zA-Z]+[1-9]?>", "")//去除结束标签 
                .trim(); 
		if(outStr.indexOf("...")!=-1){
			outStr = outStr.substring(0,outStr.indexOf("...")+3).replaceAll("\n", "");
		}else if(outStr.indexOf("\n")!=-1){
			outStr = outStr.substring(0,outStr.indexOf("\n"));
		}
		return outStr;
	}
}
