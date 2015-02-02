package com.lxq.batch.module.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lxq.batch.bean.FtpUnit;
import com.lxq.batch.module.interf.AbstractModule;

/**
 * ftp模块
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class FtpModule extends AbstractModule{

	public static Map FtpUnits = new HashMap();

	public static FtpUnit getFtpUnit(String ftpUnitName) {
		return (FtpUnit) FtpUnits.get(ftpUnitName);
	}
	
	public void load() throws Exception {
		/** 建立一个xml文件读取对象 */
		SAXReader saxReader = new SAXReader();
		/** 获取xml文档 */
		Document document = null;
		try {
			document = saxReader.read(new File(this.getPath()+this.getConfigFile()));

			Element root = document.getRootElement();

			Iterator iterator = root.elementIterator("unit");

			while (iterator.hasNext()) {

				Element item = (Element) iterator.next();

				String name = item.attributeValue("name");
				String server = item.elementText("server");
				int port = Integer.parseInt(item.elementText("port"));
				String userName = item.elementText("userName");
				String userPassword = item.elementText("userPassword");
				int fileType = Integer.parseInt(item.elementText("fileType"));
				String remoteDirectory = item.elementText("remoteDirectory");
				String localDirectory = item.elementText("localDirectory");

				FtpUnit f = new FtpUnit();

				f.setName(name);
				f.setServer(server);
				f.setPort(port);
				f.setUserName(userName);
				f.setUserPassword(userPassword);
				f.setFileType(fileType);
				f.setRemoteDirectory(remoteDirectory);
				f.setLocalDirectory(this.getPath()+localDirectory);

				FtpUnits.put(name, f);
			}
		} catch (Exception e) {
			throw e;
		}
	}

}