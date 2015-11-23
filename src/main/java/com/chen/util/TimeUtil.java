package com.chen.util;

import org.joda.time.DateTime;

public class TimeUtil {

	public static String getNowDate(){
		return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getNowTime(){
		return DateTime.now().toString("yyyy-MM-dd");
	}
}
