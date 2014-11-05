package com.sea.weather.db.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sea.weather.utils.Log;

public class DbMapDAO extends BaseDAO{

	public void put(String key,String value) throws SQLException{
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        delpstmt = conn.prepareStatement("DELETE FROM dbmap where key = '"+key+"'");
        pstmt = conn.prepareStatement("insert into dbmap (key, value) values (?,?)");
        pstmt.setString(1, key);
        pstmt.setBytes(2, value.getBytes("utf-8"));
        delpstmt.executeUpdate();
        pstmt.executeUpdate();
        conn.commit();
		} catch (SQLException | UnsupportedEncodingException e) {
			Log.e("DbMapDAO.put", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		
	}
	
	public String get(String key) throws SQLException{
		// 连续数据库
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        String value = null;
        try {
        	conn = this.getConnection();
            // statement用来执行SQL语句
            statement = conn.createStatement();
            // 要执行的SQL语句
            StringBuffer sql =new StringBuffer(256);
            			 sql.append("SELECT value FROM dbmap WHERE key = '");
            			 sql.append(key);
            			 sql.append("'");
            // 结果集
            rs = statement.executeQuery(sql.toString());
            if(rs.next()){
            	value = rs.getString("value");
            }
            return value;
        } catch (SQLException e) {
			Log.e("DbMapDAO.get", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
        return null;
	}
	
}
