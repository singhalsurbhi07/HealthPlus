package com.example.healthplus.fragments;

import java.util.Calendar;



import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment {    

public static String TAG = "DateDialogFragment";
static Context mContext; 
static int mYear;
static int mMonth;
static int mDay;
static DateDialogFragmentListener mListener;


public static DatePickerFragment newInstance(Context context, DateDialogFragmentListener listener, Calendar now) {
    DatePickerFragment dialog = new DatePickerFragment();
    mContext = context;
    mListener = listener;       
    mYear = now.get(Calendar.YEAR);
    mMonth = now.get(Calendar.MONTH);
    mDay = now.get(Calendar.DAY_OF_MONTH);      
    return dialog;
}


public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new DatePickerDialog(mContext, mDateSetListener, mYear, mMonth, mDay);
}


private OnDateSetListener mDateSetListener = new OnDateSetListener() {      
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;          
        mListener.updateChangedDate(year, monthOfYear, dayOfMonth);
    }


};


public interface DateDialogFragmentListener {
    public void updateChangedDate(int year, int month, int day);
}

}