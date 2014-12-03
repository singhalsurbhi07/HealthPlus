package com.example.healthplus.response.fragments;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import com.example.healthplus.R;
import com.example.healthplus.response.fragments.ResponseSleepFragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ResponseGoodSleepFragment extends Fragment {
private String TAG = "ResponseGoodSleepFragment";
	
	Map<String,String> responseMap  = new HashMap<>();

	View view;
	
	private int getColor(int val) {
		
		int col = 0;		
		switch(val) {
			case 1 : col = Color.parseColor("#FE6DA8");
				break;
			case 2 : col = Color.parseColor("#56B7F1");
				break;
			case 3 : col = Color.parseColor("#CDA67F");
				break;
			case 4 : col = Color.parseColor("#FED70E");
				break;
			case 5 : col = Color.parseColor("#00CC00");
				break;
			case 6 : col = Color.parseColor("#CC00FF");
				break;
		}
		return col;
	}
	
	public static ResponseGoodSleepFragment newInstance(Map<String,String> responseMap) {
        ResponseGoodSleepFragment fragmentDemo = new ResponseGoodSleepFragment();
        Bundle args = new Bundle();
        args.putSerializable("responseMap", (Serializable) responseMap);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		view = inflater.inflate(R.layout.response_good_sleep_fragment, container, false);
		responseMap = (Map<String, String>) getArguments().getSerializable("responseMap");	
		BarChart mBarChart = (BarChart) view.findViewById(R.id.ResponseGoodSleepBarchart);
		int counter = 0;
		
		for (Map.Entry<String, String> entry : responseMap.entrySet()) {

			counter = counter + 1;
			mBarChart.addBar(new BarModel(entry.getKey(), Float.parseFloat(entry.getValue()), getColor(counter)));		
		}
		mBarChart.startAnimation();
		return view;
	}
	
}
