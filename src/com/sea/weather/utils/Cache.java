package com.sea.weather.utils;

import java.sql.SQLException;
import java.util.HashMap;

import com.sea.weather.db.dao.DbMapDAO;

public class Cache {

	private static final HashMap<String, Object> map = new HashMap<String, Object>();
	
	
	 public static Object getValue(String key){
		 Object v = map.get(key);
		 if(v==null){
			 DbMapDAO objDbMapDAO = new DbMapDAO();
			 try {
				v = objDbMapDAO.get(key);
				map.put(key, v);
			} catch (SQLException e) {
				Log.e("Cache.getValue", e);
			}
		 }
		 return v;
	 }
	 
	 public static synchronized void putValue(String key,Object obj){
		 map.put(key, obj);
		 if(obj instanceof String){
			 DbMapDAO objDbMapDAO = new DbMapDAO();
			 try {
				objDbMapDAO.put(key, (String)obj);
			} catch (SQLException e) {
				Log.e("Cache.putValue", e);
			}
		 }
	 }
}
