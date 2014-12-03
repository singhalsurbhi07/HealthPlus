package com.example.healthplus.datamodels;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class QueryData {
	public static String[] functions = new String[]{
		"Total","Average"
	};

	public static String[] Tables = new String[]{
<<<<<<< HEAD
        "Sleep","Water","Calories Intake","Calories Burn"
    };
	
	public static String[] goodDaysCategory = new String[]{"Good Sleep","Good Activities","Overall Healthy Days"};
	
	
=======
		"Sleep","Water","Calories Intake","Calories Burn"
	};

>>>>>>> Added join query
	public static Map<String,String> tableMap = new HashMap<>();
	public static Map<String,String> columnMap = new HashMap<>();
	//public static Map<String,String> functionMap = new HashMap<>();

	public static void initialiseMap(){
		tableMap.put(Tables[0], "SLEEP");
		tableMap.put(Tables[1], "WATER");
		tableMap.put(Tables[2], "FOOD");
		tableMap.put(Tables[3], "ACTIVITIES");
		columnMap.put(Tables[0], "TOTAL_MINUTES_ASLEEP");
		columnMap.put(Tables[1], "AMOUNT");
		columnMap.put(Tables[2], "CALORIES");
		columnMap.put(Tables[3], "CALORIES_OUT");

	}
<<<<<<< HEAD
	
	public static String bestSleepDays(String startDate, String endDate){
		String bestSleep = null;
		bestSleep = "Select COUNT(*)  from sleep " +
		"where total_minutes_asleep >= 360 and Date  between '" + startDate + "' and '" +endDate +"'";
		return bestSleep; 
		}
	
	
	
	
	
=======

>>>>>>> Added join query
	public static String formQuery(String functionType, String tableType, String startDate, String endDate){
		initialiseMap();
		StringBuilder query = new StringBuilder();
		query.append("Select ");
		if(functionType.equals(functions[0])){
			query.append(" Sum("+columnMap.get(tableType)+") from "+tableMap.get(tableType) );
		}else if(functionType.equals(functions[1])){
			query.append(" AVERAGE("+columnMap.get(tableType)+") from "+tableMap.get(tableType) );
		}
		query.append(" where DATE BETWEEN '"+startDate+"' and '"+endDate+"' group by "+columnMap.get(tableType) );
		Log.d("QueryData formQuery",query.toString());
		return query.toString();
	}
	
	public String bestSleepDays(String startDate, String endDate){
	String bestSleep = null;
	bestSleep = " Select COUNT(*)  from sleep " +
			" where total_minutes_asleep >= 360 and Date  between '" + startDate + "' and '" +endDate +"'";
	return bestSleep; 
	}
	
	public String bestActivityDays(String startDate, String endDate){
		String bestActivity = null;
		bestActivity = " Select COUNT(*)  from Activities " +
				" where steps >= 8000 and calories_out>= 1000 and Date  between '" + startDate + "' and '" +endDate +"'";
		return bestActivity; 
	}
	
	public String bestFoodDays(String startDate, String endDate){
		String bestFood = null;
		bestFood = " Select COUNT(*) from food F , Activities A " +
				" where F.calories <= A.calories_out and A.Date  between '" + startDate + "' and '" +endDate +"'";
		return bestFood; 
	}
}
