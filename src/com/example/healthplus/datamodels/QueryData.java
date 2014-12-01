package com.example.healthplus.datamodels;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class QueryData {
	public static String[] functions = new String[]{
        "Total","Average"
    };
	
	public static String[] Tables = new String[]{
        "Sleep","Water","Calories Intake","Calories Burn"
    };
	
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
}
