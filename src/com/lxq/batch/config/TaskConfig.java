package com.lxq.batch.config;

import java.util.Map;

/**
 * 执行单元配置
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class TaskConfig {
	
	/**任务单元文件配置参数集合*/
	private Map parameters;
	
	/**任务单元文件配置属性集合*/
	private Map propertys ;
	
	/**任务单元集合*/
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
