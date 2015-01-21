package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.sea.weather.date.model.SeaAdVO;
import com.sea.weather.utils.Log;

public class SeaAdDAO extends BaseDAO{

	public boolean insertSeaAd(SeaAdVO seaAdVO) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
		
        pstmt = conn.prepareStatement("insert into seaad (id,content,type, typevalue,belong,begintime,endtime,publishtime) values (?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, seaAdVO.getId());
        	pstmt.setString(2, seaAdVO.getContent());
        	pstmt.setString(3, seaAdVO.getType());
        	pstmt.setString(4, seaAdVO.getTypevalue());
        	pstmt.setString(5, seaAdVO.getBelong());
        	pstmt.setTimestamp(6, new Timestamp(seaAdVO.getBegintime().getTime()));
        	pstmt.setTimestamp(7, new Timestamp(seaAdVO.getEndtime().getTime()));
        	pstmt.setTimestamp(8, new Timestamp(seaAdVO.getPublishtime().getTime()));
        pstmt.executeUpdate();
        return true;
		} catch (SQLException e) {
			Log.e("SeaAdDAO.insertSeaAd", e);
		} finally{
			closeCon(conn, pstmt);
		}
		return false;
	}
	
}
