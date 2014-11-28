package com.example.healthplus.adapters;

import com.example.healthplus.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageGridAdapter extends BaseAdapter {
	private Context context;
	private final String[] optionsValues;
 
	public ImageGridAdapter(Context context, String[] mobileValues) {
		this.context = context;
		this.optionsValues = mobileValues;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.menu_component, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(optionsValues[position]);
 
			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
 
			String mobile = optionsValues[position];
			if (mobile.equals("Activities")) {
				imageView.setImageResource(R.drawable.ic_activities);
			} else if (mobile.equals("Food")) {
				imageView.setImageResource(R.drawable.ic_food);
			} else if (mobile.equals("Sleep")) {
				imageView.setImageResource(R.drawable.ic_food);
			} else if(mobile.equals("Heart")){
				imageView.setImageResource(R.drawable.ic_food);
			}else if(mobile.equals("BP")){
				imageView.setImageResource(R.drawable.ic_food);
			}else if(mobile.equals("Weight")){
				imageView.setImageResource(R.drawable.ic_food);
			}
 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return optionsValues.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}

}
