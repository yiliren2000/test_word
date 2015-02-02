package com.lxq.batch.loader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lxq.batch.config.SystemConfig;
import com.lxq.batch.module.interf.AbstractModule;


/**
 * 读取系统模块配置文件
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class SystemLoader {

	/**系统配置实例*/
	private static SystemConfig systemConfig = new SystemConfig();
	
	public SystemLoader() {}

	/**
	 * 加载系统配置文件
	 * @param path 系统配置文件目录及etc目录的路径
	 * @param fileName 配置文件名称
	 * @throws DocumentException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void load(String path , String fileName) throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		//并发线程数，默认为1
		int threadNum = 1;
		
		/**是否自动执行，默认true*/
		boolean autoExecute = true;
		
		//模块属性集合
		List modules = new ArrayList();
		
		//新建xml文件读取对象
		SAXReader saxReader = new SAXReader();
		//读取配置文件内容
		Document document = saxReader.read(new File(path+fileName));
		//获取xml文件的根节点
		Element root = document.getRootElement();
		
		//获取根节点下threadNum节点的值
		threadNum = Integer.parseInt(root.element("threadNum").getText());

		//获取根节点下threadNum节点的值
		autoExecute = Boolean.parseBoolean(root.element("autoExecute").getText());
		
		//获取根节点下modules节点下的module节点集
		Iterator it_modules = root.element("modules").elementIterator("module");
		
		while (it_modules.hasNext()) {
			//获取module节点
			Element ele_module = (Element) it_modules.next();
			//获取module节点的名字
			String name = ele_module.attributeValue("name");
			//获取module节点下describe节点的值
			String describe = ele_module.element("describe").getText();
			//获取module节点下initClass节点的值
			String initClass = ele_module.element("initClass").getText();
			//获取module节点下initClass节点的值
			String configFile = ele_module.element("configFile").getText();

			//获取module节点下property节点集
			Iterator it_properties = ele_module.elementIterator("property");
			//遍历property节点集

			//存储module节点的property属性集
			Map properties = new HashMap();
			while(it_properties.hasNext()){
				//获取property节点
				Element property = (Element) it_properties.next();
				//获取property节点的name属性的值
				String propertyName = property.attributeValue("name");
				//获取property节点的value属性的值
				String propertyValue = property.attributeValue("value");
				
				//存放属性名和属性值
				properties.put(propertyName, propertyValue);
			}
			
			AbstractModule module = (AbstractModule)Class.forName(initClass).newInstance();
			
			module.setPath(path);
			module.setName(name);
			module.setDescribe(describe);
			module.setInitClass(initClass);
			module.setConfigFile(configFile);
			module.setPropertys(properties);
			
			modules.add(module);
		}
		//设置系统并发线程数
		systemConfig.setThreadNum(threadNum);
		
		//设置系统并发线程数
		systemConfig.setAutoExecute(autoExecute);
		
		//设置系统模块
		systemConfig.setModules(modules);
	}

	public static SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public static void setSystemConfig(SystemConfig systemConfig) {
		SystemLoader.systemConfig = systemConfig;
	}
	
}
