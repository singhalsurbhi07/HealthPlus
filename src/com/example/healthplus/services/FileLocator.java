package com.example.healthplus.services;

import java.io.File;

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
		 while (true){
			 String name = traverse(Environment.getExternalStorageDirectory());
			 if(name != null){
				 Log.d("FileLocator","foung file at "+name);
				 break;
				 
			 }else{
				 try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		 stopSelf();
		  
	  }
	  
	  private String  traverse(File dir){
		    if (dir.isDirectory()) {
		        String[] children = dir.list();
		        for (int i = 0; children != null && i < children.length; i++) {
		            traverse(new File(dir, children[i]));
		        }
		    }
		    if (dir.isFile()) {
		        if (dir.getName().endsWith(".json"))
		               {
		            System.out.println(dir.getAbsolutePath());//change it if needed
		            return (dir.getAbsolutePath());
		        }
		    }
		    return null;
		}
	}