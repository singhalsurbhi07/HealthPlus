package com.example.healthplus.datamodels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activities {
	private String daily_goal;
	private String total_calories_burnt;
	private EachActivity[] activitiesArray;
	
	public Activities(String goal,String total,JSONArray obj){
		this.daily_goal = goal;
		this.total_calories_burnt = total;
		this.activitiesArray = getActivities(obj);
		
	}
	
	
	
	public String getDaily_goal() {
		return daily_goal;
	}



	public void setDaily_goal(String daily_goal) {
		this.daily_goal = daily_goal;
	}



	public String getTotal_calories_burnt() {
		return total_calories_burnt;
	}



	public void setTotal_calories_burnt(String total_calories_burnt) {
		this.total_calories_burnt = total_calories_burnt;
	}



	public EachActivity[] getActivitiesArray() {
		return activitiesArray;
	}



	public void setActivitiesArray(EachActivity[] activitiesArray) {
		this.activitiesArray = activitiesArray;
	}



	public EachActivity[] getActivities(JSONArray objArray){
		EachActivity[] activityArray = new EachActivity[objArray.length()];
		
			try {
				for(int i=0;i<objArray.length();i++){
					JSONObject obj = objArray.getJSONObject(i);
					String name  = obj.getString("activityParentName");
					String duration = obj.getString("duration");
					String calories = obj.getString("calories");
					String startTime = obj.getString("startTime");
					EachActivity act = new EachActivity(name,duration,calories,startTime);
					activityArray[i]=act;
					
					
				
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return activityArray;
			
			
		
	}
	
	public String[] getActivitiesNameArray(){
		String [] res = new String[activitiesArray.length];
		for(int i=0;i<activitiesArray.length;i++){
			res[i]=activitiesArray[i].getName();
		}
		return res;
		
	}
	
	public double[] getActivitiesCalArray(){
		double [] res = new double[activitiesArray.length];
		for(int i=0;i<activitiesArray.length;i++){
			double cal =Double.parseDouble(activitiesArray[i].getCalories_burnt());
			res[i]=cal;
		}
		return res;
		
	}
	
}


class EachActivity{

	
	String name;
	String duration;
	String calories_burnt;
	String startTime;
	
	public EachActivity(String name,String duration,String calories_burnt,String startTString){
		this.name = name;
		this.duration = duration;
		this.calories_burnt = calories_burnt;
		this.start_time= startTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCalories_burnt() {
		return calories_burnt;
	}
	public void setCalories_burnt(String calories_burnt) {
		this.calories_burnt = calories_burnt;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	String start_time;


}