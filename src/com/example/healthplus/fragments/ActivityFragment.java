package com.example.healthplus.fragments;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.healthplus.R;
import com.example.healthplus.datamodels.Activities;
import com.example.healthplus.oauth.SerializableOauthData;
import com.example.healthplus.utils.DateUtil;

public class ActivityFragment extends Fragment {
	OAuthData data;
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA, Color.CYAN, Color.RED, Color.YELLOW,Color.LTGRAY};  
	
	Activities userActivitiesObj;
	
	  
	private static double[] VALUES ; 
	  
	private static String[] NAME_LIST ;
	  
	private CategorySeries mSeries = new CategorySeries("");  
	  
	private DefaultRenderer mRenderer = new DefaultRenderer();  
	  
	private GraphicalView mChartView;  
	
	View view;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      // Defines the xml file for the fragment
      view = inflater.inflate(R.layout.fragment_activities, container, false);
      getUserActivitiesData();
      
      VALUES = userActivitiesObj.getActivitiesCalArray();
      NAME_LIST = userActivitiesObj.getActivitiesNameArray();
      
      mRenderer.setApplyBackgroundColor(true);  
      mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));  
      mRenderer.setChartTitleTextSize(15);  
      mRenderer.setLabelsTextSize(10);  
      mRenderer.setLegendTextSize(10);  
      mRenderer.setMargins(new int[] { 20, 30, 15, 0 });  
      mRenderer.setZoomButtonsVisible(true);  
      mRenderer.setStartAngle(90);  
        
      for (int i = 0; i < VALUES.length; i++) {  
      mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);  
      SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();  
      renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);  
      mRenderer.addSeriesRenderer(renderer);  
      }  
        
      if (mChartView != null) {  
      mChartView.repaint();  
      }  
      
      return view;
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
					JSONObject result = new JSONObject(total.toString());
					JSONArray activitiesArray = new JSONArray(result.getString("activities"));
					userActivitiesObj = new Activities(new JSONObject(result.getString("goals")).getString("caloriesOut"), new JSONObject(result.getString("summary")).getString("caloriesOut"), activitiesArray);
					
					
					
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
	
	@Override
	public void onResume() {  
	super.onResume();  
	if (mChartView == null) {  
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.chart); 
	mChartView = ChartFactory.getPieChartView(this.getActivity(), mSeries, mRenderer);  
	mRenderer.setClickEnabled(true);  
	mRenderer.setSelectableBuffer(10);  
	  
	mChartView.setOnClickListener(new View.OnClickListener() {  
	@Override  
	public void onClick(View v) {  
	SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
	  
	if (seriesSelection == null) {  
	Toast.makeText(getActivity(),"No chart element was clicked",Toast.LENGTH_SHORT).show();  
	} else {  
	Toast.makeText(getActivity(),"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();  
	}  
	}  
	});  
	  
	mChartView.setOnLongClickListener(new View.OnLongClickListener() {  
	@Override  
	public boolean onLongClick(View v) {  
	SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();  
	if (seriesSelection == null) {  
	Toast.makeText(getActivity(),"No chart element was long pressed", Toast.LENGTH_SHORT);  
	return false;   
	} else {  
	Toast.makeText(getActivity(),"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);  
	return true;         
	}  
	}  
	});  
	layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
	}  
	else {  
	mChartView.repaint();  
	}  
	}  

}
