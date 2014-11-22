package com.example.healthplus;

import com.example.healthplus.fragments.ActivityFragment;
import com.example.healthplus.fragments.FoodFragment;
import com.example.healthplus.fragments.SleepFragment;
import com.example.healthplus.fragments.WaterConsumptionFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class OptionsActivity extends Activity {
	
	private static final String OPTION_RECEIVED = "option";
	String receivedOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		receivedOption = getIntent().getStringExtra(OPTION_RECEIVED);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		Log.d("OptionsActivity",OPTION_RECEIVED);
		if(receivedOption.equals("Activities")){
			ft.replace(R.id.fragment_placeholder, new ActivityFragment());
		}else if(receivedOption.equals("Water")){
			ft.replace(R.id.fragment_placeholder, new WaterConsumptionFragment());
		}else if(receivedOption.equals("Sleep")){
			ft.replace(R.id.fragment_placeholder, new SleepFragment());
		}else if(receivedOption.equals("Food")){
			ft.replace(R.id.fragment_placeholder, new FoodFragment());
		}else if(receivedOption.equals("Customise")){
			Intent i = new Intent(this,DataSharingSteps.class);
			startActivity(i);
		}
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
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
