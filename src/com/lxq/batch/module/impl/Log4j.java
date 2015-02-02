package com.lxq.batch.module.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.lxq.batch.module.interf.AbstractModule;

/**
 * 
 * log4j模块
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class Log4j extends AbstractModule{

	/**
	 * 加载log4j配置文件，并初始化log4j
	 * @param path 配置文件路径
	 * @param fileName 配置文件名
	 * @throws IOException
	 */
	public void load() throws Exception {
	
		Properties props = new Properties();
		
		FileInputStream istream = new FileInputStream(this.getPath() + this.getConfigFile());
		
		props.load(istream);
		
		istream.close();
	
		// 获取配置文件中log4j.appender.R.File属性的值
		String logFile = props.getProperty("log4j.appender.R.File");
		
		// 重新设置log4j.appender.R.File属性的值
		props.setProperty("log4j.appender.R.File", this.getPath() + logFile);

		// 装入log4j配置信息
		PropertyConfigurator.configure(props);
	
	}
}
