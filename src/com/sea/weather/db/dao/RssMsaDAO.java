package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.sea.weather.utils.Log;

public class RssMsaDAO extends BaseDAO{

	public boolean insertSailNotice(String marine,String description,Date updatetime) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into sailnotice (marine,description, updatetime) values (?,?,?)");
        delpstmt = conn.prepareStatement("DELETE FROM sailnotice where marine = '"+marine+"'");
        	pstmt.setString(1, marine);
        	pstmt.setString(2, description);
        	pstmt.setTimestamp(3, new Timestamp(updatetime.getTime()));
        	pstmt.addBatch();
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("SailNoticeDAO.insertSailNotice", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		
		return false;
	}
	
	public String getSailNotice(String marine) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM sailnotice s WHERE s.marine = '").append(marine).append("'");

			// 结果集
			rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				return rs.getString("description");
			}
		} catch (SQLException e) {
			Log.e("RssMsaDAO.getSailNotice", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return "";
	}
	
	public boolean insertSailWaring(String marine,String description,Date updatetime) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into sailwarning (marine,description, updatetime) values (?,?,?)");
        delpstmt = conn.prepareStatement("DELETE FROM sailwarning where marine = '"+marine+"'");
        	pstmt.setString(1, marine);
        	pstmt.setString(2, description);
        	pstmt.setTimestamp(3, new Timestamp(updatetime.getTime()));
        	pstmt.addBatch();
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("SailNoticeDAO.insertSailWaring", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		
		return false;
	}
	
	public String getSailWaring(String marine) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM sailwarning s WHERE s.marine = '").append(marine).append("'");

			// 结果集
			rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				return rs.getString("description");
			}
		} catch (SQLException e) {
			Log.e("RssMsaDAO.getSailWaring", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return "";
	}
	
}
