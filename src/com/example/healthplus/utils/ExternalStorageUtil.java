package com.example.healthplus.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.util.Log;

import com.example.healthplus.database.MySQLiteHelper;
import com.example.healthplus.oauth.SerializableOauthData;

public class ExternalStorageUtil extends Activity  {

	MySQLiteHelper sqlHelper = SerializableOauthData.getSqlHelper();
	public static HashMap<String, Double> responseMap = new HashMap<String, Double>();
	private static SharedPreferences sharedpreferences;
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
	public static void writeToSDFile(String query,String userName){
		
		


		if(isExternalStorageWritable()){

			// Find the root of the external storage.
			// See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

			//File root = android.os.Environment.getExternalStoragePublicDirectory(Environment.getExternalStorageDirectory(),"healthplus");
			JSONObject obj = new JSONObject();
			//obj.put("deviceName", );
			try {
				obj.put("type", "request");
				obj.put("query", query);
				//obj.put("Type", "Request");
				obj.put("userName",userName );
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
			//File dir = new File("/storage/extSdCard/healthplus");
			//Log.d("ExternalStorage writeFile",dir.getAbsolutePath());
			File reqfile = new File(file, "request.json");
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

	public void writeResponseFileToDeviceStorage(String resultValue,String userName) {

		if(isExternalStorageWritable()){

			JSONObject obj = new JSONObject();
			try {
				obj.put("userName", userName);
				obj.put("result", resultValue);
				obj.put("type", "Response");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			/*File file = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOWNLOADS), "healthplus");
			if (!file.mkdirs()) {
				Log.d("External Storage Directory ", "Directory not created");
			}
			Log.d("External Storage Root=",file.getAbsolutePath());*/
	
			File file = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOWNLOADS), "healthplus");
			if (!file.mkdirs()) {
				Log.d("External Storage dir ", "Directory not created");
			}
			//Log.d("Externalstorage root=",file.getAbsolutePath());
			File resfile = new File(file, "response.txt");
			Log.d("ExternalStorage writeFile requestFileName",resfile.getAbsolutePath());
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


	public String readFile(String path, String userName) throws IOException, JSONException{

		if(isExternalStorageReadable()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String line;
				StringBuilder outputString= new StringBuilder();
				Log.d("ReadFile" , "Reached Read File method!!");
				while((line = reader.readLine())!= null){
					outputString.append(line);
				}
				//Read from REQUEST JSON Object
				
				JSONObject jsonObj = new JSONObject(outputString.toString());
				Log.d("ExternalStorage read",jsonObj.getString("query"));
				
				SQLiteDatabase db = sqlHelper.getReadableDatabase();

				Double result =  sqlHelper.selectQuery(jsonObj.getString("query"));
				File file = new File(path);
                boolean deleted = file.delete();
                Log.d("File", "Request file deleted!!");
                
                responseMap.put("Shikha", result);
				responseMap.put("Surbhi", 56.0);
				
				Log.d("ExternalStorage read", " Result :" + result);
								
				writeResponseFileToDeviceStorage(Double.toString(result),userName);
				
				return Double.toString(result);
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}			
		}
		else{
			Log.d("ExternalStorage write Response File ", " SD card not readable");
		}
		return null;
	}

}