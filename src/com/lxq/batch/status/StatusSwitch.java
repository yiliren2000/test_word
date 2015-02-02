package com.lxq.batch.status;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 任务单元状态转化
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class StatusSwitch {

	/**未执行单元列表*/
	public static List executeless = new ArrayList();
	
	/**正在执行单元列表*/
	public static List executing = new ArrayList();
	
	/**执行失败单元列表*/
	public static List executeFail = new ArrayList();
	
	/**执行成功单元列表*/
	public static List executeSuccess = new ArrayList();

	/**
	 * 从未执行单元列表转向执行失败单元列表
	 * @param unitName 任务单元名称
	 */
	public static void executelessToExecuteFail(String unitName){
		executeless.remove(unitName);
		executeFail.add(unitName);
	}

	/**
	 * 从未执行单元列表转向执行成功单元列表
	 * @param unitName 任务单元名称
	 */
	public static void executelessToExecuteSuccess(String unitName){
		executeless.remove(unitName);
		executeSuccess.add(unitName);
	}
	
	/**
	 * 从未执行单元列表转向正在执行单元列表
	 * @param unitName 任务单元名称
	 */
	public static void executelessToExecuting(String unitName){
		executeless.remove(unitName);
		executing.add(unitName);
	}

	/**
	 * 从正在执行单元列表转向执行失败单元列表
	 * @param unitName 任务单元名称
	 */
	public static void executingToExecuteFail(String unitName){
		executing.remove(unitName);
		executeFail.add(unitName);
	}

	/**
	 * 从正在执行单元列表转向执行成功单元列表
	 * @param unitName 任务单元名称
	 */
	public static void executingToExecuteSuccess(String unitName){
		executing.remove(unitName);
		executeSuccess.add(unitName);
	}
	
}
