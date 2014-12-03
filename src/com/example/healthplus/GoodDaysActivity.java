package com.example.healthplus;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.healthplus.adapters.ListAdapter;
import com.example.healthplus.adapters.ListAdapterGoodDays;
import com.example.healthplus.datamodels.QueryData;
import com.example.healthplus.fragments.AlertDialogRadio;
import com.example.healthplus.fragments.AlertDialogRadioGoodDays;
import com.example.healthplus.fragments.AlertDialogRadioGoodDays.AlertPositiveListenerGoodDays;
import com.example.healthplus.fragments.DatePickerFragment;
import com.example.healthplus.fragments.AlertDialogRadio.AlertPositiveListener;
import com.example.healthplus.services.ResponseFileService;
import com.example.healthplus.utils.ExternalStorageUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GoodDaysActivity extends FragmentActivity implements AlertPositiveListenerGoodDays {
	private static int selected_button;
	private static String categoryVal;
	private static String startDateVal;
	private static String endDateVal;
	private String[] values = new String[] { "Find Good Days for", "Select Start Date", "Select End Date" };
	ExternalStorageUtil util = new ExternalStorageUtil();
	Calendar now;
	
	private static boolean [] isSelected = new boolean [3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_days);
		final ListView listview = (ListView) findViewById(R.id.lvGoodDaysQuerySteps);
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final ListAdapterGoodDays adapter = new ListAdapterGoodDays(this,list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				selected_button = position;
				isSelected[position] = true;
				if(position==0){
					Log.d("UserQuery On Item Clkick",item);

					FragmentManager manager = getFragmentManager();

					/** Instantiating the DialogFragment class */
					AlertDialogRadioGoodDays alert = new AlertDialogRadioGoodDays();

					/** Creating a bundle object to store the selected item's index */
					Bundle b  = new Bundle();

					/** Storing the selected item's index in the bundle object */
					b.putInt("selectedBtn", selected_button);
					//b.putInt("default_position", 0);

					/** Setting the bundle object to the dialog fragment object */
					alert.setArguments(b);

					/** Creating the dialog fragment object, which will in turn open the alert dialog window */
					alert.show(manager, "alert_dialog_radio");
				}else{
					showDatePickerDialog();
				}
				
			}
			//
		});
	}
	
	public void showDatePickerDialog() {
//      // TODO Auto-generated method stub
       now = Calendar.getInstance();   
      DatePickerFragment frag = DatePickerFragment.newInstance(
              this, new DatePickerFragment.DateDialogFragmentListener() {
          public void updateChangedDate(int year, int month, int day) {
          	if(selected_button==1){
             startDateVal = (String.valueOf(year) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(day));                               
              now.set(year, month, day);
            //pet.setBirthdate(birthdate.getText().toString());
              Log.d("UserQuery show DatePicker",startDateVal);
          	}else if(selected_button==2){
          		endDateVal = (String.valueOf(year) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(day));                               
                  now.set(year, month, day);
                  
          	}
          	
              
          }
      },now);
      frag.show(getFragmentManager(), "ss");
  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.good_days, menu);
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

	@Override
	public void onPositiveClick(int position) {
		// TODO Auto-generated method stub
		categoryVal = QueryData.goodDaysCategory[position];
		
		
	}
	
	private int checkValidity(){
		for (int i=0;i<isSelected.length;i++){
			if(!isSelected[i]){
				return i;
			}
			
		}
		return -1;
	}
	
	private void formGoodDaysQuery(String category){
		String query = null;
		if(category.equals(QueryData.goodDaysCategory[0])){
			query = QueryData.bestSleepDays(startDateVal, endDateVal);
		}
		SharedPreferences sharedpreferences = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
		String userName = sharedpreferences.getString("UserName","master");
		Log.d("UserQuery formQuery userName",userName);
		util.writeRequestToDownloads(query,categoryVal);
	}
	
	public void onStartSharingClicked(View v){
		int res = checkValidity();
		if(res<0){
			formGoodDaysQuery(categoryVal);
			
			
			String uri = "file://"+Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/healthplus/request.json";
			Intent sendin  = new Intent();
			sendin.setAction(Intent.ACTION_SEND);
			sendin.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri));
			sendin.setType("application/json");
			startActivityForResult(Intent.createChooser(sendin, "share file via"),0);
			Log.d("UserQuery","file sent, need to start activity");
			Intent i = new Intent(this, ResponseFileService.class);
			startService(i);

			
			//Intent i = new Intent(this,WiFiDirectActivity.class);
			//startActivity(i);
			
		}else{
			Context context = getApplicationContext();
			CharSequence text = values[res];
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		Log.d("User Query click res Value",String.valueOf(res));
    	
		
	}
}
