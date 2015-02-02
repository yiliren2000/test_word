package com.lxq.batch.config;

import java.util.List;

/**
 * 系统配置
 * 
 * @author xueqingli@foxmail.com
 */
public class SystemConfig {
	
	/**并发线程数，默认线程数为1*/
	private int threadNum = 1;

	/**是否自动执行，默认true*/
	private boolean autoExecute = true;

	/**系统模块集*/
	private List modules ;
	
	public int getThreadNum() {
		return threadNum;
	}
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	public List getModules() {
		return modules;
	}
	public void setModules(List modules) {
		this.modules = modules;
	}
	public boolean isAutoExecute() {
		return autoExecute;
	}
	public void setAutoExecute(boolean autoExecute) {
		this.autoExecute = autoExecute;
	}
	
}
