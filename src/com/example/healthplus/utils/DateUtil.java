package com.example.healthplus.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DateUtil {
	public static String getTodaysDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
		return dateFormat.format(cal.getTime()).toString();

	}

	public static String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);    
		return dateFormat.format(cal.getTime());
	}

	public static HashMap<Integer,String> getTenDaysDateMap() {

		HashMap<Integer, String> dateMap = new HashMap<Integer, String>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String newDate = null;

		for (int i = 1; i <= 10; i++) {

			cal.add(Calendar.DATE, -1);			
			newDate = dateFormat.format(cal.getTime());			
			dateMap.put(i, newDate);
		}

		return dateMap;		
	}
}
