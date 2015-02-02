package com.lxq.batch.module.impl;
import com.lxq.batch.ds.manage.DSFactory;
import com.lxq.batch.module.interf.AbstractModule;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * DBCP数据源模块
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class C3P0Module extends AbstractModule {
	private ComboPooledDataSource ds = null;
	
	public void load() throws Exception {
		
		System.setProperty("com.mchange.v2.c3p0.cfg.xml",this.getPath()+this.getConfigFile());
		
		ds = new ComboPooledDataSource(true);
		
		DSFactory.init(ds);
	}
	
	public void close() {
		
		ds.close();
	}

}
