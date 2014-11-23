package com.example.healthplus;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
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


public class UserQuery extends FragmentActivity implements AlertPositiveListener{
	private static int selected_button;
	private static String functionVal;
	private static String dataVal;
	private static String startDateVal;
	private static String endDateVal;
	private String[] values = new String[] { "Select Function","Select Data", "Select Start Date", "Select End Date" };
	
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
				int res = checkValidity();
            	checkAllValuesSet(res);
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
               startDateVal = (String.valueOf(day) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(year));                               
                now.set(year, month, day);
              //pet.setBirthdate(birthdate.getText().toString());
                Log.d("UserQuery show DatePicker",startDateVal);
            	}else if(selected_button==3){
            		endDateVal = (String.valueOf(day) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(year));                               
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
	
	private void checkAllValuesSet(int value){
		if(value<0){
			formQuery();
		}else{
			Context context = getApplicationContext();
			CharSequence text = values[value];
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	private void formQuery(){
		
	}
}