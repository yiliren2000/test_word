package com.lxq.batch.util;

import com.lxq.util.DateUtil;


public class BusinessDateUtil {
	
	public static String getBusinessDate(){
	
		//��ȡϵͳ��ǰ����yyyyMMdd;
		return DateUtil.getToday("yyyyMMdd");
	}
}
