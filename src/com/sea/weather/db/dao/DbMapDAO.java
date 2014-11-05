package com.sea.weather.db.dao;

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
        delpstmt = conn.prepareStatement("DELETE FROM dbmap where keyword = '"+key+"'");
        pstmt = conn.prepareStatement("insert into dbmap (keyword, val) values (?,?)");
        pstmt.setString(1, key);
        pstmt.setString(2, value);
        delpstmt.executeUpdate();
        pstmt.executeUpdate();
        conn.commit();
		} catch (SQLException e) {
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
            			 sql.append("SELECT val FROM dbmap WHERE keyword = '");
            			 sql.append(key);
            			 sql.append("'");
            // 结果集
            rs = statement.executeQuery(sql.toString());
            if(rs.next()){
            	value = rs.getString("val");
            }
            return value;
        } catch (SQLException e) {
			Log.e("DbMapDAO.get", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
        return null;
	}
	
	public static void main(String[] args) throws SQLException {
		DbMapDAO objDbMapDAO = new DbMapDAO();
		Log.i(objDbMapDAO.get("key"));
	}
	
}
