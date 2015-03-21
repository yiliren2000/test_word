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
 * ������������
 * 
 * @author xueqingli@foxmail.com
 * 
 */

public class ServerStart {
	
	/** ��ȡ��־���� */
	public static Logger logger = Logger.getLogger(ServerStart.class);
	/** ��ȡδִ�е�Ԫ�б� */
	private static List executeless = StatusSwitch.executeless;
	/** ��ȡ����ִ�е�Ԫ�б� */
	private static List executing = StatusSwitch.executing;
	/** ��ȡִ��ʧ�ܵ�Ԫ�б� */
	private static List executeFail = StatusSwitch.executeFail;
	
	private static String path;

	/**
	 * ���߳������ӿ����
	 * 
	 * @param path
	 *            ��Ŀ���ڵ�·�����������𣺿գ�web��Ŀ��web��Ŀ·��
	 * @throws Exception
	 */
	public static void call(String path) throws Exception {

		// ��ʼ��log4j��־
		Log4j log4j = new Log4j();
		log4j.setPath(path);
		log4j.setConfigFile("etc/module/log4j.properties");
		log4j.load();
		
		//	���ع��ʻ��ļ�
        I18N i18n = new I18N();
        i18n.setPath(path);
        i18n.setConfigFile("etc/language/message_locale.properties");
        i18n.load();
		
		// ����ϵͳģ�������ļ�
		SystemLoader.load(path, "etc/systemConfig.xml");
		logger.info("*********" + DateUtil.getNowTime() + " "+I18N.getString("load_system_module")+"*********");
		loadSystemModules(path);// ����ϵͳģ��
		// ����ִ�е�Ԫ�����ļ�
		TaskLoader.load(path, "etc/task.xml");
		initExecuteUnit(path);
		logger.info("*********" + DateUtil.getNowTime() + " "+I18N.getString("load_task_unit")+ "*********");
		
		// ��������Զ�ִ�У�����Ҫ�ֹ�ѡ��ִ�е�Ԫ
		if(!SystemLoader.getSystemConfig().isAutoExecute()){
			choiceUnit();//ѡ��ִ�е�Ԫ
		} else{
			executeTaskUnits(path);// ִ�нӿڵ�Ԫ
		}

		logger.info("*********" + DateUtil.getNowTime() + " "+I18N.getString("close_system_module")+ "*********");
		// �ر�ϵͳģ��
		closeSystemModules(path);
	}

	/**
	 * ����ϵͳ����ģ��
	 * 
	 * @throws Exception
	 */
	public static void loadSystemModules(String path) throws Exception {
		// ��ȡϵͳ����
		SystemConfig systemConfig = SystemLoader.getSystemConfig();
		// ��ȡϵͳ����ģ�鼯��
		List modules = systemConfig.getModules();
		// ѭ����������ϵͳ����ģ��
		for (int i = 0; i < modules.size(); i++) {
			// ��ȡ����ģ��
			AbstractModule module = (AbstractModule) modules.get(i);
			logger.info("**"+I18N.getString("load")+" " + module.getDescribe() + "**");
			// ����ϵͳ����ģ��
			module.load();
		}
	}

	/**
	 * �ر�ϵͳ����ģ��
	 * 
	 * @throws Exception
	 */
	public static void closeSystemModules(String path) throws Exception {

		// ��ȡϵͳ����
		SystemConfig systemConfig = SystemLoader.getSystemConfig();
		// ��ȡϵͳ����ģ�鼯��
		List modules = systemConfig.getModules();
		// ѭ����������ϵͳ����ģ��
		for (int i = 0; i < modules.size(); i++) {
			// ��ȡ����ģ��
			AbstractModule module = (AbstractModule) modules.get(i);

			logger.info("**"+I18N.getString("close")+" " + module.getDescribe() + "**");
			// ����ϵͳ����ģ��
			module.close();
		}

	}

