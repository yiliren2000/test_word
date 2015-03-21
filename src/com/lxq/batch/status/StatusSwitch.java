package com.lxq.batch.status;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ����Ԫ״̬ת��
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class StatusSwitch {

	/**δִ�е�Ԫ�б�*/
	public static List executeless = new ArrayList();
	
	/**����ִ�е�Ԫ�б�*/
	public static List executing = new ArrayList();
	
	/**ִ��ʧ�ܵ�Ԫ�б�*/
	public static List executeFail = new ArrayList();
	
	/**ִ�гɹ���Ԫ�б�*/
	public static List executeSuccess = new ArrayList();

	/**
	 * ��δִ�е�Ԫ�б�ת��ִ��ʧ�ܵ�Ԫ�б�
	 * @param unitName ����Ԫ����
	 */
	public static void executelessToExecuteFail(String unitName){
		executeless.remove(unitName);
		executeFail.add(unitName);
	}

	/**
	 * ��δִ�е�Ԫ�б�ת��ִ�гɹ���Ԫ�б�
	 * @param unitName ����Ԫ����
	 */
	public static void executelessToExecuteSuccess(String unitName){
		executeless.remove(unitName);
		executeSuccess.add(unitName);
	}
	
	/**
	 * ��δִ�е�Ԫ�б�ת������ִ�е�Ԫ�б�
	 * @param unitName ����Ԫ����
	 */
	public static void executelessToExecuting(String unitName){
		executeless.remove(unitName);
		executing.add(unitName);
	}

	/**
	 * ������ִ�е�Ԫ�б�ת��ִ��ʧ�ܵ�Ԫ�б�
	 * @param unitName ����Ԫ����
	 */
	public static void executingToExecuteFail(String unitName){
		executing.remove(unitName);
		executeFail.add(unitName);
	}

	/**
	 * ������ִ�е�Ԫ�б�ת��ִ�гɹ���Ԫ�б�
	 * @param unitName ����Ԫ����
	 */
	public static void executingToExecuteSuccess(String unitName){
		executing.remove(unitName);
		executeSuccess.add(unitName);
	}
	
}
