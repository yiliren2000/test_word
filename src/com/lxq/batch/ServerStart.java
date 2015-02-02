package com.lxq.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.lxq.batch.config.SystemConfig;
import com.lxq.batch.loader.SystemLoader;
import com.lxq.batch.loader.TaskLoader;
import com.lxq.batch.module.impl.I18N;
import com.lxq.batch.module.impl.Log4j;
import com.lxq.batch.module.interf.AbstractModule;
import com.lxq.batch.status.StatusEnum;
import com.lxq.batch.status.StatusSwitch;
import com.lxq.batch.task.interf.AbstractTaskUnit;
import com.lxq.batch.util.BusinessDateUtil;
import com.lxq.util.DateUtil;

/**
 * 批量服务启动
 * 
 * @author xueqingli@foxmail.com
 * 
 */

public class ServerStart {
	
	/** 获取日志对象 */
	public static Logger logger = Logger.getLogger(ServerStart.class);
	/** 获取未执行单元列表 */
	private static List executeless = StatusSwitch.executeless;
	/** 获取正在执行单元列表 */
	private static List executing = StatusSwitch.executing;
	/** 获取执行失败单元列表 */
	private static List executeFail = StatusSwitch.executeFail;
	
	private static String path;

	/**
	 * 多线程批量接口入口
	 * 
	 * @param path
	 *            项目所在的路径，独立部署：空，web项目：web项目路径
	 * @throws Exception
	 */
	public static void call(String path) throws Exception {

		// 初始化log4j日志
		Log4j log4j = new Log4j();
		log4j.setPath(path);
		log4j.setConfigFile("etc/module/log4j.properties");
		log4j.load();
		
		//	加载国际化文件
        I18N i18n = new I18N();
        i18n.setPath(path);
        i18n.setConfigFile("etc/language/message_locale.properties");
        i18n.load();
		
		// 加载系统模块配置文件
		SystemLoader.load(path, "etc/systemConfig.xml");
		logger.info("*********" + DateUtil.getNowTime() + " "+I18N.getString("load_system_module")+"*********");
		loadSystemModules(path);// 加载系统模块
		// 加载执行单元配置文件
		TaskLoader.load(path, "etc/task.xml");
		initExecuteUnit(path);
		logger.info("*********" + DateUtil.getNowTime() + " "+I18N.getString("load_task_unit")+ "*********");
		
		// 如果不是自动执行，则需要手工选择执行单元
		if(!SystemLoader.getSystemConfig().isAutoExecute()){
			choiceUnit();//选择执行单元
		} else{
			executeTaskUnits(path);// 执行接口单元
		}

		logger.info("*********" + DateUtil.getNowTime() + " "+I18N.getString("close_system_module")+ "*********");
		// 关闭系统模块
		closeSystemModules(path);
	}

	/**
	 * 加载系统功能模块
	 * 
	 * @throws Exception
	 */
	public static void loadSystemModules(String path) throws Exception {
		// 获取系统配置
		SystemConfig systemConfig = SystemLoader.getSystemConfig();
		// 获取系统功能模块集合
		List modules = systemConfig.getModules();
		// 循环遍历加载系统功能模块
		for (int i = 0; i < modules.size(); i++) {
			// 获取功能模块
			AbstractModule module = (AbstractModule) modules.get(i);
			logger.info("**"+I18N.getString("load")+" " + module.getDescribe() + "**");
			// 加载系统功能模块
			module.load();
		}
	}

	/**
	 * 关闭系统功能模块
	 * 
	 * @throws Exception
	 */
	public static void closeSystemModules(String path) throws Exception {

		// 获取系统配置
		SystemConfig systemConfig = SystemLoader.getSystemConfig();
		// 获取系统功能模块集合
		List modules = systemConfig.getModules();
		// 循环遍历加载系统功能模块
		for (int i = 0; i < modules.size(); i++) {
			// 获取功能模块
			AbstractModule module = (AbstractModule) modules.get(i);

			logger.info("**"+I18N.getString("close")+" " + module.getDescribe() + "**");
			// 加载系统功能模块
			module.close();
		}

	}

