//package com.example.healthplus.utils;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//
//import android.app.Activity;
//import android.os.Environment;
//import android.util.Log;
//
//import com.example.healthplus.R;
//
//public class ExternalStorageUtil extends Activity  {
//	public boolean isExternalStorageWritable() {
//	    String state = Environment.getExternalStorageState();
//	    if (Environment.MEDIA_MOUNTED.equals(state)) {
//	        return true;
//	    }
//	    return false;
//	}
//
//	/* Checks if external storage is available to at least read */
//	public boolean isExternalStorageReadable() {
//	    String state = Environment.getExternalStorageState();
//	    if (Environment.MEDIA_MOUNTED.equals(state) ||
//	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//	        return true;
//	    }
//	    return false;
//	}
//	private void writeToSDFile(){
//
//	    // Find the root of the external storage.
//	    // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal
//
//	    File root = android.os.Environment.getExternalStorageDirectory(); 
//	    //tv.append("\nExternal file system root: "+root);
//
//	    // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
//
//	    File dir = new File (root.getAbsolutePath() + "/download");
//	    dir.mkdirs();
//	    File file = new File(dir, "myData.txt");
//
//	    try {
//	        FileOutputStream f = new FileOutputStream(file);
//	        PrintWriter pw = new PrintWriter(f);
//	        pw.println("Hi , How are you");
//	        pw.println("Hello");
//	        pw.flush();
//	        pw.close();
//	        f.close();
//	    } catch (FileNotFoundException e) {
//	        e.printStackTrace();
//	        //Log.i(TAG, "******* File not found. Did you" +
//	               // " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }   
//	    //tv.append("\n\nFile written to "+file);
//	}
//
//	/** Method to read in a text file placed in the res/raw directory of the application. The
//	  method reads in all lines of the file sequentially. */
//
//	private void readRaw(){
//	    //tv.append("\nData read from res/raw/textfile.txt:");
//	    InputStream is = this.getResources().openRawResource(R.raw.textfile);
//	    InputStreamReader isr = new InputStreamReader(is);
//	    BufferedReader br = new BufferedReader(isr, 8192);    // 2nd arg is buffer size
//
//	    // More efficient (less readable) implementation of above is the composite expression
//	    /*BufferedReader br = new BufferedReader(new InputStreamReader(
//	            this.getResources().openRawResource(R.raw.textfile)), 8192);*/
//
//	    try {
//	        String test;    
//	        while (true){               
//	            test = br.readLine();   
//	            // readLine() returns null if no more lines in the file
//	            if(test == null) break;
//	            //tv.append("\n"+"    "+test);
//	        }
//	        isr.close();
//	        is.close();
//	        br.close();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    //tv.append("\n\nThat is all");
//	}
//	
//
//
//    // Find the root of the external storage.
//    // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal
//
//}