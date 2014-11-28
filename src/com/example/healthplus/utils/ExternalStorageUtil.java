package com.example.healthplus.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.example.healthplus.database.MySQLiteHelper;

public class ExternalStorageUtil extends Activity  {
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}
	public static void writeToSDFile(String query){

		if(isExternalStorageWritable()){

			// Find the root of the external storage.
			// See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

			//File root = android.os.Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageDirectory(),"healthplus");
			JSONObject obj = new JSONObject();
			//obj.put("deviceName", );
			try {
				obj.put("query", query);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File file = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOWNLOADS), "healthplus");
			if (!file.mkdirs()) {
				Log.d("External Storage dir ", "Directory not created");
			}
			Log.d("Externalstorage root=",file.getAbsolutePath());
			//tv.append("\nExternal file system root: "+root);

			// See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

			//File dir = new File (root.getAbsolutePath()+ "/healthplus");
			//String secStore = System.getenv("SECONDARY_STORAGE");
			//File f_secs = new File(secStore);
			//File dir = new File(secStore+"/healthplus");
			//dir.mkdirs();
			File dir = new File("/storage/extSdCard/healthplus");
			Log.d("ExternalStorage writeFile",dir.getAbsolutePath());
			File reqfile = new File(file, "request.txt");
			Log.d("ExternalStorage writeFile requestFileName",reqfile.getAbsolutePath());
			Log.d("ExternalStorage writeFile",file.getAbsolutePath());

			try {
				FileOutputStream f = new FileOutputStream(reqfile);
				PrintWriter pw = new PrintWriter(f);
				//pw.println(query);
				pw.write(obj.toString());
				pw.flush();
				pw.close();
				f.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				//Log.i(TAG, "******* File not found. Did you" +
				// " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
			} catch (IOException e) {
				e.printStackTrace();
			}   
		}else{
			Log.d("ExternalStorage writeFile","SD card not writable");
		}
		//tv.append("\n\nFile written to "+file);
	}

	/** Method to read in a text file placed in the res/raw directory of the application. The
	  method reads in all lines of the file sequentially. */

	public void writeResponseFileToDeviceStorage() {

		if(isExternalStorageWritable()){

			JSONObject obj = new JSONObject();
			try {
				obj.put("result", readFile());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			File file = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOWNLOADS), "healthplus");
			if (!file.mkdirs()) {
				Log.d("External Storage Directory ", "Directory not created");
			}
			Log.d("External Storage Root=",file.getAbsolutePath());

			File dir = new File("/storage/extSdCard/healthplus");
			Log.d("ExternalStorage writeResponseFile",dir.getAbsolutePath());
			File resfile = new File(file, "Response.json");
			Log.d("ExternalStorage writeFile Response File",resfile.getAbsolutePath());
			Log.d("ExternalStorage writeFile",file.getAbsolutePath());

			try {
				FileOutputStream f = new FileOutputStream(resfile);
				PrintWriter pw = new PrintWriter(f);

				pw.write(obj.toString());
				pw.flush();
				pw.close();
				f.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}   
		}else{
			Log.d("ExternalStorage writeFile","SD card not writable");
		}
	}


	public String readFile(){

		if(isExternalStorageReadable()) {
			JSONParser parser = new JSONParser();
			try {

				Object obj = parser.parse(new FileReader("HealthPlus-1417123202380.json"));

				JSONObject jsonObject = (JSONObject) obj;

				String query = (String) jsonObject.get("query");

				MySQLiteHelper dbHelper = new MySQLiteHelper(this);
				double result = dbHelper.selectQuery(query);
				
				return Double.toString(result);
			}
			catch(IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else{
			Log.d("ExternalStorage write Response File ", " SD card not readable");
		}
		return null;
	}

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

	// Find the root of the external storage.
	// See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

}