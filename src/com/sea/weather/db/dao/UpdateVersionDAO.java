package com.sea.weather.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sea.weather.date.model.UpdateVersionVO;
import com.sea.weather.utils.Log;

public class UpdateVersionDAO extends BaseDAO{

	public UpdateVersionVO get() throws SQLException{
		// 连续数据库
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
        	conn = this.getConnection();
            // statement用来执行SQL语句
            statement = conn.createStatement();
            // 要执行的SQL语句
            StringBuffer sql =new StringBuffer(256);
            			 sql.append("SELECT * FROM updateversion ");
            // 结果集
            rs = statement.executeQuery(sql.toString());
            if(rs.next()){
            	UpdateVersionVO objUpdateVersionVO = new UpdateVersionVO();
            	objUpdateVersionVO.setAppName(rs.getString("appName"));
            	objUpdateVersionVO.setUpdateContent(rs.getString("updateContent"));
            	objUpdateVersionVO.setUpdateUrl(rs.getString("updateUrl"));
            	objUpdateVersionVO.setVersionCode(rs.getInt("versionCode"));
            	objUpdateVersionVO.setVersionName(rs.getString("versionName"));
            	return objUpdateVersionVO;
            }
            return null;
        } catch (SQLException e) {
			Log.e("DbMapDAO.get", e);
		} finally{
			this.closeCon(conn, statement, rs);
		}
        return null;
		
	}
	
}
