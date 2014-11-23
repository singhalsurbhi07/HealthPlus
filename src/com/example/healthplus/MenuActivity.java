package com.example.healthplus;

import com.example.healthplus.adapters.ImageGridAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	private static final String OPTION_SELECTED = "option";

	GridView gridView;
	 
	static final String[] options = new String[] { 
			"Activities","Food","Sleep","Water","Customise"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.activity_menu);
 
		gridView = (GridView) findViewById(R.id.gridView1);
 
		gridView.setAdapter(new ImageGridAdapter(this, options));
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   //Toast.makeText(getApplicationContext(),
				//((TextView) v).getText(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(MenuActivity.this,OptionsActivity.class);
				i.putExtra(OPTION_SELECTED, options[position]);
				startActivity(i);
			}
		});
 
	}
}
