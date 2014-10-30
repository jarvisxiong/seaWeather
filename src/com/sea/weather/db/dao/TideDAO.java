package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sea.weather.date.nmc.model.PortItemVO;
import com.sea.weather.date.nmc.model.TideItemVO;
import com.sea.weather.utils.Log;


public class TideDAO extends BaseDAO{

	public boolean bathInsertProvince(List<PortItemVO> province) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into province (code, name) values (?, ?)");
        delpstmt = conn.prepareStatement("DELETE FROM province");
        for (int i = 0; i <province.size();i++) {
        	pstmt.setString(1, province.get(i).getCode());
        	pstmt.setString(2, province.get(i).getName());
        	pstmt.addBatch();
        }
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("TideDAO.bathInsertProvince", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		return false;
	}
	
	public boolean bathInsertPortcode(List<PortItemVO> portcode) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into portcode (code, name) values (?, ?)");
        delpstmt = conn.prepareStatement("DELETE FROM portcode");
        for (int i = 0; i <portcode.size();i++) {
        	pstmt.setString(1, portcode.get(i).getCode());
        	pstmt.setString(2, portcode.get(i).getName());
        	pstmt.addBatch();
        }
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("TideDAO.bathInsertProvince", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		return false;
	}
	
	public boolean bathInsertTideItem(List<TideItemVO> tideItem,String selectDate) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		PreparedStatement delpstmt = null;
		try {
			conn = getConnection();
		
        conn.setAutoCommit(false);
        pstmt = conn.prepareStatement("insert into tideitem (selectDate, code,showTime,high) values (?,?,?,?)");
        delpstmt = conn.prepareStatement("DELETE FROM tideitem WHERE selectDate IN ("+selectDate+")");
        for (int i = 0; i <tideItem.size();i++) {
        	pstmt.setString(1, tideItem.get(i).getSelectDate());
        	pstmt.setString(2, tideItem.get(i).getCode());
        	pstmt.setString(3, tideItem.get(i).getShowTime());
        	pstmt.setString(4, tideItem.get(i).getHigh());
        	pstmt.addBatch();
        }
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("TideDAO.bathInsertTideItem", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		return false;
	}
	
	public List<PortItemVO> queryProvince() throws SQLException{
		// 连续数据库
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        List<PortItemVO> lisPortItemVO = new ArrayList<PortItemVO>();
		try {
			conn = this.getConnection();
		
        // statement用来执行SQL语句
        statement = conn.createStatement();
        
        // 要执行的SQL语句
        StringBuffer sql =new StringBuffer(256);
        			 sql.append("SELECT * FROM  province");
        			 
        // 结果集
        rs = statement.executeQuery(sql.toString());
        
        while(rs.next()) {
        	PortItemVO objPortItemVO = new PortItemVO();
        	objPortItemVO.setCode(rs.getString("code"));
        	objPortItemVO.setName(rs.getString("name"));
        	lisPortItemVO.add(objPortItemVO);
        }
        	return lisPortItemVO;
		} catch (SQLException e) {
			Log.e("TideDAO.queryProvince", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return null;
	}
	
	public List<PortItemVO> queryPortcode(String province) throws SQLException{
		// 连续数据库
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        List<PortItemVO> lisPortItemVO = new ArrayList<PortItemVO>();
		try {
			conn = this.getConnection();
		
        // statement用来执行SQL语句
        statement = conn.createStatement();
        
        // 要执行的SQL语句
        StringBuffer sql =new StringBuffer(256);
        			 sql.append("SELECT * FROM portcode WHERE CODE LIKE '");
        			 sql.append(province);
        			 sql.append("%'");
        			 
        // 结果集
        rs = statement.executeQuery(sql.toString());
        
        while(rs.next()) {
        	PortItemVO objPortItemVO = new PortItemVO();
        	objPortItemVO.setCode(rs.getString("code"));
        	objPortItemVO.setName(rs.getString("name"));
        	lisPortItemVO.add(objPortItemVO);
        }
        	return lisPortItemVO;
		} catch (SQLException e) {
			Log.e("TideDAO.queryProvince", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return null;
	}
}
