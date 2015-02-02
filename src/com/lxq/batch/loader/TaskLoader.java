package com.lxq.batch.loader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lxq.batch.config.TaskConfig;
import com.lxq.batch.task.interf.AbstractTaskUnit;

/**
 * 读取执行单元配置文件
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class TaskLoader {
	
	/**任务执行单元配置*/
	private static TaskConfig task = new TaskConfig();
	
	public TaskLoader() {}

	public static void load(String path , String fileName) throws DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Map taskPropertys = new HashMap();
		Map taskUnits = new HashMap();
		
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(path+fileName));
		Element root = document.getRootElement();

		Iterator it_taskPropertys = root.elementIterator("property");

		while (it_taskPropertys.hasNext()) {
			Element property = (Element) it_taskPropertys.next();
			taskPropertys.put(property.attributeValue("name"), property.getText());
		}
		task.setPropertys(taskPropertys);

		Iterator it_executeUnits = root.element("units")
				.elementIterator("unit");

		while (it_executeUnits.hasNext()) {
			Element it_executeUnit = (Element) it_executeUnits.next();

			Map unitPropertys = new HashMap();
			Map unitParameters = new HashMap();
			String name = it_executeUnit.attributeValue("name");
			String depends = it_executeUnit.attributeValue("depends");
			String executeDate = it_executeUnit.attributeValue("executeDate");
			String describe = it_executeUnit.elementText("describe");
			
			Iterator unit_Propertys = it_executeUnit
					.elementIterator("property");
			while (unit_Propertys.hasNext()) {
				Element property = (Element) unit_Propertys.next();
				unitPropertys.put(property.attributeValue("name"), property.getText());
			}

			Iterator unit_parameters = it_executeUnit
					.elementIterator("parameter");
			while (unit_parameters.hasNext()) {
				Element property = (Element) unit_parameters.next();
				unitParameters.put(property.attributeValue("name"), property.getText());
			}
			
			String executeClass = it_executeUnit.elementText("executeClass");
			
			AbstractTaskUnit taskUnit = (AbstractTaskUnit)Class.forName(executeClass).newInstance();
			taskUnit.setPath(path);
			taskUnit.setName(name);
			taskUnit.setDepends(depends);
			taskUnit.setExecuteDate(executeDate);
			taskUnit.setDescribe(describe);
			taskUnit.setParameters(unitParameters);
			taskUnit.setPropertys(unitPropertys);
			taskUnit.setExecuteClass(executeClass);
			taskUnits.put(name,taskUnit);
		}
		task.setExecuteUnits(taskUnits);
	}

	public static TaskConfig getTask() {
		return task;
	}

	public static void setTask(TaskConfig task) {
		TaskLoader.task = task;
	}
}
