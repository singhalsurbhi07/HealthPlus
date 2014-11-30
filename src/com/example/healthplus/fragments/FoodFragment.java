package com.example.healthplus.fragments;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthplus.R;
import com.example.healthplus.datamodels.FoodModel;
import com.example.healthplus.oauth.SerializableOauthData;
import com.example.healthplus.utils.DateUtil;

public class FoodFragment extends Fragment {
	OAuthData data;
	View view;
	FoodModel foodObj;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      view = inflater.inflate(R.layout.activity_food_fragment, container, false);
      getUserFoodData();
      return view;
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
					JSONObject summary = new JSONObject(result.getString("summary"));
					foodObj = new FoodModel(summary.getString("calories"), summary.getString("carbs"), summary.getString("fat"), summary.getString("fiber"), summary.getString("protein"), summary.getString("sodium"));
					PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);

					mPieChart.addPieSlice(new PieModel("Calories", Float.parseFloat(foodObj.getCal()), Color.parseColor("#FE6DA8")));
					mPieChart.addPieSlice(new PieModel("Carbs",Float.parseFloat(foodObj.getCarbs()) , Color.parseColor("#56B7F1")));
					mPieChart.addPieSlice(new PieModel("Fat", Float.parseFloat(foodObj.getFat()), Color.parseColor("#CDA67F")));
					mPieChart.addPieSlice(new PieModel("Fiber", Float.parseFloat(foodObj.getFiber()), Color.parseColor("#FED70E")));
					mPieChart.addPieSlice(new PieModel("Protein", Float.parseFloat(foodObj.getProtein()), Color.parseColor("#00CC00")));
					mPieChart.addPieSlice(new PieModel("Sodium", Float.parseFloat(foodObj.getSodium()), Color.parseColor("#CC00FF")));
				
					mPieChart.startAnimation();
					
					
					
					
					
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
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