	/**
	 * 初始化未执行单元列表
	 * 
	 * @param path
	 *            项目路径
	 */
	public static void initExecuteUnit(String path) {
		// 获取所有接口单元列表
		Map taskUnits = TaskLoader.getTask().getExecuteUnits();
		// 用所有接口单元名称初始化未执行单元表
		Set keys = taskUnits.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			executeless.add(it.next());
		}
	}

	/**
	 * 执行任务单元
	 * 
	 * @param path
	 *            项目路径
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InterruptedException
	 */
	public static void executeTaskUnits(String path)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InterruptedException {
		List threads = new ArrayList();

		// 获取所有接口单元列表
		Map taskUnits = TaskLoader.getTask().getExecuteUnits();

		while (true) {
			// 查找未执行单元表
			for (int i = 0; i < executeless.size(); i++) {
				// 获取未执行的接口单元
				AbstractTaskUnit taskUnit = (AbstractTaskUnit) taskUnits
						.get(executeless.get(i));
				// 获取接口单元名称
				String name = taskUnit.getName();
				// 获取接口单元执行日期
				String executeDate = taskUnit.getExecuteDate();
				// 执行日期是当前业务日期
				if (isExecuteDate(executeDate)) {
					// 获取接口单元的单元依赖
					String depends = taskUnit.getDepends();
					// 检查依赖单元，判断此单元下一步操作
					StatusEnum executeStatus = checkDepends(name, depends);
					// 如果当前单元可执行,且没有超过最大线程数
					if (StatusEnum.executeAble == executeStatus
							&& executing.size() < SystemLoader
									.getSystemConfig().getThreadNum()) {
						// 将当前单元转入正在执行单元列表
						StatusSwitch.executelessToExecuting(name);
						Thread thread = new Thread(taskUnit);
						threads.add(thread);
						thread.start();
					}
					// 执行日期不是当前日期，则视为此单元执行成功
				} else {
					StatusSwitch.executelessToExecuteSuccess(name);
				}
			}
			/*
			 * 如果未执行单元表中的接口单元已经为空， 则证明所有的接口单元已经执行完毕，那么跳出循环。
			 */
			if (executeless.size() == 0) {
				break;
			}
		}
		// 等待所有线程终止
		while (true) {
			for (int i = 0; i < threads.size(); i++) {
				Thread thread = (Thread) threads.get(i);
				thread.join();
				threads.remove(thread);
			}

			if (threads.size() <= 0) {
				break;
			}
		}
	}

	/**
	 * 判断业务日期是否为执行日期
	 * 
	 * @param executeDate
	 *            执行日期
	 * @return
	 */
	public static boolean isExecuteDate(String executeDate) {

		// 执行日期为空，则默认当前日期执行
		if (executeDate.trim().length() == 0) {
			return true;
		}
		String year = executeDate.substring(0, 4);// 获取接口单元执行日期年份yyyy
		String month = executeDate.substring(4, 6);// 获取接口单元执行日期月份MM
		String day = executeDate.substring(6, 8);// 获取接口单元执行日期日份dd
		String today = BusinessDateUtil.getBusinessDate();// 获取系统当前日期yyyyMMdd
		String cur_year = today.substring(0, 4);// 获取系统当前年份yyyy
		String cur_month = today.substring(4, 6);// 获取系统当前月份MM
		String cur_day = today.substring(6, 8);// 获取系统当前日份dd
		if (year.equals("YYYY")) {
			year = cur_year;
		}
		if (month.equals("MM")) {
			month = cur_month;
		}
		if (day.equals("DD")) {
			day = cur_day;
		}
		// 如果业务日期是执行日期
		if (today.equals(year + month + day)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param unitName
	 *            任务单元名称
	 * @param depends
	 *            当前接口单元依赖的执行单元（多个执行单元之间以逗号隔开）
	 * @return 本单元可执行状态（未执行、可以执行、执行失败）
	 */
	public static StatusEnum checkDepends(String unitName, String depends) {
		// 没有依赖单元则返回可执行状态
		if (depends.trim().length() == 0) {
			return StatusEnum.executeAble;
		}
		String[] dependArray = depends.split(",");
		// 依赖单元有一个在失败单元表则本单元进入失败单元表
		for (int i = 0; i < dependArray.length; i++) {
			if (executeFail.contains(dependArray[i])) {
				StatusSwitch.executelessToExecuteFail(unitName);
				return StatusEnum.executeFail;
			}
		}

		// 依赖单元有一个在未执行单元表或正在执行单元表则本单元保留在未执行单元表
		for (int i = 0; i < dependArray.length; i++) {
			if (executeless.contains(dependArray[i])
					|| executing.contains(dependArray[i])) {
				return StatusEnum.executeless;
			}
		}
		// 否则返回可执行
		return StatusEnum.executeAble;
	}
	
	public static void choiceUnit() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException{
		logger.info("----"+I18N.getString("choice_execute_unit")+"----");
		
		for(int i = 0 ; i < executeless.size() ; i ++){
			System.out.println(executeless.get(i));
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		
		if(!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("exit")){
			
			if(!executeless.contains(input)){
			    executeless.clear();
				System.out.println(I18N.getString("rechoose"));
			} else{
				executeless.clear();
				executeless.add(input);
				executeTaskUnits(path);// 执行接口单元
			}
			initExecuteUnit(path);
			choiceUnit();
		} 
		br.close();
	}
}
