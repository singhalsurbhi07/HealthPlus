package com.example.healthplus.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONObject;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;


import com.example.healthplus.oauth.SerializableOauthData;

public class ApiCallHelper {
	
	OAuthData data = SerializableOauthData.getOauthData();
	String waterJson;
	String sleepJson;
	public String getSleepJson() {
		return sleepJson;
	}

	public void setSleepJson(String sleepJson) {
		this.sleepJson = sleepJson;
	}

	public String getFoodJson() {
		return foodJson;
	}

	public void setFoodJson(String foodJson) {
		this.foodJson = foodJson;
	}

	public String getActivitiesJson() {
		return activitiesJson;
	}

	public void setActivitiesJson(String activitiesJson) {
		this.activitiesJson = activitiesJson;
	}

	String foodJson;
	String activitiesJson;;
	
	public String getWaterModel() {
		return waterJson;
	}

	public void setWaterModel(String waterModel) {
		this.waterJson = waterModel;
	}

	public void getUserWaterData(){
		data = SerializableOauthData.getOauthData();
		data.http("/1/user/-/foods/log/water/date/"+DateUtil.getYesterdayDateString()+".json", new OAuthRequest() {
			private URL url;
			private URLConnection con;
			@Override
			public void onSetURL(String _url) {
				// This method is called once the final url is returned.
				System.out.println("_url-->"+_url);
				try {
					url = new URL(_url);
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); } 
			}

			@Override
			public void onSetHeader(String header, String value) {
				// This method is called for each header to add to the request.
				con.addRequestProperty(header, value);
			}

			@Override
			public void onReady() {
				// This method is called once url and headers are set.
				
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					System.out.println(total.toString());
					setWaterModel(total.toString());
					//JSONObject result = new JSONObject(total.toString());
					//waterComsumpt  = new WaterConsumeModel(result);
					
					
					
					
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
	}
	
	public void getUserSleepData(){
		data = SerializableOauthData.getOauthData();
		//data.http("/1/user/-/sleep/date/"+DateUtil.getTodaysDate()+".json", new OAuthRequest() {
		data.http("/1/user/-/sleep/date/2014-11-13.json", new OAuthRequest() {
			private URL url;
			private URLConnection con;
			@Override
			public void onSetURL(String _url) {
				// This method is called once the final url is returned.
				System.out.println("_url-->"+_url);
				try {
					url = new URL(_url);
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); } 
			}

			@Override
			public void onSetHeader(String header, String value) {
				// This method is called for each header to add to the request.
				con.addRequestProperty(header, value);
			}

			@Override
			public void onReady() {
				// This method is called once url and headers are set.
				
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					//System.out.println(total.toString());
					JSONObject result = new JSONObject(total.toString());
					//sleepObj = new SleepModel(result);
					setSleepJson(total.toString());
					
					
					
					
					
					
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
	}
	
	public void getUserFoodData(){
		data = SerializableOauthData.getOauthData();
		data.http("/1/user/-/foods/log/date/"+DateUtil.getTodaysDate()+".json", new OAuthRequest() {
			private URL url;
			private URLConnection con;
			@Override
			public void onSetURL(String _url) {
				// This method is called once the final url is returned.
				System.out.println("_url-->"+_url);
				try {
					url = new URL(_url);
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); } 
			}

			@Override
			public void onSetHeader(String header, String value) {
				// This method is called for each header to add to the request.
				con.addRequestProperty(header, value);
			}

			@Override
			public void onReady() {
				// This method is called once url and headers are set.
				
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					System.out.println(total.toString());
					JSONObject result = new JSONObject(total.toString());
					setFoodJson(total.toString());
					
					
					
					
					
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
	}
	
	public void getUserActivitiesData(){
		data = SerializableOauthData.getOauthData();
		data.http("/1/user/-/activities/date/"+DateUtil.getTodaysDate()+".json", new OAuthRequest() {
			private URL url;
			private URLConnection con;
			@Override
			public void onSetURL(String _url) {
				// This method is called once the final url is returned.
				System.out.println("_url-->"+_url);
				try {
					url = new URL(_url);
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); } 
			}

			@Override
			public void onSetHeader(String header, String value) {
				// This method is called for each header to add to the request.
				con.addRequestProperty(header, value);
			}

			@Override
			public void onReady() {
				// This method is called once url and headers are set.
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					System.out.println(total.toString());
					setActivitiesJson(toString());
					
					
					
					//JSONObject user = new JSONObject(result.getString("user").toString());
					
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
	}

}
