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
import com.sea.weather.utils.Cache;
import com.sea.weather.utils.CacheDate;
import com.sea.weather.utils.Cachekey;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssNewAction {

	public static final int urlSize = 20;
	
	private Gson gson =new Gson();
	
	public static void main(String args[]) throws IllegalArgumentException, IOException, FeedException { 
		RssNewAction objRssNewAction = new RssNewAction();
		 URL feedurl = objRssNewAction.initUrl().get(20);
		System.out.print(objRssNewAction.gradNews());
		for(int i=0;i<objRssNewAction.gradRssRewsUrl(feedurl).getLisRssNewsVO().size();i++){
			System.out.println(objRssNewAction.gradRssRewsUrl(feedurl).getLisRssNewsVO().get(i).getTitle());
		}
	}
	
	
	
	private List<URL> initUrl() throws MalformedURLException {
		List<URL> lisUrl = new ArrayList<URL>();

			/** 网易RSS 开始  */
			//网易头条 0
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_newstop.xml"));
			//国内新闻 1
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_gn.xml"));
			//国际新闻 2
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_gj.xml"));
			//军事新闻 3
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_war.xml"));
			
			/** 网易RSS 结束  */
			
			/** 百度RSS 开始  */
			//国内焦点 4
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss"));
			//国际焦点 5
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=internews&tn=rss"));
			//军事焦点 6
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=mil&tn=rss"));
			//互联网焦点 7
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=internet&tn=rss"));
			//体育焦点 8
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=sportnews&tn=rss"));
			
			//国内最新 9
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=civilnews&tn=rss"));
			//国际最新 10
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=internews&tn=rss"));
			//军事最新 11
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=mil&tn=rss"));
			//互联网最新 12
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=internet&tn=rss"));
			//体育最新 13
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=sportnews&tn=rss"));
			/** 百度RSS 结束  */
			
			/** 新浪RSS 开始  */
			
			//国内要闻 14
			lisUrl.add(new URL("http://rss.sina.com.cn/news/china/focus15.xml"));
			//国际要闻 15
			lisUrl.add(new URL("http://rss.sina.com.cn/news/world/focus15.xml"));
			//社会新闻 16
			lisUrl.add(new URL("http://rss.sina.com.cn/news/society/focus15.xml"));
			//独家 17
			lisUrl.add(new URL("http://rss.sina.com.cn/blog/index/exc.xml"));
			
			/** 新浪RSS 结束  */
			
			/** 腾讯Rss 开始*/
			//国内要闻 18
			lisUrl.add(new URL("http://news.qq.com/newsgn/rss_newsgn.xml"));
			//国际要闻 19
			lisUrl.add(new URL("http://news.qq.com/newsgj/rss_newswj.xml"));
			//社会要闻 20
			lisUrl.add(new URL("http://news.qq.com/newssh/rss_newssh.xml"));
			/** 腾讯Rss 结束*/

		return lisUrl;
	}
	
	public String gradNews() throws IOException, IllegalArgumentException, FeedException{
			 RssNewsListVO objRssNewsListVO = null;
			 for(int i=0;i<18;i++){
				if(objRssNewsListVO==null||(objRssNewsListVO!=null&&objRssNewsListVO.getLisRssNewsVO().size()==0)){
					objRssNewsListVO =  gradRssRewsUrl(initUrl().get(CacheDate.getUrlIndex()));
				}else{
					break;
				}
			 }
			 String strReturn = gson.toJson(objRssNewsListVO);
			 gradVersionNews(objRssNewsListVO);
             return strReturn;
	}
	
	private void gradVersionNews(RssNewsListVO objRssNewsListVO){
		List<RssNewsVO>  lisRssNewsVO = new ArrayList<RssNewsVO>();
		RssNewsVO objRssNewsVO =new RssNewsVO();
		lisRssNewsVO = objRssNewsListVO.getLisRssNewsVO();
		objRssNewsVO.setTitle("海洋天气1.5版发布了");
		objRssNewsVO.setDescriptionValue("新版特性:"
				+ "\n1.增加中国沿岸34个海区天气，包括浙江，福建，山东等地方；"
				+ "\n2.增加中国近海18个海区天气预报，包括渤海等;"
				+ "\n3.修改台风天气布局，方便使用;"
				+ "\n获取方式:"
				+ "\n1.点击该新闻，复制地址.访问海洋天气首页，点击图片下载;"
				+ "\n2.访问百度移动应用，搜索海洋天气，下载最新版;");
		objRssNewsVO.setLink("http://readread.duapp.com/sea/Update.jsp");
		objRssNewsVO.setPublishedDate(new Date());
		lisRssNewsVO.add(0, objRssNewsVO);
		objRssNewsListVO.setLisRssNewsVO(lisRssNewsVO);
		Cache.putValue(Cachekey.rsskey_5, gson.toJson(objRssNewsListVO));
	}

	private RssNewsListVO gradRssRewsUrl(URL feedurl) throws IOException, FeedException {
		RssNewsListVO objRssNewsListVO = new RssNewsListVO();
		URLConnection uc = feedurl.openConnection();
		 uc.setConnectTimeout(5000);
		 uc.setReadTimeout(5000);
		 SyndFeedInput input = new SyndFeedInput();
		 SyndFeed feed = input.build(new XmlReader(uc)); 
		 List<?> entries = feed.getEntries(); 
		 Date dateNow=new Date();
		 Date date=new Date(dateNow.getTime() - 48*60*60*1000);
		 
		 List<RssNewsVO>  lisRssNewsVO = new ArrayList<RssNewsVO>();
		
		 
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
		 return objRssNewsListVO;
	}
	
	private String rmHtml(String inputStr){
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
