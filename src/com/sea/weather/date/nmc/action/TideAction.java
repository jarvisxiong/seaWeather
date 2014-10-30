package com.sea.weather.date.nmc.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.sea.weather.date.nmc.model.PortItemVO;
import com.sea.weather.db.dao.TideDAO;

public class TideAction {
	private final static Map<String,String> map = new HashMap<String,String>();
	private List<PortItemVO> portcode = new ArrayList<PortItemVO>();
	public Document getTide(){
		Document dc_tide=null;
		try {
			dc_tide = Jsoup.connect("http://www.chinaports.com/chaoxi?changed=0&state=0&country=0-0&province=0-0-0&portcode=0-0-0-0&date=2014-10-28").timeout(5000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dc_tide;
	}
	
	private List<String> getText(){
		Document dc_tide = getTide();
		String url = "http://www.chinaports.com/chaoxi?changed=0&state=0&country=0-0&province=";
		Elements e =  dc_tide.select("#provinceLi").select("select").select("option");
		
		List<String> lisurl = new ArrayList<String>();
		List<PortItemVO> province = new ArrayList<PortItemVO>();
		for(int i=0;i<e.size();i++){
			if(!"0".equals(e.get(i).attr("value"))){
				PortItemVO objPortItemVO = new PortItemVO();
				map.put(e.get(i).attr("value"), e.get(i).text());
				objPortItemVO.setCode(e.get(i).attr("value"));
				objPortItemVO.setName(e.get(i).text());
				province.add(objPortItemVO);
				String url1=url+e.get(i).attr("value")+"&portcode="+e.get(i).attr("value")+"-0&date=2014-10-28";
				lisurl.add(url1);
			}
		}
		TideDAO objTideDAO = new TideDAO();
		try {
			objTideDAO.bathInsertProvince(province);
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		return lisurl;
	}
	
	private String getText2() throws Exception{
		List<String> lisurl =getText();
		Document dc_tide = null;
		
		for(int i=0;i<lisurl.size();i++){
			dc_tide = Jsoup.connect(lisurl.get(i)).get();
			getMap(dc_tide);
		}
		Gson gson = new Gson();
		TideDAO objTideDAO = new TideDAO();
		try {
			objTideDAO.bathInsertPortcode(portcode);
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		return gson.toJson(map);
	}
	
	private void getMap(Document dc_tide){
		
		Elements e =  dc_tide.select(".select-4").select("option");
		for(int i=0;i<e.size();i++){
			if(!"0".equals(e.get(i).attr("value"))){
				PortItemVO objPortItemVO = new PortItemVO();
				map.put(e.get(i).attr("value"), e.get(i).text());
				objPortItemVO.setCode(e.get(i).attr("value"));
				objPortItemVO.setName(e.get(i).text());
				portcode.add(objPortItemVO);
			}
		}
	}
	
	public static void main(String args[]) throws Exception { 
		TideAction objTideAction = new TideAction();
		objTideAction.getText2();
		String str ="{'0-0-10-42':'东沙岛（东沙群岛）','0-0-10-41':'下泊','0-0-10-40':'流沙','0-0-3-0':'东风港','0-0-3-1':'湾湾沟口','0-0-3-2':'东营港','0-0-3-3':'潍坊港','0-0-10-30':'上川岛（三洲湾）','0-0-10-32':'海陵山岛（闸坡）','0-0-3-9':'北隍城岛','0-0-10-31':'北津','0-0-3-8':'砣矶岛','0-0-10-34':'博贺','0-0-3-7':'南长山岛','0-0-10-33':'电白','0-0-3-6':'蓬莱','0-0-10-36':'茂石化（水东港）','0-0-3-5':'龙口','0-0-10-35':'西葛','0-0-3-4':'莱州港','0-0-10-38':'湛江','0-0-10-37':'硇洲岛（北港）','0-0-10-39':'海安','0-0-10-25':'灯笼山','0-0-10-24':'大万山','0-0-10-23':'东澳岛','0-0-10-22':'珠海（九洲港）','0-0-10-21':'珠海（香洲）','0-0-10-20':'横门','0-0-10-29':'井岸（白蕉）','0-0-10-28':'横山','0-0-10-27':'珠海港','0-0-10-26':'三灶岛','0-0-10-12':'内伶仃岛','0-0-10-11':'桂山岛','0-0-10-14':'深圳机场（油码头）','0-0-10-13':'舢舨洲','0-0-10-10':'蛇口(赤湾)','0-0-11-0':'澳门','0-0-10-19':'北街','0-0-10-16':'海沁','0-0-10-15':'南沙（水牛头）','0-0-10-18':'广州','0-0-10-17':'黄埔','0-0-7-2':'帮门','0-0-7-3':'罗源迹头','0-0-7-0':'三沙','0-0-7-1':'赛岐','0-0-7-6':'福清湾（松下港）','0-0-7-7':'竹屿','0-0-7-4':'黄岐','0-0-7-5':'马尾','0-0-7-8':'郎官','0-0-7-9':'闽江口（川石）','6-0-0':'其他','0-0-12-13':'双子礁（南沙群岛）','0-0-13-3':'龙门','0-0-12-14':'永暑礁（南沙群岛）','0-0-13-4':'企沙','0-0-13-1':'涠洲岛','0-0-13-2':'北海','0-0-13-7':'珍珠港','0-0-12-10':'新盈','0-0-12-11':'马村港','0-0-13-5':'炮台角','0-0-6-19':'石浦','0-0-12-12':'永兴岛（西沙群岛）','0-0-13-6':'防城港','0-0-6-18':'西泽','0-0-12-8':'东方（八所）','0-0-6-17':'梅山','0-0-12-9':'洋浦','0-0-6-16':'崎头角','0-0-12-6':'三亚','0-0-6-15':'北仑港','0-0-12-7':'莺歌海','0-0-6-14':'镇海','0-0-12-4':'新村','0-0-6-13':'宁波','0-0-12-5':'牙笼港','0-0-6-12':'沥港','0-0-12-2':'清澜','0-0-6-11':'定海','0-0-12-3':'博鳌','0-0-6-10':'沈家门','0-0-10-9':'大鹏湾（盐田港）','0-0-10-8':'大亚湾','0-0-10-7':'惠州港','0-0-10-6':'马鞭洲（广石化）','0-0-10-5':'汕尾','0-0-10-4':'甲子','0-0-15':'三沙市','0-0-13':'广西','0-0-14':'其他','0-0-11':'澳门','0-0-12':'海南','0-0-6-29':'温州','0-0-10':'广东','0-0-6-26':'东门村','0-0-6-25':'海门港（白沙）','0-0-6-28':'大门岛(黄大岙)','0-0-6-27':'坎门','0-0-6-22':'鱼山','0-0-6-21':'健跳','0-0-7-10':'闽江口（琯头）','0-0-6-24':'海门','0-0-6-23':'下大陈','0-0-7-14':'梯吴','0-0-7-13':'秀屿','0-0-0-10':'长兴岛','0-0-7-12':'三江口','0-0-0-11':'鲅鱼圈','0-0-6-20':'旗门港','0-0-7-11':'平潭','0-0-7-18':'深沪港','0-0-7-17':'泉州（石湖）','0-0-7-16':'后渚','0-0-7-15':'崇武','0-0-0-16':'团山角','0-0-0-17':'芷锚湾','0-0-7-19':'围头','0-0-0-12':'营口','0-0-0-13':'老北河口','0-0-0-14':'锦州港（笔架山）','0-0-0-15':'菊花岛','0-0-7-20':'石井','0-0-7-21':'厦门','0-0-7-23':'东山','0-0-7-22':'石码','0-0-14-0':'白龙尾','0-0-14-1':'钓鱼岛','0-0-15-0':'中沙群岛－黄岩岛','0-0-10-0':'南澳岛（云澳湾）','0-0-10-1':'潮州港（三百门）','0-0-10-2':'汕头','0-0-10-3':'海门（广东）','0-0-12-1':'铺前','0-0-12-0':'海口（秀英）','0-0-5-2':'中浚','0-0-5-3':'高桥','0-0-5-4':'吴淞','0-0-5-5':'黄浦公园','6-0-0-0':'南极—长城站','0-0-5-6':'芦潮港（南汇嘴）','0-0-5-7':'金山嘴','0-0-3-18':'青岛','0-0-3-17':'女岛港','0-0-3-16':'千里岩','0-0-3-15':'乳山口','0-0-3-19':'黄岛','0-0-3-10':'烟台','0-0-3-14':'张家埠','0-0-3-13':'石岛','0-0-1-7':'黄骅港（一期煤码头）','0-0-3-12':'成山角','0-0-1-6':'埕口外海','0-0-3-11':'威海','0-0-1-2':'七里海（新开口）','0-0-1-3':'京唐港','0-0-1-4':'曹妃甸','0-0-1-5':'岐口','0-0-1-0':'山海关','0-0-1-1':'秦皇岛','0-0-5-1':'崇明（南堡镇）','0-0-5-0':'佘山','0-0-6-31':'南麂山','0-0-6-30':'瑞安','0-0-13-0':'铁山港（石头埠）','0-0-9-1':'基隆','0-0-9-0':'马公','0-0-9-3':'下港','0-0-9-2':'高雄','0-0-3-21':'岚山港','0-0-9-4':'乌石','0-0-3-20':'日照港(石臼所)','0-0-0-9':'葫芦岛','0-0-0-8':'金县','0-0-0-7':'旅顺新港','0-0-6-9':'西码头','0-0-6-6':'海黄山','0-0-6-5':'滩浒','0-0-6-8':'岱山','0-0-6-7':'长涂','0-0-6-2':'嵊山','0-0-6-1':'大戢山','0-0-6-4':'澉浦','0-0-6-3':'乍浦','0-0-4-7':'洋口港','0-0-4-8':'吕四','0-0-4-9':'天生港','0-0-4-3':'射阳河口','0-0-4-4':'新洋港','0-0-4-5':'大丰港','0-0-4-6':'弶港','0-0-2':'天津','0-0-3':'山东','0-0-4':'江苏','0-0-2-0':'塘沽','0-0-5':'上海','0-0-6':'浙江','0-0-7':'福建','0-0-8':'香港','0-0-9':'台湾','0-0-4-2':'滨海港','0-0-4-1':'燕尾','0-0-4-0':'连云港','0-0-0':'辽宁','0-0-1':'河北','0-0-6-0':'绿华山','0-0-0-0':'丹东','0-0-0-1':'丹东新港','0-0-0-2':'石山子','0-0-0-3':'大鹿岛','0-0-0-4':'小长山岛','0-0-0-5':'大窑湾(南大圈)','0-0-0-6':'大连(老虎滩)','0-0-8-0':'将军澳','0-0-8-1':'香港'}";
		str.replaceAll("'", "\"");
		Gson gson = new Gson();
		Map<String,String> map = gson.fromJson(str, Map.class);
		System.out.println(map.get("0-0-0"));
	}
}
 