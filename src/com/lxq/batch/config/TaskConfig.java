package com.lxq.batch.config;

import java.util.Map;

/**
 * ִ�е�Ԫ����
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class TaskConfig {
	
	/**����Ԫ�ļ����ò�������*/
	private Map parameters;
	
	/**����Ԫ�ļ��������Լ���*/
	private Map propertys ;
	
	/**����Ԫ����*/
	private Map executeUnits ;
	
	public Map getPropertys() {
		return propertys;
	}
	public void setPropertys(Map propertys) {
		this.propertys = propertys;
	}
	public Map getExecuteUnits() {
		return executeUnits;
	}
	public void setExecuteUnits(Map executeUnits) {
		this.executeUnits = executeUnits;
	}
	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
}
