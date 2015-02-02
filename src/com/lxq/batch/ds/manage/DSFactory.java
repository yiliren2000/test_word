package com.lxq.batch.ds.manage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * DBCP数据源模块
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class DSFactory {
	
	private static DataSource myDataSource;
	
	public static synchronized Connection getConnection() throws SQLException
	{
		Connection conn = null;
		try {
			conn = myDataSource.getConnection();
		} catch (SQLException e) {
			throw e;
		}
		return conn;
	}
	
	public static void init(DataSource ds) throws Exception {
		myDataSource = ds ;
		if(ds == null){
			throw new Exception("errror,DBCP init !");
		}
	}

	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
			}finally{
				conn = null;
			}
		}
		
	}
	public static void close(Statement stmt){
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
			}finally{
				stmt = null;
			}
		}
	}
	public static void close(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
			}finally{
				rs = null;
			}
		}
	}

}
