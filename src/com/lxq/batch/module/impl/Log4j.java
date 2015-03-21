package com.lxq.batch.module.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.lxq.batch.module.interf.AbstractModule;

/**
 * 
 * log4jģ��
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class Log4j extends AbstractModule{

	/**
	 * ����log4j�����ļ�������ʼ��log4j
	 * @param path �����ļ�·��
	 * @param fileName �����ļ���
	 * @throws IOException
	 */
	public void load() throws Exception {
	
		Properties props = new Properties();
		
		FileInputStream istream = new FileInputStream(this.getPath() + this.getConfigFile());
		
		props.load(istream);
		
		istream.close();
	
		// ��ȡ�����ļ���log4j.appender.R.File���Ե�ֵ
		String logFile = props.getProperty("log4j.appender.R.File");
		
		// ��������log4j.appender.R.File���Ե�ֵ
		props.setProperty("log4j.appender.R.File", this.getPath() + logFile);

		// װ��log4j������Ϣ
		PropertyConfigurator.configure(props);
	
	}
}
