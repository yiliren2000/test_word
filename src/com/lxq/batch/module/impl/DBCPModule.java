package com.lxq.batch.module.impl;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.lxq.batch.ds.manage.DSFactory;
import com.lxq.batch.module.interf.AbstractModule;

/**
 * DBCP数据源模块
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class DBCPModule extends AbstractModule {
	
	BasicDataSource ds = null;
	
	public void load() throws Exception {
		Properties props = new Properties();
		FileInputStream istream;
			
		istream = new FileInputStream(this.getPath()+this.getConfigFile());
		props.load(istream);
		
		ds = (BasicDataSource) BasicDataSourceFactory.createDataSource(props);
		
		DSFactory.init(BasicDataSourceFactory.createDataSource(props));
	}
	
	public void close() {
		try {
			ds.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
