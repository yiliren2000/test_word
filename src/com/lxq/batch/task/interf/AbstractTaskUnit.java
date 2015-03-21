package com.lxq.batch.task.interf;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lxq.batch.module.impl.I18N;
import com.lxq.batch.status.StatusEnum;
import com.lxq.batch.status.StatusSwitch;
import com.lxq.util.DateUtil;

/**
 * 
 * �̳д��࣬�ɱ������ӿڵ���
 * 
 * @author xueqingli@foxmail.com
 * 
 */
public abstract class AbstractTaskUnit implements Runnable {
	
	/**log4j��־*/
	public static Logger logger = Logger.getLogger(AbstractTaskUnit.class);
	
	/**��Ŀ·��*/
	private String path ;
	
	/**����Ԫ����*/
	private String name ;
	
	/**����Ԫ����*/
	private String depends ;
	
	/**����Ԫִ������*/
	private String executeDate;
	
	/**����Ԫ����*/
	private String describe ;

	/**����Ԫ����*/
	private Map parameters = new HashMap();

	/**����Ԫ����*/
	private Map propertys = new HashMap();
	
	/**����Ԫִ�е�class��*/
	private String executeClass ;
	
	/**
	 * ����Ԫ����
	 */
	public void run(){
		
		logger.info(DateUtil.getNowTime()+"--"+this.getDescribe()+"--"+I18N.getString("start_execute"));
		
		try {
			
			StatusEnum status = execute();
			
			if(StatusEnum.executeFail == status){
				
				StatusSwitch.executingToExecuteFail(name);
				logger.info(DateUtil.getNowTime()+"--"+this.getDescribe()+"--"+I18N.getString("execute_failed"));
			
			}else{
				
				logger.info(DateUtil.getNowTime()+"--"+this.getDescribe()+"--"+I18N.getString("execute_successed"));
				StatusSwitch.executingToExecuteSuccess(name);
			
			}
			
		} catch (Exception e) {
			
			StatusSwitch.executingToExecuteFail(name);
			logger.fatal(DateUtil.getNowTime()+"--"+this.getDescribe()+"--"+I18N.getString("execute_failed"));
			logger.error(I18N.getString("error_info")+"��", e);
		
		}
	}
	
	/**
	 * ����ִ�е�Ԫִ�еķ���
	 * @return ����Ԫִ�н��
	 * @throws Exception
	 */
	public abstract StatusEnum execute() throws Exception;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepends() {
		return depends;
	}

	public void setDepends(String depends) {
		this.depends = depends;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Map getPropertys() {
		return propertys;
	}

	public void setPropertys(Map propertys) {
		this.propertys = propertys;
	}

	public String getExecuteClass() {
		return executeClass;
	}

	public void setExecuteClass(String executeClass) {
		this.executeClass = executeClass;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		AbstractTaskUnit.logger = logger;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	
}
