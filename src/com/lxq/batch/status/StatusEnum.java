package com.lxq.batch.status;

/**
 *
 * ����Ԫ��״̬
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class StatusEnum {
	
	/**δִ��*/
	public static final StatusEnum executeless = new StatusEnum(1);
	
	/**��ִ��*/
	public static final StatusEnum executeAble = new StatusEnum(2);
	
	/**����ִ��*/
	public static final StatusEnum executing = new StatusEnum(3);
	
	/**ִ��ʧ��*/
	public static final StatusEnum executeFail = new StatusEnum(4);
	
	/**ִ�гɹ�*/
	public static final StatusEnum executeSuccess = new StatusEnum(5);

	private int num;
	
	public StatusEnum(int num){
		this.num = num;
	}
	
	public boolean equals(StatusEnum obj) {
		
		return this.num == obj.num;
	}

	public int hashCode() {
		return super.hashCode();
	}
}