	/**
	 * ��ʼ��δִ�е�Ԫ�б�
	 * 
	 * @param path
	 *            ��Ŀ·��
	 */
	public static void initExecuteUnit(String path) {
		// ��ȡ���нӿڵ�Ԫ�б�
		Map taskUnits = TaskLoader.getTask().getExecuteUnits();
		// �����нӿڵ�Ԫ���Ƴ�ʼ��δִ�е�Ԫ��
		Set keys = taskUnits.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			executeless.add(it.next());
		}
	}

	/**
	 * ִ������Ԫ
	 * 
	 * @param path
	 *            ��Ŀ·��
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InterruptedException
	 */
	public static void executeTaskUnits(String path)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InterruptedException {
		List threads = new ArrayList();

		// ��ȡ���нӿڵ�Ԫ�б�
		Map taskUnits = TaskLoader.getTask().getExecuteUnits();

		while (true) {
			// ����δִ�е�Ԫ��
			for (int i = 0; i < executeless.size(); i++) {
				// ��ȡδִ�еĽӿڵ�Ԫ
				AbstractTaskUnit taskUnit = (AbstractTaskUnit) taskUnits
						.get(executeless.get(i));
				// ��ȡ�ӿڵ�Ԫ����
				String name = taskUnit.getName();
				// ��ȡ�ӿڵ�Ԫִ������
				String executeDate = taskUnit.getExecuteDate();
				// ִ�������ǵ�ǰҵ������
				if (isExecuteDate(executeDate)) {
					// ��ȡ�ӿڵ�Ԫ�ĵ�Ԫ����
					String depends = taskUnit.getDepends();
					// ���������Ԫ���жϴ˵�Ԫ��һ������
					StatusEnum executeStatus = checkDepends(name, depends);
					// �����ǰ��Ԫ��ִ��,��û�г�������߳���
					if (StatusEnum.executeAble == executeStatus
							&& executing.size() < SystemLoader
									.getSystemConfig().getThreadNum()) {
						// ����ǰ��Ԫת������ִ�е�Ԫ�б�
						StatusSwitch.executelessToExecuting(name);
						Thread thread = new Thread(taskUnit);
						threads.add(thread);
						thread.start();
					}
					// ִ�����ڲ��ǵ�ǰ���ڣ�����Ϊ�˵�Ԫִ�гɹ�
				} else {
					StatusSwitch.executelessToExecuteSuccess(name);
				}
			}
			/*
			 * ���δִ�е�Ԫ���еĽӿڵ�Ԫ�Ѿ�Ϊ�գ� ��֤�����еĽӿڵ�Ԫ�Ѿ�ִ����ϣ���ô����ѭ����
			 */
			if (executeless.size() == 0) {
				break;
			}
		}
		// �ȴ������߳���ֹ
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
	 * �ж�ҵ�������Ƿ�Ϊִ������
	 * 
	 * @param executeDate
	 *            ִ������
	 * @return
	 */
	public static boolean isExecuteDate(String executeDate) {

		// ִ������Ϊ�գ���Ĭ�ϵ�ǰ����ִ��
		if (executeDate.trim().length() == 0) {
			return true;
		}
		String year = executeDate.substring(0, 4);// ��ȡ�ӿڵ�Ԫִ���������yyyy
		String month = executeDate.substring(4, 6);// ��ȡ�ӿڵ�Ԫִ�������·�MM
		String day = executeDate.substring(6, 8);// ��ȡ�ӿڵ�Ԫִ�������շ�dd
		String today = BusinessDateUtil.getBusinessDate();// ��ȡϵͳ��ǰ����yyyyMMdd
		String cur_year = today.substring(0, 4);// ��ȡϵͳ��ǰ���yyyy
		String cur_month = today.substring(4, 6);// ��ȡϵͳ��ǰ�·�MM
		String cur_day = today.substring(6, 8);// ��ȡϵͳ��ǰ�շ�dd
		if (year.equals("YYYY")) {
			year = cur_year;
		}
		if (month.equals("MM")) {
			month = cur_month;
		}
		if (day.equals("DD")) {
			day = cur_day;
		}
		// ���ҵ��������ִ������
		if (today.equals(year + month + day)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param unitName
	 *            ����Ԫ����
	 * @param depends
	 *            ��ǰ�ӿڵ�Ԫ������ִ�е�Ԫ�����ִ�е�Ԫ֮���Զ��Ÿ�����
	 * @return ����Ԫ��ִ��״̬��δִ�С�����ִ�С�ִ��ʧ�ܣ�
	 */
	public static StatusEnum checkDepends(String unitName, String depends) {
		// û��������Ԫ�򷵻ؿ�ִ��״̬
		if (depends.trim().length() == 0) {
			return StatusEnum.executeAble;
		}
		String[] dependArray = depends.split(",");
		// ������Ԫ��һ����ʧ�ܵ�Ԫ���򱾵�Ԫ����ʧ�ܵ�Ԫ��
		for (int i = 0; i < dependArray.length; i++) {
			if (executeFail.contains(dependArray[i])) {
				StatusSwitch.executelessToExecuteFail(unitName);
				return StatusEnum.executeFail;
			}
		}

		// ������Ԫ��һ����δִ�е�Ԫ�������ִ�е�Ԫ���򱾵�Ԫ������δִ�е�Ԫ��
		for (int i = 0; i < dependArray.length; i++) {
			if (executeless.contains(dependArray[i])
					|| executing.contains(dependArray[i])) {
				return StatusEnum.executeless;
			}
		}
		// ���򷵻ؿ�ִ��
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
				executeTaskUnits(path);// ִ�нӿڵ�Ԫ
			}
			initExecuteUnit(path);
			choiceUnit();
		} 
		br.close();
	}
}
