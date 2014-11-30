package com.example.healthplus.services;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

import com.example.healthplus.utils.ExternalStorageUtil;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class FileLocator extends IntentService {
	  // Must create a default constructor
	  public FileLocator() {
	    // Used to name the worker thread, important only for debugging.
	    super("test-service");
	  }

	  @Override
	  protected void onHandleIntent(Intent intent) {
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
						util.readFile(reqPath);
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