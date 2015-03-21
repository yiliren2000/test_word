package com.lxq.batch.module.interf;

import java.util.Map;

/**
 * �̳д��࣬�ɱ������ӿڵ���
 * 
 * @author xueqingli@foxmail.com
 */
public abstract class AbstractModule {
	
	/**��Ŀ·��*/
	private String path;
	
	/**ϵͳģ������*/
	private String name;
	
	/**ϵͳģ������*/
	private String describe;
	
	/**ϵͳģ���ʼ����*/
	private String initClass;
	
	/**ϵͳģ�������ļ�*/
	private String configFile;
	
	/**ϵͳģ���ʼ������*/
	private Map propertys;

	public abstract void load() throws Exception;
	public void close() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getInitClass() {
		return initClass;
	}

	public void setInitClass(String initClass) {
		this.initClass = initClass;
	}

	public Map getPropertys() {
		return propertys;
	}

	public void setPropertys(Map propertys) {
		this.propertys = propertys;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
