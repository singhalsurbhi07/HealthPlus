package com.example.healthplus.fragments;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthplus.R;
import com.example.healthplus.datamodels.EachSleepModel;
import com.example.healthplus.datamodels.SleepModel;
import com.example.healthplus.oauth.SerializableOauthData;
import com.example.healthplus.utils.DateUtil;

public class SleepFragment extends Fragment {
	View view;
	OAuthData data;
	SleepModel sleepObj;
	
	private int getColor(String val){
		int col=0;
		switch(val){
		case "1" : col = Color.parseColor("#6699FF");
					break;
		case "2" : col = Color.parseColor("#990033");
					break;
		case "3" :col = Color.parseColor("#0066CC");
					break;
		
		}
		return col;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      view = inflater.inflate(R.layout.activity_sleep_fragment, container, false);
      getUserSleepData();
      //ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
      BarChart mBarChart = (BarChart) view.findViewById(R.id.barchart);
//      mBarChart.setBarWidth(.1f);
//      mBarChart.setBarMargin(.1f);
      List<List<EachSleepModel>> minutesData = new ArrayList<>();
      minutesData = sleepObj.getMinuteData();
      
      for(int i=0;i<minutesData.size();i++){
    	  List<EachSleepModel> temp = new ArrayList<>();
    	  temp= minutesData.get(i);
    	  Log.d("SleepFragmentr minutesData",String.valueOf(temp.size()));
    	  //ValueLineSeries series = new ValueLineSeries();
    	  //series.setColor(8046847);
          //series.setColor(0xFF56B7F1);
    	  for(int j=0;j<temp.size();j++){
    		  EachSleepModel entry = temp.get(j);
    		  System.out.println(entry.getTime()+" "+entry.getVal()+" "+entry.getEfficiency());
    		  //mBarChart.addBar(new BarModel(entry.getTime(),Float.parseFloat(entry.getEfficiency()), getColor(entry.getVal())));
    		  mBarChart.addBar(new BarModel(entry.getTime(),2.f, getColor(entry.getVal())));
    		  
    	  }
    	  
    	  
      }
      mBarChart.startAnimation();
      //mCubicValueLineChart.startAnimation();
      
      //BarChart mBarChart = (BarChart) view.findViewById(R.id.barchart);
      
      
     
//      mBarChart.addBar(new BarModel(2.3f, 0xFF123456));
//      mBarChart.addBar(new BarModel(2.f,  0xFF343456));
//      mBarChart.addBar(new BarModel(3.3f, 0xFF563456));
//      mBarChart.addBar(new BarModel(1.1f, 0xFF873F56));
//      mBarChart.addBar(new BarModel(2.7f, 0xFF56B7F1));
//      mBarChart.addBar(new BarModel(2.f,  0xFF343456));
//      mBarChart.addBar(new BarModel(0.4f, 0xFF1FF4AC));
//      mBarChart.addBar(new BarModel(4.f,  0xFF1BA4E6));
      
      
      

//      ValueLineSeries series = new ValueLineSeries();
//      series.setColor(0xFF56B7F1);
//
//      series.addPoint(new ValueLinePoint("Jan", 2.4f));
//      series.addPoint(new ValueLinePoint("Feb", 3.4f));
//      series.addPoint(new ValueLinePoint("Mar", .4f));
//      series.addPoint(new ValueLinePoint("Apr", 1.2f));
//      series.addPoint(new ValueLinePoint("Mai", 2.6f));
//      series.addPoint(new ValueLinePoint("Jun", 1.0f));
//      series.addPoint(new ValueLinePoint("Jul", 3.5f));
//      series.addPoint(new ValueLinePoint("Aug", 2.4f));
//      series.addPoint(new ValueLinePoint("Sep", 2.4f));
//      series.addPoint(new ValueLinePoint("Oct", 3.4f));
//      series.addPoint(new ValueLinePoint("Nov", .4f));
//      series.addPoint(new ValueLinePoint("Dec", 1.3f));
//
//      mCubicValueLineChart.addSeries(series);
//      mCubicValueLineChart.startAnimation();
      return view;
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
					sleepObj = new SleepModel(result);
					
					
					
					
					
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
