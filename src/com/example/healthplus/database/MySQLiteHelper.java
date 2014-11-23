package com.example.healthplus.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.healthplus.utils.ApiCallHelper;
import com.example.healthplus.utils.DateUtil;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "HealthDB.db";
	public static String COLUMN_ID = "DATE";

	public static final String TBL_WATER = "WATER";
	public static final String COLUMN_AMOUNT = "AMOUNT";
	private static final String TABLE_WATER_CREATE = "CREATE TABLE  IF NOT EXISTS " + TBL_WATER 
			+ " ( " + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " 
			+  " AMOUNT INTEGER DEFAULT 0 );"	;

	private static final String TBL_FOOD = "FOOD";
	private static final String TBL_SLEEP = "SLEEP";
	private static final String TBL_ACTIVITIES = "ACTIVITIES";


	private static final String TABLE_FOOD_CREATE = "CREATE TABLE IF NOT EXISTS " + TBL_FOOD 
			+ " (" + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL,  "
			+ " CALORIES REAL DEFAULT 0.0, "
			+ " CARBS REAL DEFAULT 0.0, "
			+ " FAT REAL DEFAULT 0.0, "
			+ " FIBER REAL DEFAULT 0.0, "
			+ " PROTEIN REAL DEFAULT 0.0, "
			+ " SODIUM REAL DEFAULT 0.0 );";

	private static final String TABLE_SLEEP_CREATE = "CREATE TABLE  IF NOT EXISTS " + TBL_SLEEP
			+ " (" + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, "
			+ " TOTAL_MINUTES_ASLEEP INTEGER DEFAULT 0, "
			+ " RESTLESS_DURATION INTEGER DEFAULT 0, "
			+ " MINUTES_AWAKE INTEGER DEFAULT 0 ); ";

	private static final String TABLE_ACTIVITIES_CREATE = "CREATE TABLE  IF NOT EXISTS " + TBL_ACTIVITIES
			+ " ( " + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL , "
			+ " DISTANCE REAL DEFAULT 0.0, " 
			+ " CALORIES_OUT REAL DEFAULT 0.0, "
			+ " STEPS INTEGER DEFAULT 0 ); ";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_WATER_CREATE);
		db.execSQL(TABLE_FOOD_CREATE);
		db.execSQL(TABLE_SLEEP_CREATE);
		db.execSQL(TABLE_ACTIVITIES_CREATE);
		//db.execSQL(TABLE_INSERT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER_CREATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_CREATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP_CREATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES_CREATE);

		onCreate(db);
	}

	// FITBIT DATE FORMAT = 2010-02-25.json.
	public static String dateToSqliteDateString(String date) {
		// The format is the same as CURRENT_TIMESTAMP: "YYYY-MM-DD"
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		return sdf.format(date);
	}

	// Insert record into the database
	public void addWaterRow(String waterStr) throws JSONException {
		Log.d("MysqliteHelper","addwaterItem");

		JSONObject waterJSON = new JSONObject(waterStr);		
		String amount = new JSONObject(waterJSON.getString("summary")).getString("water");
		// Open database connection
		SQLiteDatabase db = this.getWritableDatabase();
		// Define values for each field
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, DateUtil.getYesterdayDateString()); 
		values.put(COLUMN_AMOUNT, Float.parseFloat(amount)); 
		// Insert Row
		long rowId=db.insert(TBL_WATER, null, values);
		Log.d("MySqliteHlper addWaterItem",String.valueOf(rowId));
		db.close(); 
		// Closing database connection
	}

	public void addSleepRow(String sleepStr) throws JSONException {

		Log.d("MysqliteHelper","addSleepRow");

		JSONObject sleepJSON = new JSONObject(sleepStr);		
		String totalMinutesAsleep = new JSONObject(sleepJSON.getString("summary")).getString("totalMinutesAsleep");
		int totalMinutesAwake = computeMiutesAwake(sleepJSON.getJSONArray("sleep"));
		int totalRestlessMinutes = computeRestlessMinutes(sleepJSON.getJSONArray("sleep"));

		// Open database connnection
		SQLiteDatabase db = this.getWritableDatabase();
		// Define values for each field
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, DateUtil.getYesterdayDateString()); 
		values.put("TOTAL_MINUTES_ASLEEP", totalMinutesAsleep);
		values.put("MINUTES_AWAKE", totalMinutesAwake);
		values.put("RESTLESS_DURATION", totalRestlessMinutes);
		// Insert Row
		long rowId=db.insert(TBL_SLEEP, null, values);
		Log.d("MySqliteHlper addSleepRow",String.valueOf(rowId));
		db.close(); 
	}

	public void addFoodRow(String foodStr) throws JSONException {

		Log.d("MysqliteHelper","addFoodRow");

		JSONObject FoodJSON = new JSONObject(foodStr);		
		String calories = new JSONObject(FoodJSON.getString("summary")).getString("calories");
		String carbs  = new JSONObject(FoodJSON.getString("summary")).getString("carbs");
		String fat = new JSONObject(FoodJSON.getString("summary")).getString("fat");
		String protein = new JSONObject(FoodJSON.getString("summary")).getString("protein");
		String sodium = new JSONObject(FoodJSON.getString("summary")).getString("sodium");
		String fiber = new JSONObject(FoodJSON.getString("summary")).getString("fiber");

		// Open database connnection
		SQLiteDatabase db = this.getWritableDatabase();
		// Define values for each field
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, DateUtil.getYesterdayDateString()); 
		values.put("CALORIES", calories);
		values.put("CARBS", carbs);
		values.put("FAT", fat);
		values.put("PROTEIN", protein);
		values.put("SODIUM", sodium);
		values.put("FIBER", fiber);

		// Insert Row
		long rowId=db.insert(TBL_FOOD, null, values);
		Log.d("MySqliteHlper addFoodRow",String.valueOf(rowId));
		db.close();
	}

	public void addActivitiesRow(String activitiesStr) throws JSONException {

		Log.d("MysqliteHelper","addActivitiesRow");

		JSONObject activitiesJSON = new JSONObject(activitiesStr);		
		String calories_out = new JSONObject(activitiesJSON.getString("summary")).getString("caloriesOut");
		String steps  = new JSONObject(activitiesJSON.getString("summary")).getString("steps");
		float distance = computeDistance(new JSONObject(activitiesJSON.getString("summary")).getJSONArray("distances"));
				
		// Open database connnection
		SQLiteDatabase db = this.getWritableDatabase();
		// Define values for each field
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, DateUtil.getYesterdayDateString()); 
		values.put("CALORIES_OUT", calories_out);
		values.put("STEPS", steps);
		values.put("DISTANCE", distance);

		// Insert Row
		long rowId=db.insert(TBL_ACTIVITIES, null, values);
		Log.d("MySqliteHlper addActivitiesRow",String.valueOf(rowId));
		db.close();
	}
	
	private int computeMiutesAwake(JSONArray arr) throws JSONException {
		int total=0;

		for(int i=0;i<arr.length();i++){
			JSONObject eachObj = arr.getJSONObject(i);
			int eachMinutesAwake =Integer.parseInt(eachObj.getString("minutesAwake"));
			total += eachMinutesAwake;    		
		}
		return total;
	}

	private int computeRestlessMinutes(JSONArray arr) throws JSONException {
		int total=0;

		for(int i=0;i<arr.length();i++){
			JSONObject eachObj = arr.getJSONObject(i);
			int eachRestlessDuration =Integer.parseInt(eachObj.getString("restlessDuration"));
			total += eachRestlessDuration;    		
		}
		return total;
	}
	
	private float computeDistance(JSONArray arr) throws JSONException {
		float total=0f;

		for(int i=0;i<arr.length();i++){
			JSONObject eachObj = arr.getJSONObject(i);
			String eachDistance = eachObj.getString("activity");
			
			if (eachDistance.equals("total")) {
				
				total = Float.parseFloat(eachObj.getString("distance"));
				System.out.println("computeDistance="+String.valueOf(total));
			}
		}
		return total;
	}
}
