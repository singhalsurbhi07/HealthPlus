package com.example.healthplus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.TreeMap;

import org.json.*;

import io.oauth.OAuth;

import com.example.healthplus.database.MySQLiteHelper;
import com.example.healthplus.oauth.SerializableOauthData;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OAuthCallback{
	//private static final String consumerKey = "jNaTn8A9BMCdqpmkHATVCdg4eHY";
	private static final String consumerKey = "f79f35c5e78b434fba7127262827b5f2";

	private static final String consumerSecretKey = "d31d57c0b96842548642efc94d23dabf";
	
	
	TextView token;
	TextView tokenSecret;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MySQLiteHelper sqlHelper = new MySQLiteHelper(this);
		SerializableOauthData.setSqlHelper(sqlHelper);
		OAuthData data = SerializableOauthData.getOauthData();
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
        		cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
		if(data == null){
		final OAuth oauth = new OAuth(MainActivity.this);
		//oauth.initialize(consumerKey);
		oauth.initialize("jNaTn8A9BMCdqpmkHATVCdg4eHY");
		token = (TextView)findViewById(R.id.token);
		tokenSecret = (TextView)findViewById(R.id.tokenSecret);
		StrictMode.ThreadPolicy policy = new
				StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
	
		oauth.popup("fitbit", MainActivity.this);
		}else{
			Intent i = new Intent(this, UserActivity.class);
			//i.putExtra("OauthData", data);
			startActivity(i);
			
		}
			
			
			

//				Parameters pars = new Parameters(consumerKey, consumerSecretKey, result.token, result.secret);
//				TreeMap<String,String> param_map = pars.getParameters();
//				for(Map.Entry<String,String> entry : param_map.entrySet()) {
//					
//					  System.out.println(entry.getKey() + " => " + entry.getValue());
//				}
				//public void getPublicTimeline() throws JSONException {
//			        FitBitClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
//			            @Override
//			            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
//			                // If the response is JSONObject instead of expected JSONArray
//			            }
//			            
//			            @Override
//			            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray timeline) {
//			                // Pull out the first event on the public timeline
//			                
//							try {
//								JSONObject firstEvent = (JSONObject) timeline.get(0);
//				                String tweetText;
//								tweetText = firstEvent.getString("text");
//								 System.out.println(tweetText);
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//			                // Do something with the response
//			               
//			            }
//			        });
			    
				
				
			
	}
	
	public void onFinished(OAuthData result) {
		//Log.d("MainActivity",result.error);
		//System.out.println("OnFinished");
		//System.out.println(result.token);
		//Log.d("MainActivity",result.toString());
		
		//token.setText(result.token);
		//tokenSecret.setText(result.secret);
		SerializableOauthData.setOauthData(result);
		//System.out.println("result.request-->"+result.request.toString());
		//SerializableOauthData data = new SerializableOauthData(result);
		Intent i = new Intent(this, UserActivity.class);
		//i.putExtra("OauthData", data);
		startActivity(i);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
