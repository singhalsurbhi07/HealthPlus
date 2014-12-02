package com.example.healthplus;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthplus.adapters.ListAdapter;
import com.example.healthplus.datamodels.QueryData;
import com.example.healthplus.fragments.AlertDialogRadio;
import com.example.healthplus.fragments.AlertDialogRadio.AlertPositiveListener;
import com.example.healthplus.fragments.DatePickerFragment;
import com.example.healthplus.services.FileLocator;
import com.example.healthplus.services.ResponseFileService;
import com.example.healthplus.utils.ExternalStorageUtil;
import com.example.healthplus.wifidirect.WiFiDirectActivity;


public class UserQuery extends FragmentActivity implements AlertPositiveListener{
	private static int selected_button;
	private static String functionVal;
	private static String dataVal;
	private static String startDateVal;
	private static String endDateVal;
	private String[] values = new String[] { "Select Function","Select Data", "Select Start Date", "Select End Date" };
	ExternalStorageUtil util = new ExternalStorageUtil();
	
	private static boolean [] isSelected = new boolean [4];
	Calendar now;

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_query);

		final ListView listview = (ListView) findViewById(R.id.lvSharingSteps);
		

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final ListAdapter adapter = new ListAdapter(this,list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				selected_button = position;
				isSelected[position] = true;
				if((position==0)||(position==1)){
					Log.d("UserQuery On Item Clkick",item);

					FragmentManager manager = getFragmentManager();

					/** Instantiating the DialogFragment class */
					AlertDialogRadio alert = new AlertDialogRadio();

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

	@Override
	public void onPositiveClick(int position) {
		
		if(selected_button==0){
			functionVal = QueryData.functions[position];
			Log.d("UserQuery onPositiveClick functionVal",functionVal);
		}else{
			dataVal = QueryData.Tables[position];
			Log.d("UserQuery onPositiveClick dataVal",dataVal);
		}
		
	}
	
	public void showDatePickerDialog() {
//        // TODO Auto-generated method stub
         now = Calendar.getInstance();   
        DatePickerFragment frag = DatePickerFragment.newInstance(
                this, new DatePickerFragment.DateDialogFragmentListener() {
            public void updateChangedDate(int year, int month, int day) {
            	if(selected_button==2){
               startDateVal = (String.valueOf(year) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(day));                               
                now.set(year, month, day);
              //pet.setBirthdate(birthdate.getText().toString());
                Log.d("UserQuery show DatePicker",startDateVal);
            	}else if(selected_button==3){
            		endDateVal = (String.valueOf(year) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(day));                               
                    now.set(year, month, day);
                    
            	}
            	
                
            }
        }, 
        now);
        frag.show(getFragmentManager(), "ss");
    }
	
	private int checkValidity(){
		for (int i=0;i<isSelected.length;i++){
			if(!isSelected[i]){
				return i;
			}
			
		}
		return -1;
	}
	
	
	
	private void formQuery(){
		String query = QueryData.formQuery(functionVal, dataVal, startDateVal, endDateVal);
		SharedPreferences sharedpreferences = 
				getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
		String userName = sharedpreferences.getString("UserName","master");
		Log.d("UserQuery formQuery userName",userName);
		util.writeRequestToDownloads(query,dataVal);
		
	}
	
	public void onStartSharingClicked(View v){
		int res = checkValidity();
		if(res<0){
			formQuery();
			
			
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
