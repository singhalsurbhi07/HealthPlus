package com.example.healthplus.services;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.example.healthplus.CumulativeResponseActivity;
import com.example.healthplus.utils.ExternalStorageUtil;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class ResponseFileService extends IntentService {
	private String TAG = "ResponseFileService";
	boolean isResFound = false;
	String resDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ShareViaWifi";
	String resPattern = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ShareViaWifi/response??.txt";
	ExternalStorageUtil util = new ExternalStorageUtil();

	public ResponseFileService() {
		// Used to name the worker thread, important only for debugging.
		super("test-service");
	}
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG,"Enter response service");
		while(!(isResFound)){
		Log.d(TAG,"isResFound = false;");
		if(traverseResponse(resDir)){
			isResFound = true;
			Log.d(TAG,"isResFound = true now;");
			break;
		}

	}
		stopSelf();
		Log.d(TAG,"Response File service stopped, going tp CumulativeResponseActivity");
		Intent i  = new Intent(this, CumulativeResponseActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}
	
	private boolean  traverseResponse(String dirString){
		Log.d(TAG,"waiting at traverseResponse");

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Pattern r = Pattern.compile(resPattern);

		File dir = new File(dirString);
		if (dir.isDirectory()) {
			Log.d(TAG,"recurring "+dir.getAbsolutePath());

			String[] children = dir.list();
			while(!(children.length>0)){
				try {
					Thread.sleep(10000);
					children = dir.list();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			children = dir.list();
			Log.d(TAG,"children size = "+children.length);
			for (int i = 0; children != null && i < children.length; i++) {
				File f = new File(dir, children[i]);
				Log.d(TAG,"found response file"+f.getName());

				if(r.matcher(f.getAbsolutePath()) != null){
					Log.d(TAG,"req file matches pattern"+f.getName());
					
						util.readResponFromSharedWiFi(f.getAbsolutePath());
						
						
					
				}
			}
			for (int i = 0; children != null && i < children.length; i++) {
				File f = new File(dir, children[i]);
				Log.d(TAG,"need to delete "+f.getName());

				f.delete();
			}
			
			return true;
		}
		return false;

	}

}
