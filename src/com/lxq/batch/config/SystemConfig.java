package com.lxq.batch.config;

import java.util.List;

/**
 * ϵͳ����
 * 
 * @author xueqingli@foxmail.com
 */
public class SystemConfig {
	
	/**�����߳�����Ĭ���߳���Ϊ1*/
	private int threadNum = 1;

	/**�Ƿ��Զ�ִ�У�Ĭ��true*/
	private boolean autoExecute = true;

	/**ϵͳģ�鼯*/
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
