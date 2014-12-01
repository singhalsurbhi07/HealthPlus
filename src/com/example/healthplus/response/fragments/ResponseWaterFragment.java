package com.example.healthplus.response.fragments;

import java.util.Map;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthplus.R;
import com.example.healthplus.utils.ExternalStorageUtil;

public class ResponseWaterFragment {

	View view;

	private int getColor(int val){
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

	public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.activity_response_water_fragment, container, false);

		PieChart mPieChart = (PieChart) view.findViewById(R.id.ResponseWaterPiechart);
		int counter = 0;
		for (Map.Entry<String, String> entry : ExternalStorageUtil.responseMap.entrySet()) {
			counter = counter + 1;

			mPieChart.addPieSlice(new PieModel("amount", Float.parseFloat(entry.getValue()), getColor(counter)));
		}		
		mPieChart.startAnimation();
	}
}
