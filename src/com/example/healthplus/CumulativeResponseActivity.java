package com.example.healthplus;

import com.example.healthplus.datamodels.WaterConsumeModel;
import com.example.healthplus.fragments.ActivityFragment;
import com.example.healthplus.response.fragments.ResponseWaterFragment;
import com.example.healthplus.utils.ExternalStorageUtil;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CumulativeResponseActivity extends Activity {
	ExternalStorageUtil util = new ExternalStorageUtil();
	private static String TAG= "CumulativeResponseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cumulative_response);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ResponseWaterFragment fragmentDemo = ResponseWaterFragment.newInstance(util.getResponseHashMap());
		ft.replace(R.id.fragment_cumulative_response_placeholder, fragmentDemo);
		ft.commit();
		
		
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
