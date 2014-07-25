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

			/** ����RSS ��ʼ  */
			//����ͷ�� 0
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_newstop.xml"));
			//�������� 1
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_gn.xml"));
			//�������� 2
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_gj.xml"));
			//�������� 3
			lisUrl.add(new URL("http://news.163.com/special/00011K6L/rss_war.xml"));
			
			/** ����RSS ����  */
			
			/** �ٶ�RSS ��ʼ  */
			//���ڽ��� 4
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss"));
			//���ʽ��� 5
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=internews&tn=rss"));
			//���½��� 6
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=mil&tn=rss"));
			//���������� 7
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=internet&tn=rss"));
			//�������� 8
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=1&class=sportnews&tn=rss"));
			
			//�������� 9
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=civilnews&tn=rss"));
			//�������� 10
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=internews&tn=rss"));
			//�������� 11
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=mil&tn=rss"));
			//���������� 12
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=internet&tn=rss"));
			//�������� 13
			lisUrl.add(new URL("http://news.baidu.com/n?cmd=4&class=sportnews&tn=rss"));
			/** �ٶ�RSS ����  */
			
			/** ����RSS ��ʼ  */
			
			//����Ҫ�� 14
			lisUrl.add(new URL("http://rss.sina.com.cn/news/china/focus15.xml"));
			//����Ҫ�� 15
			lisUrl.add(new URL("http://rss.sina.com.cn/news/world/focus15.xml"));
			//������� 16
			lisUrl.add(new URL("http://rss.sina.com.cn/news/society/focus15.xml"));
			//���� 17
			lisUrl.add(new URL("http://rss.sina.com.cn/blog/index/exc.xml"));
			
			/** ����RSS ����  */
			
			/** ��ѶRss ��ʼ*/
			//����Ҫ�� 18
			lisUrl.add(new URL("http://news.qq.com/newsgn/rss_newsgn.xml"));
			//����Ҫ�� 19
			lisUrl.add(new URL("http://news.qq.com/newsgj/rss_newswj.xml"));
			//���Ҫ�� 20
			lisUrl.add(new URL("http://news.qq.com/newssh/rss_newssh.xml"));
			/** ��ѶRss ����*/

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
		objRssNewsVO.setTitle("��������1.5�淢����");
		objRssNewsVO.setDescriptionValue("�°�����:"
				+ "\n1.�����й��ذ�34�����������������㽭��������ɽ���ȵط���"
				+ "\n2.�����й�����18����������Ԥ��������������;"
				+ "\n3.�޸�̨���������֣�����ʹ��;"
				+ "\n��ȡ��ʽ:"
				+ "\n1.��������ţ����Ƶ�ַ.���ʺ���������ҳ�����ͼƬ����;"
				+ "\n2.���ʰٶ��ƶ�Ӧ�ã����������������������°�;");
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
		
		 
		 //��ȡһ��Դ������ŵ�list����
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
		 //����RSSlist
		 objRssNewsListVO.setLisRssNewsVO(lisRssNewsVO);
		 objRssNewsListVO.setRssGrabTime(dateNow);
		 return objRssNewsListVO;
	}
	
	private String rmHtml(String inputStr){
		String outStr = inputStr.replaceAll("\\&[a-zA-Z]{1,10};", "")//ȥ������&lt; &gt; &nbsp;���ִ� 
                .replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "") //ȥ����ʼ��ǩ��û�н�����ǩ�ı�ǩ 
                .replaceAll("</[a-zA-Z]+[1-9]?>", "")//ȥ��������ǩ 
                .trim(); 
		if(outStr.indexOf("...")!=-1){
			outStr = outStr.substring(0,outStr.indexOf("...")+3).replaceAll("\n", "");
		}else if(outStr.indexOf("\n")!=-1){
			outStr = outStr.substring(0,outStr.indexOf("\n"));
		}
		return outStr;
	}
}
