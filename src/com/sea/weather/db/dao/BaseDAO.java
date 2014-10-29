package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sea.weather.db.utils.DBConstant;

public class BaseDAO {

	public Connection getConnection() throws SQLException{
		try {
			Class.forName(DBConstant.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(DBConstant.URL, DBConstant.USER,DBConstant.PASSWORD);
		return conn;
	}
	
	public void closeCon(Connection conn,Statement statement,ResultSet rs) throws SQLException{
		if(conn!=null){
			conn.close();
		}
		if(statement!=null){
			statement.close();
		}
		if(rs!=null){
			rs.close();
		}		
	}
	
	public void closeCon(Connection conn,PreparedStatement ps) throws SQLException{
		if(conn!=null){
			conn.close();
		}
		if(ps!=null){
			ps.close();
		}
	}
}
