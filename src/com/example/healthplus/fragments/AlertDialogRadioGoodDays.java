package com.example.healthplus.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.example.healthplus.datamodels.QueryData;
import com.example.healthplus.fragments.AlertDialogRadio.AlertPositiveListener;
public class AlertDialogRadioGoodDays  extends DialogFragment{
	 
    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListenerGoodDays alertPositiveListener;
 
    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    public interface AlertPositiveListenerGoodDays {
        public void onPositiveClick(int position);
    }
 
    /** This is a callback method executed when this fragment is attached to an activity.
    *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
    * */
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListenerGoodDays) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }
 
    /** This is the OK button listener for the alert dialog,
     *  which in turn invokes the method onPositiveClick(position)
     *  of the hosting activity which is supposed to implement it
    */
    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position);
        }
    };
 
    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        /** Getting the arguments passed to this fragment */
        Bundle bundle = getArguments();
        int selectedBtn = bundle.getInt("selectedBtn");
        
 
        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        
 
        /** Setting a title for the window */
        b.setTitle("Select Category");
 
        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(QueryData.goodDaysCategory, 0, null);
        
 
        /** Setting a positive button and its listener */
        b.setPositiveButton("OK",positiveListener);
 
        /** Setting a positive button and its listener */
        b.setNegativeButton("Cancel", null);
 
        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();
 
        /** Return the alert dialog window */
        return d;
    }
}

