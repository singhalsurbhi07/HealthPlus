package com.example.healthplus.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthplus.R;


	public class ListAdapter extends ArrayAdapter<String> {
		
		public ListAdapter(Context context, ArrayList<String> data) {
		       super(context, 0, data);
		    }
		    
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		       // Get the data item for this position
		       String option = getItem(position);    
		       // Check if an existing view is being reused, otherwise inflate the view
		       if (convertView == null) {
		          convertView = LayoutInflater.from(getContext()).inflate(R.layout.each_sharing_step, parent, false);
		       }
		       // Lookup view for data population
		       TextView tvName = (TextView) convertView.findViewById(R.id.tvEachStep);
		       
		       // Populate the data into the template view using the data object
		       tvName.setText(option);
		       //tvHome.setText(user.hometown);
		       // Return the completed view to render on screen
		       return convertView;
		   }
	}
