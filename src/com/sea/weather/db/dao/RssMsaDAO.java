package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sea.weather.utils.Log;

public class RssMsaDAO extends BaseDAO{

	public boolean insertSailNotice(String marine,String description) throws SQLException{
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
        	pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        	pstmt.addBatch();
        delpstmt.executeUpdate();
        pstmt.executeBatch();
        conn.commit();
        return true;
		} catch (SQLException e) {
			Log.e("SailNoticeDAO.bathInsertSailNotice", e);
		} finally{
			closeCon(conn, pstmt);
			if(delpstmt!=null){
				delpstmt.close();
			}
		}
		
		return false;
	}
	
}
