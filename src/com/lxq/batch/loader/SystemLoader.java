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
 * ��ȡϵͳģ�������ļ�
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class SystemLoader {

	/**ϵͳ����ʵ��*/
	private static SystemConfig systemConfig = new SystemConfig();
	
	public SystemLoader() {}

	/**
	 * ����ϵͳ�����ļ�
	 * @param path ϵͳ�����ļ�Ŀ¼��etcĿ¼��·��
	 * @param fileName �����ļ�����
	 * @throws DocumentException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void load(String path , String fileName) throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		//�����߳�����Ĭ��Ϊ1
		int threadNum = 1;
		
		/**�Ƿ��Զ�ִ�У�Ĭ��true*/
		boolean autoExecute = true;
		
		//ģ�����Լ���
		List modules = new ArrayList();
		
		//�½�xml�ļ���ȡ����
		SAXReader saxReader = new SAXReader();
		//��ȡ�����ļ�����
		Document document = saxReader.read(new File(path+fileName));
		//��ȡxml�ļ��ĸ��ڵ�
		Element root = document.getRootElement();
		
		//��ȡ���ڵ���threadNum�ڵ��ֵ
		threadNum = Integer.parseInt(root.element("threadNum").getText());

		//��ȡ���ڵ���threadNum�ڵ��ֵ
		autoExecute = Boolean.parseBoolean(root.element("autoExecute").getText());
		
		//��ȡ���ڵ���modules�ڵ��µ�module�ڵ㼯
		Iterator it_modules = root.element("modules").elementIterator("module");
		
		while (it_modules.hasNext()) {
			//��ȡmodule�ڵ�
			Element ele_module = (Element) it_modules.next();
			//��ȡmodule�ڵ������
			String name = ele_module.attributeValue("name");
			//��ȡmodule�ڵ���describe�ڵ��ֵ
			String describe = ele_module.element("describe").getText();
			//��ȡmodule�ڵ���initClass�ڵ��ֵ
			String initClass = ele_module.element("initClass").getText();
			//��ȡmodule�ڵ���initClass�ڵ��ֵ
			String configFile = ele_module.element("configFile").getText();

			//��ȡmodule�ڵ���property�ڵ㼯
			Iterator it_properties = ele_module.elementIterator("property");
			//����property�ڵ㼯

			//�洢module�ڵ��property���Լ�
			Map properties = new HashMap();
			while(it_properties.hasNext()){
				//��ȡproperty�ڵ�
				Element property = (Element) it_properties.next();
				//��ȡproperty�ڵ��name���Ե�ֵ
				String propertyName = property.attributeValue("name");
				//��ȡproperty�ڵ��value���Ե�ֵ
				String propertyValue = property.attributeValue("value");
				
				//���������������ֵ
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
		//����ϵͳ�����߳���
		systemConfig.setThreadNum(threadNum);
		
		//����ϵͳ�����߳���
		systemConfig.setAutoExecute(autoExecute);
		
		//����ϵͳģ��
		systemConfig.setModules(modules);
	}

	public static SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public static void setSystemConfig(SystemConfig systemConfig) {
		SystemLoader.systemConfig = systemConfig;
	}
	
}
