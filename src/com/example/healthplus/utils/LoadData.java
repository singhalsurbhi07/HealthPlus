package com.example.healthplus.utils;

import java.util.HashMap;

import android.util.Log;

import com.example.healthplus.utils.DateUtil;
import com.example.healthplus.utils.ApiCallHelper;

public class LoadData {
	
	ApiCallHelper helperObj = new ApiCallHelper();
	
	public void loadTenDaysData() {
		
		HashMap<Integer, String> dateMap =  DateUtil.getTenDaysDateMap();
		
		for (String dateStr : dateMap.values()) {		
			Log.d(" LoadData Class ", " call to synchronized method ");
			insertWater(dateStr);
			insertSleep(dateStr);
			insertActivity(dateStr);
			insertFood(dateStr);
			//System.out.println(" Date = " + dateStr);
		}
	}
	
	private synchronized void insertWater(String dateStr) {
		helperObj.getUserWaterData(dateStr);	
	}
	
	private synchronized void insertSleep(String dateStr) {
		helperObj.getUserSleepData(dateStr);
	}
	
	private synchronized void insertFood(String dateStr) {
		helperObj.getUserFoodData(dateStr);	
	}
	
	private synchronized void insertActivity(String dateStr) {
		helperObj.getUserActivitiesData(dateStr);	
	}
}