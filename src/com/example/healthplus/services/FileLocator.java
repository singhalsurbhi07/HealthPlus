package com.example.healthplus.services;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.healthplus.UserActivity;
import com.example.healthplus.utils.ExternalStorageUtil;

public class FileLocator extends IntentService {
	SharedPreferences sharedpreferences;
	  // Must create a default constructor
	  public FileLocator() {
	    // Used to name the worker thread, important only for debugging.
	    super("test-service");
	  }

	  @Override
	  protected void onHandleIntent(Intent intent) {
		  sharedpreferences = 
					getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
			String userName = sharedpreferences.getString("UserName","master");
			Log.d("FileLocator userName",userName);
	    // This describes what will happen when service is triggered
		  String reqPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ShareViaWifi/request.json";
		  Log.d("FileLocator req path",reqPath);
		  while(true)
		  {
			 if(traverse(reqPath)){
				 ExternalStorageUtil util = new ExternalStorageUtil();
					//String response = null;
					try {
						Log.d("FileLocatorService", "Now read file");
						util.readFile(reqPath,userName);
						String uri = "file://"+Environment.getExternalStoragePublicDirectory(
								Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/healthplus/response.txt";
						//Context con = getApplicationContext();
						Intent i  = new Intent();
						i.setAction(Intent.ACTION_SEND);
						i.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri));
						i.setType("text/rtf");
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
						break;
						
					} catch (IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }else{
				 try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		  }
		  Log.d("FileLocator","need to stop service...");
		  stopSelf();
			 
				 
				 
			// Log.d("onHandleIntent", name);
			 
	  }
	  
	  private boolean  traverse(String filePathString){
		  File f = new File(filePathString);
		  if(f.exists() && !f.isDirectory()){
			  return true;
		  }
		  return false;
//		    if (dir.isDirectory()) {
//		        String[] children = dir.list();
//		        for (int i = 0; children != null && i < children.length; i++) {
//		            traverse(new File(dir, children[i]));
//		        }
//		    }
//		    if (dir.isFile()) {
//		        if (dir.getName().endsWith(".json"))
//		               {
//		            System.out.println(dir.getAbsolutePath());//change it if needed
//		            return (dir.getAbsolutePath());
//		        }
//		    }
//		    return null;
//		
	}
}