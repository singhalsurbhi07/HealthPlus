package com.example.healthplus.response.fragments;

import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
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
import com.example.healthplus.datamodels.QueryData;
import com.example.healthplus.datamodels.Request;
import com.example.healthplus.datamodels.SleepModel;
import com.example.healthplus.oauth.SerializableOauthData;
import com.example.healthplus.utils.ExternalStorageUtil;

public class ResponseSleepFragment extends Fragment {

	View view;
	
	private int getColor(int val){
		int col=0;
		switch(val){
		case 1 : col = Color.parseColor("#6699FF");
		break;
		case 2 : col = Color.parseColor("#990033");
		break;
		case 3 :col = Color.parseColor("#0066CC");
		break;
		}
		return col;
	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		view = inflater.inflate(R.layout.activity_response_fragment, container, false);

		BarChart mBarChart = (BarChart) view.findViewById(R.id.barchart);
		int counter = 0;
		
		for (Map.Entry<String, Double> entry : ExternalStorageUtil.responseMap.entrySet()) {

			counter = counter + 1;
			mBarChart.addBar(new BarModel(Double.toString(entry.getValue()), 2.f, getColor(counter)));		
		}
		mBarChart.startAnimation();
		return view;
	}
}
