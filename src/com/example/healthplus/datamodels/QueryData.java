package com.example.healthplus.datamodels;

import java.util.HashMap;
import java.util.Map;

public class QueryData {
	public static String[] functions = new String[]{
        "Total","Average"
    };
	
	public static String[] Tables = new String[]{
        "Sleep","Water","Calories Intake","Calories Burn"
    };
	
	public static Map<String,String> tableMap = new HashMap<>();
	//public static Map<String,String> functionMap = new HashMap<>();
	
	public static void initialiseMap(){
		tableMap.put(Tables[0], "Sleep");
		tableMap.put(Tables[1], "Water");
		tableMap.put(Tables[2], "Food");
		tableMap.put(Tables[3], "Activities");
		
		
		
	}
	
//	public static String formQuery(String functionType, String tableType, String startDate, String endDate){
//		StringBuilder query = new StringBuilder();
//		query.append("Select");
//		if(functionType.equals(functions[0])){
//			query.append("Sum(");
//		}else if(functionType.equals(functions[1])){
//			query.append("Average(");
//		}
//		//query.append("from "+tableMap.get(tableType)+" where startDate ")
//	}
}
