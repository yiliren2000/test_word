package com.lxq.batch.status;

/**
 *
 * 任务单元的状态
 * 
 * @author xueqingli@foxmail.com
 *
 */
public class StatusEnum {
	
	/**未执行*/
	public static final StatusEnum executeless = new StatusEnum(1);
	
	/**可执行*/
	public static final StatusEnum executeAble = new StatusEnum(2);
	
	/**正在执行*/
	public static final StatusEnum executing = new StatusEnum(3);
	
	/**执行失败*/
	public static final StatusEnum executeFail = new StatusEnum(4);
	
	/**执行成功*/
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
