package com.example.healthplus.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Units {
	private static Map<String,List<String>> unitMap = new HashMap<>();
	
	
	private static void initializeMap(){
		ArrayList<String> durationList = new ArrayList<String>(
			    Arrays.asList("milliseconds", "milliseconds", "milliseconds"));
		unitMap.put("duration", durationList);
		ArrayList<String> distanceList = new ArrayList<String>(
			    Arrays.asList("miles", "kilometers", "kilometers"));
		unitMap.put("distance", distanceList);
		ArrayList<String> elevationList = new ArrayList<String>(
			    Arrays.asList("feet", "meters", "meters"));
		unitMap.put("elevation", elevationList);
		ArrayList<String> heightList = new ArrayList<String>(
			    Arrays.asList("inches", "centimeters", "centimeters"));
		unitMap.put("height", heightList);
		ArrayList<String> weightList = new ArrayList<String>(
			    Arrays.asList("pounds", "stone", "kilograms"));
		unitMap.put("weight", weightList);
		ArrayList<String> measurementsList = new ArrayList<String>(
			    Arrays.asList("inches", "centimeters", "centimeters"));
		unitMap.put("measurements", measurementsList);
		ArrayList<String> liquidsList = new ArrayList<String>(
			    Arrays.asList("fl oz", "milliliters", "milliliters"));
		unitMap.put("liquids", liquidsList);
		
	}
	
	private static int getIndex(String userUnit){
		if(userUnit.equals("en_US")){
			return 0;
		}else if(userUnit.equals("en_UK")){
			return 1;
		}else if(userUnit.equals("METRIC")){
			return 2;
		}
		return -1;
	}
	
	private static boolean isMapInitialised(){
		return (!(unitMap.isEmpty()));
		
	}
	
	public static String getHeightUnits(String userUnit){
		if(!isMapInitialised()){
			System.out.println("need to intialise map");
			initializeMap();
		}
		
		System.out.println("units userUnit"+userUnit);
		int index = getIndex(userUnit);
		System.out.println("units userUnit"+index);
		if(unitMap.get("height").get(index)!=null){
			System.out.println(unitMap.get("height").get(index)!=null);
		}else{
			System.out.println("value null");

		}
		
		
		return unitMap.get("height").get(index);
		
	}
	
	public static String getWeightUnits(String userUnit){
		if(!isMapInitialised()){
			//System.out.println("need to intialise map");
			initializeMap();
		}
		
		//System.out.println("units userUnit"+userUnit);
		int index = getIndex(userUnit);
		//System.out.println("units userUnit"+index);
		if(unitMap.get("weight").get(index)!=null){
			System.out.println(unitMap.get("height").get(index)!=null);
		}else{
			System.out.println("value null");

		}
		
		
		return unitMap.get("weight").get(index);
		
	}

}
