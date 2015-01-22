package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.sea.weather.date.model.SeaAdVO;
import com.sea.weather.utils.Log;

public class SeaAdDAO extends BaseDAO{

	public boolean insertSeaAd(SeaAdVO seaAdVO) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
		
        pstmt = conn.prepareStatement("insert into seaad (id,content,type, typevalue,belong,begintime,endtime,publishtime,custom) values (?,?,?,?,?,?,?,?,?)");
        	pstmt.setString(1, seaAdVO.getId());
        	pstmt.setString(2, seaAdVO.getContent());
        	pstmt.setString(3, seaAdVO.getType());
        	pstmt.setString(4, seaAdVO.getTypevalue());
        	pstmt.setString(5, seaAdVO.getBelong());
        	pstmt.setTimestamp(6, new Timestamp(seaAdVO.getBegintime().getTime()));
        	pstmt.setTimestamp(7, new Timestamp(seaAdVO.getEndtime().getTime()));
        	pstmt.setTimestamp(8, new Timestamp(seaAdVO.getPublishtime().getTime()));
        	pstmt.setString(9, seaAdVO.getCustom());
        pstmt.executeUpdate();
        return true;
		} catch (SQLException e) {
			Log.e("SeaAdDAO.insertSeaAd", e);
		} finally{
			closeCon(conn, pstmt);
		}
		return false;
	}
	
	public SeaAdVO getSeaAdVO(String belong) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		SeaAdVO seaAdVO = null;
		try {
			conn = this.getConnection();

			// statement用来执行SQL语句
			statement = conn.createStatement();

			// 要执行的SQL语句
			StringBuffer sql = new StringBuffer(256);
			sql.append("SELECT * FROM seaad sa WHERE  NOW() > sa.begintime AND NOW() <sa.endtime AND sa.belong = '").append(belong).append("'");

			// 结果集
			rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				seaAdVO = new SeaAdVO();
				seaAdVO.setId(rs.getString("id"));
				seaAdVO.setContent(rs.getString("content"));
				seaAdVO.setType(rs.getString("type"));
				seaAdVO.setTypevalue(rs.getString("typevalue"));
				seaAdVO.setBelong(rs.getString("belong"));
				seaAdVO.setBegintime(rs.getTimestamp("begintime"));
				seaAdVO.setEndtime(rs.getTimestamp("endtime"));
				seaAdVO.setPublishtime(rs.getTimestamp("publishtime"));
				seaAdVO.setCustom(rs.getString("custom"));
			}
		} catch (SQLException e) {
			Log.e("RssMsaDAO.getSailWaring", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
		return seaAdVO;
	}
	
}
