package com.example.healthplus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.healthplus.datamodels.Response;
import com.example.healthplus.response.fragments.ResponseActivitiesFragment;
import com.example.healthplus.response.fragments.ResponseFoodFragment;
import com.example.healthplus.response.fragments.ResponseWaterFragment;

public class CumulativeResponseActivity extends Activity {
	//ExternalStorageUtil util = new ExternalStorageUtil();
	private static String TAG= "CumulativeResponseActivity";
	private Map<String,String> responseMap = new HashMap<>();
	private String dataType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cumulative_response);
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();

		List<Response> responses=
		               (List<Response>)bundle.getSerializable("allResponse");
		initialiseResponseMap(responses);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if(dataType.equals("Water")){
			Log.d(TAG,"dataType = water");
			ResponseWaterFragment fragmentDemo = ResponseWaterFragment.newInstance(responseMap);
			ft.replace(R.id.fragment_cumulative_response_placeholder, fragmentDemo);
			ft.commit();
		}else if(dataType.equals("Calories Intake")){
			Log.d(TAG,"dataType = calories intake");
			ResponseFoodFragment fragmentDemo = ResponseFoodFragment.newInstance(responseMap);
			ft.replace(R.id.fragment_cumulative_response_placeholder, fragmentDemo);
			ft.commit();
		}else if(dataType.equals("Calories Burn")){
			Log.d(TAG,"dataType = calories burn");
			ResponseActivitiesFragment fragmentDemo = ResponseActivitiesFragment.newInstance(responseMap);
			ft.replace(R.id.fragment_cumulative_response_placeholder, fragmentDemo);
			ft.commit();
		}
		
		
	}

	private void initialiseResponseMap(List<Response> responses) {
		for(Response response : responses){
			Log.d(TAG,"response val = "+response.getResultValue());
			responseMap.put(response.getUserName(), response.getResultValue());
			dataType = response.getDataType();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cumulative_response, menu);
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
