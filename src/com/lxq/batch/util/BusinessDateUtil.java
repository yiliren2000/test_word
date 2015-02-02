package com.lxq.batch.util;

import com.lxq.util.DateUtil;


public class BusinessDateUtil {
	
	public static String getBusinessDate(){
	
		//获取系统当前日期yyyyMMdd;
		return DateUtil.getToday("yyyyMMdd");
	}
}
