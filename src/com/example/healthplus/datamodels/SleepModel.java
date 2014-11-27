package com.example.healthplus.datamodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.ArrayAdapter;

public class SleepModel {
	
	String totalMinutesAsleep;
	String totalTimeInBed;
	
	//List<Map<String,String>> minuteData = new ArrayList<>();
	List<List<EachSleepModel>> minuteData = new ArrayList<>();

	private List<EachSleepModel> InitialiseMinuteData(JSONArray array, String efficiency){
		List<EachSleepModel>res = new ArrayList<>();
		try{
			for(int i=0;i<array.length();i++){
				JSONObject obj = array.getJSONObject(i);
				//Log.d("sleepModel InitialiseMinuteData",obj.getString("dateTime"));
				EachSleepModel temp = new EachSleepModel(obj.getString("dateTime"), obj.getString("value"), efficiency);
				res.add(temp);			
			}
		}catch(Exception  e){
			e.printStackTrace();
		}
		return res;		
	}

	public SleepModel(JSONObject obj){
		try {
			this.totalMinutesAsleep = new JSONObject(obj.getString("summary")).getString("totalMinutesAsleep");
			Log.d("sleepModel",totalMinutesAsleep);
			this.totalTimeInBed = new JSONObject(obj.getString("summary")).getString("totalTimeInBed");
			Log.d("sleepModel",totalTimeInBed);
			JSONArray sleepArray = obj.getJSONArray("sleep");
			for(int i=0;i<sleepArray.length();i++){
				JSONArray minuteDataArray = sleepArray.getJSONObject(i).getJSONArray("minuteData");
				minuteData.add(InitialiseMinuteData(minuteDataArray,sleepArray.getJSONObject(i).getString("efficiency")));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTotalMinutesAsleep() {
		return totalMinutesAsleep;
	}

	public void setTotalMinutesAsleep(String totalMinutesAsleep) {
		this.totalMinutesAsleep = totalMinutesAsleep;
	}

	public String getTotalTimeInBed() {
		return totalTimeInBed;
	}

	public void setTotalTimeInBed(String totalTimeInBed) {
		this.totalTimeInBed = totalTimeInBed;
	}

	public List<List<EachSleepModel>> getMinuteData() {
		return minuteData;
	}
}