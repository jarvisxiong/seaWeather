package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sea.weather.date.nmc.model.MarineVO;
import com.sea.weather.utils.Log;

public class RssMsaDAO extends BaseDAO{

	public boolean insertSailNotice(String marine,String code,String description,Date updatetime) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into sailnotice (marine,code,description, updatetime) values (?,?,?,?)");
        delpstmt = conn.prepareStatement("DELETE FROM sailnotice where marine = '"+marine+"'");
        	pstmt.setString(1, marine);
        	pstmt.setString(2, code);
        	pstmt.setString(3, description);
        	pstmt.setTimestamp(4, new Timestamp(updatetime.getTime()));
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
	
	public String getSailNotice(String code) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM sailnotice s WHERE s.code = '").append(code).append("'");

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
	
	public boolean insertSailWaring(String marine,String code,String description,Date updatetime) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into sailwarning (marine,code,description, updatetime) values (?,?,?,?)");
        delpstmt = conn.prepareStatement("DELETE FROM sailwarning where marine = '"+marine+"'");
        	pstmt.setString(1, marine);
        	pstmt.setString(2, code);
        	pstmt.setString(3, description);
        	pstmt.setTimestamp(4, new Timestamp(updatetime.getTime()));
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
	
	public String getSailWaring(String code) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM sailwarning s WHERE s.code = '").append(code).append("'");

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
	
	public boolean insertMarine(MarineVO marine) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into marine (name,code, type) values (?,?,?)");
        delpstmt = conn.prepareStatement("DELETE FROM marine where code = '"+marine.getCode()+"'");
        	pstmt.setString(1, marine.getName());
        	pstmt.setString(2, marine.getCode());
        	pstmt.setString(3, marine.getType());
        	pstmt.addBatch();
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("SailNoticeDAO.insertMarine", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		
		return false;
	}
	
	public MarineVO getMarine(String code) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		MarineVO objMarineVO = null;
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM marine s WHERE s.code = '").append(code).append("'");

			// 结果集
			rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				objMarineVO = new MarineVO();
				objMarineVO.setName(rs.getString("name"));
				objMarineVO.setCode(rs.getString("code"));
				objMarineVO.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			Log.e("RssMsaDAO.getSailWaring", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return objMarineVO;
	}
	
	public List<MarineVO> queryMarine()throws SQLException{
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		List<MarineVO> lisMarineVO = new ArrayList<MarineVO>();
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM marine ");

			// 结果集
			rs = statement.executeQuery(sql.toString());
			while (rs.next()) {
				MarineVO objMarineVO = new MarineVO();
				objMarineVO.setName(rs.getString("name"));
				objMarineVO.setCode(rs.getString("code"));
				objMarineVO.setType(rs.getString("type"));
				lisMarineVO.add(objMarineVO);
			}
		} catch (SQLException e) {
			Log.e("RssMsaDAO.queryMarine", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return lisMarineVO;
	}
	
}
