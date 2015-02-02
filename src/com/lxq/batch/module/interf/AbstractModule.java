package com.lxq.batch.module.interf;

import java.util.Map;

/**
 * 继承此类，可被批量接口调用
 * 
 * @author xueqingli@foxmail.com
 */
public abstract class AbstractModule {
	
	/**项目路径*/
	private String path;
	
	/**系统模块名称*/
	private String name;
	
	/**系统模块描述*/
	private String describe;
	
	/**系统模块初始化类*/
	private String initClass;
	
	/**系统模块配置文件*/
	private String configFile;
	
	/**系统模块初始化参数*/
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
