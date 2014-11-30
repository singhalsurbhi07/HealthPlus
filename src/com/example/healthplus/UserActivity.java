package com.example.healthplus;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.healthplus.oauth.SerializableOauthData;
import com.example.healthplus.utils.ApiCallHelper;
import com.example.healthplus.utils.DateUtil;
import com.example.healthplus.utils.LoadData;
import com.example.healthplus.utils.Units;
import com.example.healthplus.wifidirect.WiFiDirectActivity;
import com.nostra13.universalimageloader.core.ImageLoader;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends Activity {

	public static OAuthData data;
	TextView tvNameVal;
	TextView tvLocVal;
	TextView tvAboutMeVal;
	TextView tvDOBVal;
	TextView tvHeightVal;
	TextView tvWeightVal;
	TextView tvMemberSinceVal;
	ImageView ivPicVal;
	Button btnContinue;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		Intent i = getIntent();
		setUpViews();

		getUserData();

	}

	public void setUpViews(){
		tvNameVal = (TextView) findViewById(R.id.tvUserName);
		tvLocVal  = (TextView) findViewById(R.id.tvUserLoc);
		tvAboutMeVal = (TextView) findViewById(R.id.tvABoutMe);
		tvDOBVal = (TextView) findViewById(R.id.tvDOBVal);
		tvHeightVal = (TextView) findViewById(R.id.tvHeightVal);
		tvWeightVal = (TextView) findViewById(R.id.tvWeightVal);
		tvMemberSinceVal = (TextView) findViewById(R.id.tvMemberSinceVal);
		ivPicVal = (ImageView) findViewById(R.id.ivUserPic);
		btnContinue = (Button) findViewById(R.id.btnContinue);

	}

	public void getUserData(){
		data = SerializableOauthData.getOauthData();
		data.http("/1/user/-/profile.json", new OAuthRequest() {
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
					JSONObject result = new JSONObject(total.toString());
					JSONObject user = new JSONObject(result.getString("user").toString());
					displayUserInfo(user);
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
	}

	public void displayUserInfo(JSONObject user){
		System.out.println(user.toString());
		getUserPic(user);
		tvNameVal.setText(getName(user));
		tvLocVal.setText(getLocation(user));
		tvAboutMeVal.setText(getAboutMe(user));
		tvDOBVal.setText(getDOB(user));
		tvHeightVal.setText(getHeight(user));
		tvWeightVal.setText(getWeight(user));
		tvMemberSinceVal.setText(getMemberSince(user));
		SharedPreferences sharedpreferences = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString("UserName", getName(user));
		editor.commit();




	}

	private void getUserPic(JSONObject obj){
		ImageLoader loader = ImageLoader.getInstance();

		try {
			if(obj.has("avatar")){
				loader.displayImage(obj.getString("avatar"), ivPicVal);
			}
			else if(obj.has("avatar150")){
				loader.displayImage(obj.getString("avatar150"), ivPicVal);
			}else{
				ivPicVal.setImageResource(R.drawable.ic_user_def);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private String getName(JSONObject obj){


		try {
			if(obj.has("fullName")){
				return obj.getString("fullName");
			}else if(obj.has("displayName")){
				return obj.getString("displayName");
			}else if(obj.has("nickname")){
				return obj.getString("nickname");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "No Name specified";
	}
	private String getAboutMe(JSONObject obj){

		try {
			if(obj.has("aboutMe")){
				return obj.getString("aboutMe");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	private String getDOB(JSONObject obj){

		try {
			if(obj.has("dateOfBirth")){
				return obj.getString("dateOfBirth");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Not Specified";

	}



	private String getHeight(JSONObject obj){
		StringBuilder height = new StringBuilder();

		try {
			if(obj.has("height")){
				height.append(obj.getString("height")+" ");
				System.out.println(height.toString());
			}
			if(obj.has("heightUnit")){
				height.append(Units.getHeightUnits(obj.getString("heightUnit")));
				System.out.println(height.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(height.toString().length()>0){
			return height.toString();
		}else{
			return "Height not specified";
		}
	}

	private String getWeight(JSONObject obj){
		StringBuilder weight = new StringBuilder();

		try {
			if(obj.has("weight")){
				weight.append(obj.getString("weight")+" ");
				//System.out.println(height.toString());
			}
			if(obj.has("weightUnit")){
				weight.append(Units.getWeightUnits(obj.getString("weightUnit")));
				//System.out.println(height.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(weight.toString().length()>0){
			return weight.toString();
		}else{
			return "weight not specified";
		}
	}

	private String getMemberSince(JSONObject obj){
		try{
			if(obj.has("memberSince")){
				return obj.getString("memberSince");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ("date not specifies");
	}



	private String getLocation(JSONObject obj){
		StringBuilder loc = new StringBuilder();
		try {
			if(obj.has("city")){
				System.out.println(" city");
				loc.append(obj.getString("city"));
				loc.append(", ");
			} 
			if(obj.has("state")){
				loc.append(obj.getString("state"));
				loc.append(", ");
			}
			if(obj.has("country")){
				loc.append(obj.getString("country"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if(loc.toString().length() > 0){
			return loc.toString();
		}else{
			return "No Location mentioned";
		}

	}

	public void onContinue(View v){
		Intent i = new Intent(UserActivity.this,MenuActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onSyncAction(MenuItem mi) {
		// handle click here
		Log.d("UserActivity","OnSyncAction");

		ApiCallHelper helper = new ApiCallHelper();		
		String date = DateUtil.getYesterdayDateString();

		/*helper.getUserWaterData(date);
		helper.getUserSleepData(date);
		helper.getUserFoodData(date);
		helper.getUserActivitiesData(date);
		*/
		//Test ot load ten days of history data
		LoadData insertData = new LoadData();
		insertData.loadTenDaysData();

	}

	public void onWiFiConnect(MenuItem mi){
		Intent i = new Intent(this,WiFiDirectActivity.class);
		startActivity(i);
	}
}

